import React, {Component} from "react";
import NavigationBar from "../home/NavigationBar";
import Footer from "../home/Footer";
import './YourOwnOffer.css'
import {Modal, Tab, Tabs} from "react-bootstrap";
import AuthContext from "../../others/AuthContext";
import {goTravelApi} from "../../others/GoTravelApi";
import {BsCheck2Circle, BsInfoCircle} from "react-icons/bs";
import {handleLogError} from "../../others/JWT";
import {withTranslation} from "react-i18next";
import Button from "react-bootstrap/Button";
import {Message} from "semantic-ui-react";
import StripeButton from "./StripeButton";


class YourOwnOffer extends Component {

    static contextType = AuthContext

    constructor(props) {
        super(props);
        this.state = {
            userInfo: [],
            countries: [],
            selectedCountryId: 0,
            cities: [],
            selectedCityId: 0,
            accommodations: [],
            selectedAccommodationId: 0,
            typeOfRooms: [],
            attractions: [],
            showModal: false,
            checkedAttractions: {},
            numberOfAdults: 0,
            numberOfChildren: 0,
            departureDate: '',
            numberOfDays: 0,
            food: false,
            isError: false,
            errorMessage: '',
            price: 0,
            key: 'generalInformation',
            rooms: [{type: "", quantity: ""}],
            user: null,
            summaryVisibility: false,
            bookedCorrectlyVisible: false,
            showModalReservationConfirmation: false,
            message: '',
            idOwnOffer: 0,
            insurances: [],
            selectedInsuranceId: 1,
            changesNotSaved: true
        }
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            goTravelApi.getUserInfo(user).then(res => {
                this.setState({
                    userInfo: res.data,
                    departureDate: this.getCurrentDate(),
                    user: user
                })
            })
        }
        this.handleGetCountries()
        this.handleGetAccommodations()
        this.handleGetTypeOfRooms()
        this.handleGetAttractions()
        this.getInsuranceInfo()
    }
    getCurrentDate() {
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        let month = currentDate.getMonth() + 1;
        if (month < 10) month = '0' + month;
        let day = currentDate.getDate();
        if (day < 10) day = '0' + day;
        return `${year}-${month}-${day}`;
    }

    getInsuranceInfo = () => {
        goTravelApi.getAllInsurances().then(res => {
            this.setState({
                insurances: res.data
            })
        }).catch(error => {
            handleLogError(error)
        })
    }

    handleGetAttractions = () => {goTravelApi.getAllAttractions().then(res => {this.setState({attractions: res.data})})}

    handleGetTypeOfRooms = () => {goTravelApi.getAllTypeOfRoom().then(res => {this.setState({typeOfRooms: res.data})})}

    handleGetAccommodations = () => {goTravelApi.getAccommodations().then(res => {this.setState({accommodations: res.data})})}

    handleGetCountries = () => {goTravelApi.getCountries().then(res => {this.setState({countries: res.data})})}

    handleChangeCountry = (e) => {
        this.setState({
            selectedCountryId: e.target.value,
        })
        if (e.target.value === "0") {
            this.setState({
                cities: [],
                selectedCityId: "0"
            })
        }

        goTravelApi.getCitiesByIdCountry(e.target.value).then(response => {
            this.setState({cities: response.data, changesNotSaved: true})
        })
    }

    handleRoomQuantityChange = (index, value) => {
        const rooms = [...this.state.rooms];
        rooms[index].quantity = value;
        this.setState({ rooms , changesNotSaved: true});
    }

    addRoom = () => {this.setState(prevState => ({rooms: [...prevState.rooms, { type: "", quantity: "" }], changesNotSaved: true}));}

    removeRoom = (index) => {this.setState(prevState => ({rooms: prevState.rooms.filter((_, i) => i !== index), changesNotSaved: true}));}

    handleRoomTypeChange = (index, value) => {
        const rooms = [...this.state.rooms];
        rooms[index].type = value;
        this.setState({ rooms, changesNotSaved: true});
    }

    typeOfRoomsStep = () => {return this.state.selectedAccommodationId !== '0' ? {"display": "block"} : {"display": "none"};}

    handleCloseModalReservationConfirmation = () => {this.setState({showModalReservationConfirmation: false, key: 'additionalInformation'});};

    handleCheckboxChangeAttractions = (e) => {
        const {id, checked} = e.target;
        this.setState(prevState => ({
            checkedAttractions: {
                ...prevState.checkedAttractions,
                [id]: checked
            },
            changesNotSaved: true
        }), this.checkIfAnyCheckboxCheckedAttractions);
    }

    checkIfAnyCheckboxCheckedAttractions = () => {
        const {checkedAttractions} = this.state;
        const anyCheckboxChecked = Object.values(checkedAttractions).some(checked => checked);
        if (!anyCheckboxChecked) {
            this.setState({checkedAttractions: {}})
        }
    }

    formatDate = (date) => {
        const parts = date.split('.');
        const reversed = parts.reverse();
        return reversed.join('-')
    }

    validationData = () => {
        const {t} = this.props;
        const checkedAttractionsName = Object.keys(this.state.checkedAttractions)

        if (this.state.rooms.some(room => room.type === "" || room.type === "choose" || room.quantity === "" || room.quantity === "0")) {
            this.setState({
                isError: true,
                errorMessage: t('goTravelNamespace3:pleaseIndicateTheNumberAndTypeOfRoomsWhenBooking'),
                showModalReservationConfirmation: true,
                summaryVisibility: false,
                changesNotSaved: true
            });
            return;
        }

        return {
            numberOfChildren: this.state.numberOfChildren,
            numberOfAdults: this.state.numberOfAdults,
            departureDate: this.state.departureDate,
            food: this.state.food,
            totalPrice: this.state.price,
            numberOfDays: this.state.numberOfDays,
            payment: false,
            offerCity: {
                idCity: this.state.selectedCityId
            },
            offerAccommodation: {
                idAccommodation: this.state.selectedAccommodationId
            },
            ownOfferTypeOfRooms: this.state.rooms.map(room => ({
                numberOfRoom: room.quantity,
                typeOfRoom: {
                    type: room.type
                }
            })),
            offerAttraction: checkedAttractionsName.map(atr => ({
                idAttraction: atr
            })),
            insuranceOwnOffer: {
                idInsurance: parseInt(this.state.selectedInsuranceId)
            }
        }
    }

    handleGetTotalPrice = async () => {
        const {t} = this.props;
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;
        const ownOffer = this.validationData()

        if(!ownOffer) return;

        goTravelApi.getTotalPriceOwnOffer(this.state.user, csrfToken, ownOffer).then((res) => {
            this.setState({summaryVisibility: true, price: res.data, changesNotSaved: false})
        }).catch(error => {
            handleLogError(error)
            const errorData = error.response
            this.setState({isError: true, errorMessage: t('goTravelNamespace3:'+errorData.data.message), showModalReservationConfirmation: true, summaryVisibility: false})
        })
    }

     handlePostOwnOffer = async () => {
         const {t} = this.props;
         const csrfResponse = await goTravelApi.csrf();
         const csrfToken = csrfResponse.data.token;
         const ownOffer = this.validationData()

         if(ownOffer) {
             goTravelApi.createOwnOffer(this.state.user, csrfToken, ownOffer).then(response => {
                 this.setState({
                     bookedCorrectlyVisible: true,
                     message: response.data.message,
                     idOwnOffer: response.data.idOwnOffer,
                     changesNotSaved: false
                 })
             }).catch(error => {
                 handleLogError(error)
                 const errorData = error.response
                 this.setState({
                     isError: true,
                     errorMessage: t('goTravelNamespace3:' + errorData.data.message),
                     showModalReservationConfirmation: true
                 })
             })
         }
     }

    handleCloseModalBookedCorrectly = () => {
        this.setState({
            bookedCorrectlyVisible: false,
            message: ''
        });
        window.location.href = "/myProfile/invoices"
    }

    handleTabSelect = (selectedKey) => {
        if (selectedKey === 'summary') {
            this.handleGetTotalPrice().catch(error => {
                console.error("Error fetching total price:", error);
            });
        }
        this.setState({key: selectedKey});
    };

    returnTripFormat = (date) => {
        if (date !== 0) {
            let newDate = new Date(date.toString());
            newDate.setDate(newDate.getDate() + parseInt(this.state.numberOfDays));
            return newDate.toISOString().slice(0, 10);
        }
    }

    render() {
        const {key} = this.state
        const {t} = this.props
        const checkedAttractionsName = Object.keys(this.state.checkedAttractions)

        return (
            <div>
                <NavigationBar/>
                <section className='d-flex justify-content-center justify-content-lg-between p-2  mt-4'></section>
                <header className={"head"}>
                    <section className={"d-flex justify-content-center"}>
                        <div className="d-flex justify-content-center w-75">
                            <div className="d-flex flex-column align-items-start">
                                <Tabs
                                    id="controlled-tab"
                                    activeKey={key}
                                    onSelect={this.handleTabSelect}
                                    className="mb-3 flex-row"
                                    style={{width: '1200px'}}
                                >
                                    <Tab eventKey="generalInformation" title={t('goTravelNamespace3:generalInformation')}></Tab>
                                    <Tab eventKey="basicInformationAboutTheTrip" title={t('goTravelNamespace3:basicInformationAboutTheTrip')}/>
                                    <Tab eventKey="accommodation" title={t('goTravelNamespace3:accommodation')}/>
                                    <Tab eventKey="attractions" title={t('goTravelNamespace2:attractions')}/>
                                    <Tab eventKey="additionalInformation" title={t('goTravelNamespace3:additionalInformation')}/>
                                    {this.state.summaryVisibility && !this.state.changesNotSaved &&
                                        <Tab eventKey="summary" title={t('goTravelNamespace3:summary')}/>
                                    }

                                </Tabs>
                                <div className={"ml-auto"}>
                                    {key === 'generalInformation' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '1000px', marginLeft: '10%', marginRight: '10%'}}>
                                            <div style={{textAlign: 'center'}}>
                                                <p className={"infoOwnOffer"}>{t('goTravelNamespace3:generalInformationPart1')}</p>
                                                <ul style={{
                                                    fontFamily: 'Century Gothic',
                                                    fontSize: '17px',
                                                    textAlign: 'justify',
                                                    margin: '10px 0'
                                                }}>
                                                    <li>{t('goTravelNamespace3:generalInformationPart2')}</li>
                                                    <li>{t('goTravelNamespace3:generalInformationPart3')}</li>
                                                    <li>{t('goTravelNamespace3:generalInformationPart4')}</li>
                                                </ul>
                                                <nav className="tabs mt-4">
                                                    <ul style={{ justifyContent: 'flex-end' }}>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('basicInformationAboutTheTrip')}}>{t('goTravelNamespace3:next')}</a></li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    }
                                    {key === 'basicInformationAboutTheTrip' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '1000px', height: '300px', marginLeft: '10%', marginRight: '10%'}}>
                                            <div style={{textAlign: 'center'}}>
                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <p className={"filterNameOwnTrip"}>{t('goTravelNamespace1:country')}: </p>
                                                    </div>
                                                    <div className={"col"}>
                                                        <select style={{width: '500px'}} className="form-control search-slt" id="exampleFormControlSelect1" name={"selectedCountry"} onChange={(e) => this.handleChangeCountry(e)} value={this.state.selectedCountryId}>
                                                            <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                                            {this.state.countries.map(country =>
                                                                <option key={country.idCountry} value={country.idCountry}>{t('goTravelNamespace2:'+country.nameCountry)}</option>
                                                            )}
                                                        </select>
                                                    </div>
                                                </div>

                                                <div className={"row"} style={{marginTop: '20px'}}>
                                                    <div className={"col"}>
                                                        <p className={"filterNameOwnTrip"}>{t('goTravelNamespace2:city')}: </p>
                                                    </div>
                                                    <div className={"col"}>
                                                        <select style={{width: '500px'}} className="form-control search-slt" id="exampleFormControlSelect1" name={"selectedCity"} onChange={(e) => this.setState({selectedCityId: e.target.value, changesNotSaved: true})} value={this.state.selectedCityId}>
                                                            <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                                            {this.state.cities.map(city =>
                                                                <option key={city.idCity} value={city.idCity}>{t('goTravelNamespace2:'+city.nameCity)}</option>
                                                            )}
                                                        </select>
                                                    </div>
                                                </div>

                                                <nav className="tabs mt-4">
                                                    <ul style={{ justifyContent: 'flex-end' }}>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('generalInformation')}}>{t('goTravelNamespace3:back')}</a></li>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('accommodation')}}>{t('goTravelNamespace3:next')}</a></li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    }
                                    {key === 'accommodation' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '1000px', height: '300px', marginLeft: '10%', marginRight: '10%'}}>
                                            <div style={{textAlign: 'center'}}>
                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <p className={"filterNameOwnTrip"}>{t('goTravelNamespace3:accommodation')}: <BsInfoCircle onClick={(e) => this.setState({showModal: true})}/></p>
                                                    </div>
                                                    <div className={"col"}>
                                                        <select style={{width: '500px'}} className="form-control search-slt" id="exampleFormControlSelect1"
                                                                name={"selectedTransport"}
                                                                onChange={(e) => this.setState({selectedAccommodationId: e.target.value, changesNotSaved: true})}
                                                                value={this.state.selectedAccommodationId}>
                                                            <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                                            {this.state.accommodations.map(accommodation =>
                                                                <option key={accommodation.idAccommodation} value={accommodation.idAccommodation}>{t('goTravelNamespace2:'+accommodation.nameAccommodation)}</option>
                                                            )}
                                                        </select>
                                                    </div>
                                                </div>

                                                <div style={this.typeOfRoomsStep() || {height: '140px'}}>
                                                    <div className={"col"}>
                                                        <div className="row g-2 align-items-center">
                                                            <div className="col w-20">
                                                                <label className="col-form-label">{t('goTravelNamespace3:typeOfRoom')}</label>
                                                            </div>
                                                            <div className="col w-20">
                                                                <label className="col-form-label">{t('goTravelNamespace3:quantity')}</label>
                                                            </div>
                                                            <div className="col colReservation w-20"/>
                                                        </div>
                                                    </div>
                                                    {this.state.rooms.map((room, index) => (
                                                        <div key={index} className="row mt-2 align-items-center" style={{textAlign: '-webkit-center'}}>
                                                            <div className="col">
                                                                <select
                                                                    className="form-control search-slt"
                                                                    style={{ width: '15rem', borderRadius: '5px' }}
                                                                    value={room.type}
                                                                    onChange={(e) => this.handleRoomTypeChange(index, e.target.value)}>
                                                                    <option>{t('choose')}</option>
                                                                    {this.state.typeOfRooms.map(room =>
                                                                        <option key={room.idTypeOfRoom} value={room.type}>{t('goTravelNamespace2:'+room.type)}</option>
                                                                    )}
                                                                </select>
                                                            </div>
                                                            <div className="col">
                                                                <input
                                                                    type="number"
                                                                    min="0"
                                                                    className="form-control"
                                                                    placeholder="0"
                                                                    style={{ width: '10rem' }}
                                                                    value={room.quantity}
                                                                    onChange={(e) => this.handleRoomQuantityChange(index, e.target.value)}
                                                                />
                                                            </div>
                                                            <div className="col">
                                                                {index === this.state.rooms.length - 1 && <button onClick={this.addRoom} className={"quantityButton"}>+</button>}
                                                                {index !== 0 && <button onClick={() => this.removeRoom(index)} className={"quantityButton"}>-</button>}
                                                            </div>
                                                        </div>
                                                    ))}
                                                    <Modal size={"lg"} show={this.state.showModal} onHide={() => this.setState({showModal: false})}>
                                                        <Modal.Header style={{fontFamily: 'Comic Sans MS'}} closeButton><Modal.Title>{t('goTravelNamespace3:accommodationInfoPart1')}</Modal.Title></Modal.Header>
                                                        <Modal.Body style={{textAlign: 'justify', fontFamily: 'Comic Sans MS'}}>
                                                            <br/>
                                                            <table className={"table"}>
                                                                <thead>
                                                                <tr>
                                                                    <th scope={"col"} style={{textAlign: 'center'}}>{t('goTravelNamespace3:standard')}</th>
                                                                    <th scope={"col"} ><span style={{marginRight: '30px', marginLeft:'30px'}}>{t('goTravelNamespace3:typeOfRoom')}</span>{t('goTravelNamespace3:pricePerPerson')}</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                {this.state.accommodations.map(accommodation =>
                                                                    <tr key={accommodation.idAccommodation}>
                                                                        <th scope={"row"} style={{
                                                                            fontWeight: 'normal',
                                                                            textAlign: 'center'
                                                                        }}>{t('goTravelNamespace2:'+accommodation.nameAccommodation)}</th>
                                                                        {this.state.typeOfRooms.map(typeOfRoom =>
                                                                            <tr key={typeOfRoom.idTypeOfRoom}>
                                                                                <th style={{
                                                                                    fontWeight: 'normal',
                                                                                    textAlign: 'center',
                                                                                    width: '200px'
                                                                                }}>{t('goTravelNamespace2:'+typeOfRoom.type)}</th>
                                                                                <th style={{
                                                                                    fontWeight: 'normal',
                                                                                    textAlign: 'center',
                                                                                    width: '70px'
                                                                                }}>{accommodation.priceAccommodation + typeOfRoom.roomPrice}</th>
                                                                            </tr>
                                                                        )}
                                                                    </tr>
                                                                )}
                                                                </tbody>
                                                            </table>
                                                        </Modal.Body>
                                                        <Modal.Footer>
                                                            <Button variant="secondary" onClick={() => this.setState({showModal: false})}>{t('goTravelNamespace3:back')}</Button>
                                                        </Modal.Footer>
                                                    </Modal>

                                                </div>

                                                <nav className="tabs mt-4">
                                                    <ul style={{ justifyContent: 'flex-end' }}>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('basicInformationAboutTheTrip')}}>{t('goTravelNamespace3:back')}</a></li>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('attractions')}}>{t('goTravelNamespace3:next')}</a></li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    }
                                    {key === 'attractions' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '1000px', height: '400px', marginLeft: '10%', marginRight: '10%'}}>
                                            <div style={{textAlign: 'center', width: '600px'}}>
                                                <p className={"filterNameOwnTrip"}>{t('goTravelNamespace2:attractions')}:</p>
                                                <div>
                                                    {this.state.attractions.map((attraction, index) => (index % 2 === 0 && <div className={"row mt-4"} key={index}>
                                                            {this.state.attractions.slice(index, index + 2).map((attraction) => (
                                                                <div className={"col-6 col-lg-6"} key={attraction.idAttraction}>
                                                                    <div className={"form-check"}>
                                                                        <input className="form-check-input"
                                                                               type="checkbox"
                                                                               value={attraction.idAttraction}
                                                                               id={attraction.idAttraction}
                                                                               name={attraction.idAttraction}
                                                                               checked={this.state.checkedAttractions[attraction.idAttraction]}
                                                                               onChange={this.handleCheckboxChangeAttractions}/>
                                                                        <label className="form-check-label" htmlFor={attraction.idAttraction}>{t('goTravelNamespace2:'+attraction.nameAttraction)} - {attraction.priceAttraction} {t('goTravelNamespace3:zloty')}</label>
                                                                    </div>
                                                                </div>
                                                            ))}
                                                        </div>
                                                    ))}
                                                </div>

                                                <nav className="tabs mt-4">
                                                    <ul style={{ justifyContent: 'flex-end' }}>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('accommodation')}}>{t('goTravelNamespace3:back')}</a></li>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('additionalInformation')}}>{t('goTravelNamespace3:next')}</a></li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    }
                                    {key === 'additionalInformation' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '1000px', height: '300px', marginLeft: '10%', marginRight: '10%'}}>
                                            <div style={{textAlign: 'center'}}>
                                                <form className="row gy-2 gx-3 align-items-center">
                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('goTravelNamespace3:numberOfAdults')}</label>
                                                                </div>
                                                                <div className="col colReservation w-50">
                                                                    <input type="number" className="form-control" min={"0"}
                                                                           placeholder={"0"} style={{width: '10rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.numberOfAdults}
                                                                           onChange={(e) => this.setState({numberOfAdults: e.target.value, changesNotSaved: true})}/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('goTravelNamespace3:numberOfChildren')}</label>
                                                                </div>
                                                                <div className="col colReservation w-50">
                                                                    <input type="number" className="form-control" min={"0"}
                                                                           placeholder={"0"} style={{width: '10rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.numberOfChildren}
                                                                           onChange={(e) => this.setState({numberOfChildren: e.target.value, changesNotSaved: true})}/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('goTravelNamespace3:departureDate')}</label>
                                                                </div>
                                                                <div className="col colReservation w-50">
                                                                    <input type="date" className="form-control"
                                                                           style={{width: '10rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           min={new Date().toISOString().split('T')[0]}
                                                                           value={this.state.departureDate}
                                                                           onChange={(e) => this.setState({departureDate: this.formatDate(e.target.value), changesNotSaved: true})}/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('numberOfDays')}</label>
                                                                </div>
                                                                <div className="col colReservation w-50">
                                                                    <input type="number" className="form-control" min={"0"}
                                                                           placeholder={"0"} style={{width: '10rem'}}
                                                                           value={this.state.numberOfDays}
                                                                           aria-describedby="passwordHelpInline"
                                                                           onChange={(e) => this.setState({numberOfDays: e.target.value, changesNotSaved: true})}/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('insurance')}</label>
                                                                </div>
                                                                <div className="col colReservation w-50">
                                                                    <select className="form-control search-slt"
                                                                            style={{ width: '15rem', borderRadius: '5px' }}
                                                                            value={this.state.selectedInsuranceId}
                                                                            onChange={(e) => {this.setState({selectedInsuranceId: e.target.value, changesNotSaved: true});}}>
                                                                        {this.state.insurances.map(insurance =>
                                                                            <option key={insurance.idInsurance} value={insurance.idInsurance}>{t(insurance.name)}</option>
                                                                        )}
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className={"col"}></div>
                                                    </div>
                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <div className={"form-check"}>
                                                                        <input className={"form-check-input"} type={"checkbox"}
                                                                               value={this.state.food}
                                                                               checked={this.state.food}
                                                                               onChange={(e) => {
                                                                                   this.setState({food: e.target.checked, changesNotSaved: true})
                                                                               }} style={{marginLeft: '90px'}}/>
                                                                        <label className={"form-check-label"}><span
                                                                            data-bs-toggle="tooltip"
                                                                            title={t('goTravelNamespace3:foodInformation')}>{t('goTravelNamespace3:food')} *</span></label>
                                                                    </div>
                                                                </div>
                                                                <div className="col colReservation w-50">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                </div>
                                                                <div className="col colReservation w-50">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </form>
                                                <nav className="tabs mt-4">
                                                    <ul style={{ justifyContent: 'flex-end' }}>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('attractions')}}>{t('goTravelNamespace3:back')}</a></li>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('summary')}}>{t('goTravelNamespace3:next')}</a></li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    }
                                    {key === 'summary' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '1000px', marginLeft: '10%', marginRight: '10%', overflowY: 'scroll', height: '500px'}}>
                                            <div style={{textAlign: 'center'}}>
                                                <div style={{textAlign: 'center'}}>
                                                    <p>{t('goTravelNamespace3:personalData')} <section className='d-flex justify-content-center justify-content-lg-between p-1 border-bottom'  style={{width: '900px'}}></section></p>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('firstname')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.firstname} disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('lastname')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.lastname} disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">Email</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.email} disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace2:phoneNumber')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.phoneNumber} disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace2:city')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.city} disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:address')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={ this.state.userInfo.street + ' ' + this.state.userInfo.streetNumber + ' ' + this.state.userInfo.zipCode}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <p style={{marginTop: '10px'}}>{t('goTravelNamespace3:reservationDetails')} <section className='d-flex justify-content-center justify-content-lg-between p-1 border-bottom' style={{width: '900px'}}></section></p>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('country')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={t('goTravelNamespace2:'+ this.state.countries.find(country => country.idCountry === parseInt(this.state.selectedCountryId))?.nameCountry) + ' - ' +
                                                                       t('goTravelNamespace2:'+this.state.cities.find(city => city.idCity === parseInt(this.state.selectedCityId))?.nameCity)}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:food')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={t('goTravelNamespace3:'+this.state.food)}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('numberOfDays')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.numberOfDays}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:accommodation')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={t('goTravelNamespace2:'+this.state.accommodations.find(accommodation => accommodation.idAccommodation === parseInt(this.state.selectedAccommodationId))?.nameAccommodation)}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:typeOfRoom')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <div className="row mt-1">
                                                                {this.state.rooms.map(({ type, quantity }) =>
                                                                    (type !== '' && quantity !== '') ? (
                                                                        <div className="col" key={type} style={{marginBottom: '10px'}}>
                                                                            <div className="d-flex align-items-center">
                                                                                <input type="text" className="form-control" style={{ width: '20rem', marginRight: '10px' }} aria-describedby="passwordHelpInline" key={type} value={t('goTravelNamespace2:'+type)} disabled />
                                                                                <label className="col-form-label">{t('goTravelNamespace3:quantity')}</label>
                                                                                <input type="text" className="form-control" style={{ width: '5rem', marginLeft: '5px' }} aria-describedby="passwordHelpInline" key={type} value={quantity} disabled />
                                                                            </div>
                                                                        </div>
                                                                    ) : null
                                                                )}
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:numberOfAdults')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.numberOfAdults}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:numberOfChildren')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.numberOfChildren}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:departureDate')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.departureDate}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:dateOfReturn')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.returnTripFormat(this.state.departureDate)}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace2:attractions')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            {checkedAttractionsName.map(name =>
                                                                <div className={"mt-1"} key={name}>
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline" key={name}
                                                                           value={t('goTravelNamespace2:'+this.state.attractions.find(attraction => attraction.idAttraction === parseInt(name))?.nameAttraction)} disabled/>
                                                                </div>
                                                            )}
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('insurance')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={t(this.state.insurances.find(ins => ins.idInsurance === parseInt(this.state.selectedInsuranceId))?.name)}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <div className="row g-1 align-items-center mt-2"
                                                         style={{width: '350px'}}>
                                                        <div className="col colReservation"
                                                             style={{width: '150px', marginRight: '4%'}}>
                                                            <label className="col-form-label">{t('goTravelNamespace3:totalCost')}</label>
                                                        </div>
                                                        <div className="col colReservation" style={{width: '200px'}}>
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.price}
                                                                   disabled/>
                                                        </div>
                                                    </div>
                                                    <button className="btn btn-primary reservation mt-4" type="submit" onClick={this.handlePostOwnOffer}>{t('goTravelNamespace3:finish')}</button>

                                                </div>
                                                <nav className="tabs mt-4">
                                                    <ul style={{ justifyContent: 'flex-end' }}>
                                                        <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('additionalInformation')}}>{t('goTravelNamespace3:back')}</a></li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    }
                                </div>
                            </div>
                            <Modal size={"lg"} show={this.state.showModalReservationConfirmation} onHide={this.handleCloseModalReservationConfirmation}>
                                <Modal.Header closeButton>
                                    <Modal.Title style={{fontFamily: "Comic Sans MS"}}>{t('goTravelNamespace3:reservationConfirmation')}</Modal.Title>
                                </Modal.Header>
                                <Modal.Body style={{fontFamily: "Comic Sans MS"}}>
                                    {this.state.isError &&
                                        <Message negative  className={"mt-3 messageError"} style={{fontFamily: "Comic Sans MS", width: '400px'}}>{this.state.errorMessage}</Message>}
                                </Modal.Body>
                                <Modal.Footer>
                                    <Button variant="secondary" onClick={this.handleCloseModalReservationConfirmation} style={{fontFamily: "Comic Sans MS"}}>
                                        {t('goTravelNamespace3:close')}
                                    </Button>
                                </Modal.Footer>
                            </Modal>
                            <Modal size={"lg"} show={this.state.bookedCorrectlyVisible} onHide={this.handleCloseModalBookedCorrectly}>
                                <Modal.Header closeButton>
                                    <Modal.Title style={{fontFamily: "Comic Sans MS"}}>{t('goTravelNamespace3:reservationConfirmation')}</Modal.Title>
                                </Modal.Header>
                                <Modal.Body style={{fontFamily: "Comic Sans MS"}}>
                                    <BsCheck2Circle style={{width: '100px', height: '100px', color: '#52d113'}}/>
                                    {t('goTravelNamespace3:'+this.state.message)}
                                </Modal.Body>
                                <Modal.Footer>
                                    <Button variant="secondary" onClick={this.handleCloseModalBookedCorrectly} style={{fontFamily: "Comic Sans MS"}}>
                                        {t('goTravelNamespace3:close')}
                                    </Button>
                                    <StripeButton price={this.state.price} email={this.state.userInfo.email} user={this.state.user} type={"ownOffer"} id={this.state.idOwnOffer}/>
                                </Modal.Footer>
                            </Modal>
                        </div>
                    </section>
                </header>
                <Footer/>
            </div>
        )
    }
}

export default withTranslation()(YourOwnOffer);