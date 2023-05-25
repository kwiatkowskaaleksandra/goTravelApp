import React, {Component} from "react";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import './YourOwnOffer.css'
import {Container, Modal} from "react-bootstrap";
import AuthContext from "../../others/AuthContext";
import {orderApi} from "../../others/OrderApi";
import {BsInfoCircle} from "react-icons/bs";
import Button from "react-bootstrap/Button";
import {handleLogError} from "../../others/Helpers";


class YourOwnOffer extends Component{

    static contextType = AuthContext

    state = {
        userId: 0,
        userInfo: [],
        userUsername: '',

        countries: [],
        selectedCountry: '',
        selectedCountryId: 0,

        cities: [],
        selectedCity: '',
        selectedCityId: 0,

        accommodations: [],
        selectedAccommodation: '',
        selectedAccommodationId: 0,

        typeOfRooms: [],
        singleRoom: 0,
        twoPersonRoom: 0,
        roomWithTwoSingleBeds: 0,
        tripleRoom: 0,
        apartment: 0,

        attractions: [],

        stepThree: false,
        stepFour: false,
        stepFive: false,
        stepSix: false,

        showModal: false,
        checkedItems: {},
        numberOfRooms: {},
        checkedAttractions: {},

        numberOfAdults: '0',
        numberOfChildren: '0',
        departureDate: '',
        numberOfDays: '0',
        food: false,

        selectedTypeOfRoom: {},

        isError: false,
        errorMessage: '',
        price: 0
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if(user != null){
            this.setState({isUserLogin: true, userId: user.id})
            orderApi.getUserInfo(user).then(res => {
                this.setState({
                    userInfo: res.data,
                    userId: res.data.id,
                    userUsername: res.data.username
                })
            })
        }
        this.handleGetCountries()
        this.handleGetAccommodations()
        this.handleGetTypeOfRooms()
        this.handleGetAttractions()
    }

    handleGetAttractions = () => {
        orderApi.getAllAttractions().then(res => {
            this.setState({attractions: res.data})
        })
    }

    handleGetTypeOfRooms = () => {
        orderApi.getAllTypeOfRoom().then(res => {
            this.setState({typeOfRooms: res.data})
        })
    }

    handleGetAccommodations = () => {
        orderApi.getAccommodations().then(res => {
            this.setState({accommodations: res.data})
        })
    }

    handleGetCountries = () => {
        orderApi.getCountries().then(res => {
            this.setState({countries: res.data})
        })
    }

    handleChangeCountry = (e) => {
        this.setState({selectedCountry: e.target.value,  stepThree: false})
        if(e.target.value === 'Wybierz'){
            this.setState({
                stepThree: false,
                stepFour: false,
                stepFive: false,
                stepSix: false,
                cities: []
            })
        }
            this.state.countries.map(country => {
                if (country.nameCountry === e.target.value) {
                    this.setState({selectedCountryId: country.idCountry, selectedCountry: e.target.value})
                    orderApi.getCitiesByIdCountry(country.idCountry).then(response => {
                        this.setState({cities: response.data})
                    })
                }
            })
    }

    handleChangeAccommodation = (e) => {
        this.setState({selectedAccommodation: e.target.value})
        if(e.target.value === 'Wybierz'){
            this.setState({
                stepFour: false,
                stepFive: false,
                stepSix: false,
                selectedAccommodationId: 0
            })
        }
        this.state.accommodations.map(accommodation => {
            if(accommodation.nameAccommodation === e.target.value){
                this.setState({selectedAccommodationId: accommodation.idAccommodation})
            }
        })
    }

    handleChangeCity = (e) => {
        this.render()
        this.setState({
            selectedCity: e.target.value,
        })

        if( e.target.value === 'Wybierz'){
            this.setState({
                stepThree: false,
                stepFour: false,
                stepFive: false,
                stepSix: false
            })
        }else{
            this.setState({
                stepThree: true,
                selectedCity: e.target.value
            })
            this.state.cities.map(city => {
                if(city.nameCity === e.target.value){
                    this.setState({selectedCityId: city.idCity})
                }
            })
        }
    }

    sectionStyleStepThree = () => {
        return this.state.stepThree ? { "display": "block" } : { "display": "none" }
    }
    sectionStyleStepFour = () => {
        return this.state.stepFour ? { "display": "block" } : { "display": "none" }
    }
    sectionStyleStepFive = () => {
        return this.state.stepFive ? { "display": "block" } : { "display": "none" }
    }
    sectionStyleStepSix = () => {
        return this.state.stepSix ? { "display": "block" } : { "display": "none" }
    }

    typeOfRoomsStep = () => {
        return this.state.selectedAccommodationId ? { "display": "block" } : { "display": "none" }
    }

    handleCloseModal = () => {
        this.setState({ showModal: false });
    };

    handleShowModal = () => {
        this.setState({ showModal: true });
    };

    handleCheckboxChangeAttractions = (e) => {
        const {name, checked} = e.target;
        this.setState(prevState => ({
            checkedAttractions:{
                ...prevState.checkedAttractions,
                    [name]: checked
            }
        }), this.checkIfAnyCheckboxCheckedAttractions);
    }

    checkIfAnyCheckboxCheckedAttractions = (e) => {
        const { checkedAttractions } = this.state;
        const anyCheckboxChecked = Object.values(checkedAttractions).some(checked => checked);
        if (!anyCheckboxChecked) {
            this.setState({checkedAttractions: {}})
        }
    }

    handleCheckboxChangeTypeOfRooms = (e) => {
        const {name, checked} = e.target
        this.setState(prevState => ({
            checkedItems:{
                ...prevState.checkedItems,
                [name]: checked
            }
        }), this.checkIfAnyCheckboxChecked);
    }

    checkIfAnyCheckboxChecked = () => {
        const { checkedItems } = this.state;
        const anyCheckboxChecked = Object.values(checkedItems).some(checked => checked);

        if (!anyCheckboxChecked) {
            this.setState({
                stepFour: false,
                stepFive: false,
                stepSix: false
            });
        } else {
            this.setState({
                stepFour: true,
                stepFive: true
            });
        }
    }

    handleNumberInputChange = (e) => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            numberOfRooms: {
                ...prevState.numberOfRooms,
                [name]: value
            }
        }));
    }

    handleChangeDepartureDate = (e) => {
        this.setState({departureDate: this.formatDate(e.target.value)})
        if(this.state.numberOfDays !== '0' && this.state.numberOfAdults !== '0' && e.target.value !== ''){
            this.setState({stepSix: true})
        }else{
            this.setState({stepSix: false})
        }
    }

    handleNumberOfDaysChange = (e) => {
        this.setState({numberOfDays: e.target.value})
        if(this.state.departureDate !== '' && this.state.numberOfAdults !== '0' && e.target.value !== '0'){
            this.setState({stepSix: true})
        }else{
            this.setState({stepSix: false})
        }
    }

    handleNumberOfChildrenChange = (e) => {
        this.setState({numberOfChildren: e.target.value})
        if(this.state.departureDate !== '' && this.state.numberOfDays !== '0' && this.state.numberOfAdults !== '0'){
            this.setState({stepSix: true})
        }else{
            this.setState({stepSix: false})
        }
    }

    handleNumberOfAdultsChange = (e) => {
        this.setState({numberOfAdults: e.target.value})
        if(this.state.departureDate !== '' && this.state.numberOfDays !== '0' && e.target.value !== '0'){
            this.setState({stepSix: true})
        }else{
            this.setState({stepSix: false})
        }
    }

    formatDate = (date) => {
        const parts = date.split('.');
        const reversed = parts.reverse();
        return reversed.join('-')
    }

    returnTripFormat = (date) => {
        if(date !== ''){
            const newDate = new Date(date);
            newDate.setDate(newDate.getDate() + parseInt(this.state.numberOfDays));
            return newDate.toISOString().slice(0,10);
        }
    }

    totalCost = () => {
        const {checkedItems, numberOfRooms} = this.state;
        const numberInputChangeNames = Object.entries(checkedItems)
            .filter(([key,value]) => value === true)
            .map(([key, value]) => ({room: key, quantity: numberOfRooms[key] || 0}));

        const checkedAttractionsName = Object.keys(this.state.checkedAttractions)
        let priceAcc = 0.0, totalPrice = 0.0, priceAtt = 0.0;
        this.state.accommodations.map(acc => {
            if(acc.idAccommodation === this.state.selectedAccommodationId){
                numberInputChangeNames.map(({room, quantity}) => {
                    if(quantity !== 0) {
                        this.state.typeOfRooms.map(type => {
                            if(type.type === room){
                                priceAcc += (acc.priceAccommodation + type.roomPrice) * quantity
                            }
                        })
                    }
                })
            }
        })

        this.state.attractions.map(atr => {
            checkedAttractionsName.map(checked => {
                if(atr.nameAttraction === checked){
                    priceAtt += atr.priceAttraction
                }
            })
        })

        if(this.state.food === true){
            totalPrice += (priceAcc * this.state.numberOfDays) + (this.state.numberOfChildren * (priceAtt / 2)) + (this.state.numberOfAdults * priceAtt) + (this.state.numberOfChildren * (50/2) * this.state.numberOfDays) + (this.state.numberOfAdults * 50 * this.state.numberOfDays)
        }else{
            totalPrice += (priceAcc * this.state.numberOfDays) + (this.state.numberOfChildren * (priceAtt / 2)) + (this.state.numberOfAdults * priceAtt)
        }

        this.setState({price: totalPrice})
    }

    handlePostOwnOffer = () => {
        const {checkedItems, numberOfRooms} = this.state;
        const numberInputChangeNames = Object.entries(checkedItems)
            .filter(([key,value]) => value === true)
            .map(([key, value]) => ({room: key, quantity: numberOfRooms[key] || 0}));

        const checkedAttractionsName = Object.keys(this.state.checkedAttractions)

        const ownOffer = {
            departureDate: this.state.departureDate,
            food: this.state.food,
            numberOfAdults: this.state.numberOfAdults,
            numberOfChildren: this.state.numberOfChildren,
            numberOfDays: this.state.numberOfDays,
            totalPrice: this.state.price,
            offerAccommodation: {
                idAccommodation: this.state.selectedAccommodationId
            },
            offerCity: {
                idCity: this.state.selectedCityId
            }
        }


        orderApi.postOwnOffer(this.state.userUsername, ownOffer).then(() => {
            numberInputChangeNames.map(({room, quantity}) => {
                const ownOfferTypeOfRooms = {
                    numberOfRoom: quantity
                }
                if(quantity !== 0) {
                    orderApi.postOwnOfferTypOfRooms(room, ownOfferTypeOfRooms).then(() => {})
                }
            })

            checkedAttractionsName.map(atr => {
                orderApi.postAttractionOwnOffer(atr).then(() => {})
            })

            window.location.href="/"
        }).catch(error => {
            handleLogError(error)
            const errorData = error.response.data
            console.log("ERROR: " + errorData.message)
            this.setState({isError: true, errorMessage: errorData.message})
        })
    }

    render() {

        const tabs = document.querySelectorAll(".my-tabs .tabs li");
        const sections = document.querySelectorAll(".my-tabs .tab-content");

        tabs.forEach(tab => {
            tab.addEventListener("click", e => {
                e.preventDefault();
                removeActiveTab();
                addActiveTab(tab);
            });
        })

        const removeActiveTab = () => {
            tabs.forEach(tab => {
                tab.classList.remove("is-active");
            });
            sections.forEach(section => {
                section.classList.remove("is-active");
            });
        }

        const addActiveTab = tab => {
            tab.classList.add("is-active");
            const href = tab.querySelector("a").getAttribute("href");
            const matchingSection = document.querySelector(href);
            matchingSection.classList.add("is-active");
        }
        const checkedAttractionsName = Object.keys(this.state.checkedAttractions)
        const {checkedItems, numberOfRooms} = this.state;
        const numberInputChangeNames = Object.entries(checkedItems)
            .filter(([key,value]) => value === true)
            .map(([key, value]) => ({room: key, quantity: numberOfRooms[key] || 0}));

        return(
            <div>
                <NavigationBar/>
                <header className={"head"}>
                    <section>
                        <Container style={{height: '600px', display: 'flex'}}>

                            <div className="card-body">
                                <div className="my-tabs">
                                    <nav className="tabs">
                                        <ul>
                                            <li className="is-active"><a href="#tab-one">Informacje ogólne</a></li>
                                            <li><a href="#tab-two">Podstawowe dane o wycieczce</a></li>
                                            <li style={this.sectionStyleStepThree()}><a href="#tab-three">Zakwaterowanie</a></li>
                                            <li style={this.sectionStyleStepFour()}><a href="#tab-four">Atrakcje</a></li>
                                            <li style={this.sectionStyleStepFive()}><a href="#tab-five">Dodatkowe informacje</a></li>
                                            <li style={this.sectionStyleStepSix()}><a href="#tab-six" onClick={this.totalCost}>Podsumowanie</a></li>
                                        </ul>
                                    </nav>

                                    <section className="tab-content is-active " id="tab-one" style={{marginLeft: '10%', marginRight: '10%'}}>
                                        <p className={"infoOwnOffer"}>Biuro podróży GoTravel oferuje możliwość utorzenia wycieczki według własnych kryteriów. Poniżej znajdują się najważniejsze informacje związane z tą funkcjonalnością.</p>
                                        <ul style={{fontFamily: 'Century Gothic', fontSize: '17px', textAlign: 'justify', margin: '10px 0'}}>
                                            <li>Aby prawidłowo utworzyć i zarezerwować samodzielnie utworzoną wycieczkę, należy wcześniej uzupełnić wszytskie dane związane z miejscem zamieszkania oraz numerem telefonu znajdujące się w zakładce mojego profilu. </li>
                                            <li>W kolejnych krokach należy uzupełnić wszytskie potrzebne informacje dotyczące wycieczki. Aby przejść do kolejnej zakładki, należy uzupełnić wszytskie potrzebne dane w aktywnej zakładce. </li>
                                            <li>Wszystkie wprowadzone informacje zostaną zestawione w ostatniej zakładce, przed potwierdzeniem rezerwacji należy dokładnie sprawdzić wszystkie dane.</li>
                                        </ul>

                                        <nav className="tabs"  style={{marginTop: '20%'}}>
                                            <ul>
                                                <li><a href="#tab-two">Dalej</a></li>
                                            </ul>
                                        </nav>

                                    </section>

                                    <section className="tab-content" id="tab-two" style={{marginLeft: '10%', marginRight: '10%'}}>
                                        <div className={"ownTrip"}>
                                            <p className={"filterNameOwnTrip"}>Kraj: </p>
                                            <select className="form-control search-slt" id="exampleFormControlSelect1"
                                                    name={"selectedTransport"} onChange={this.handleChangeCountry}>

                                                <option>Wybierz</option>
                                                {this.state.countries.map(country =>
                                                    <option key={country.idCountry}>{country.nameCountry}</option>
                                                )}
                                            </select>

                                            <p className={"filterNameOwnTrip"}>Miasto: </p>
                                            <select className="form-control search-slt" id="exampleFormControlSelect1"
                                                    name={"selectedTransport"} onChangeCapture={this.handleChangeCity}>

                                                <option>Wybierz</option>
                                                {this.state.cities.map(city =>
                                                    <option key={city.idCity}>{city.nameCity}</option>
                                                )}
                                            </select>

                                        </div>
                                        <nav className="tabs" style={{marginTop: '20%'}}>
                                            <ul>
                                                <li> <a style={{ textDecoration: 'none' }} href="#tab-one">Wstecz</a></li>
                                                <li  style={this.sectionStyleStepThree()}> <a style={{ textDecoration: 'none' }} href="#tab-three">Dalej</a></li>
                                            </ul>
                                        </nav>
                                    </section>

                                    <section className="tab-content" id="tab-three"  style={{marginLeft: '10%', marginRight: '10%'}}>

                                        <div className={"ownTrip"}>
                                            <p className={"filterNameOwnTrip"}>Zakwaterowanie: <BsInfoCircle  onClick={this.handleShowModal}/></p>
                                            <select className="form-control search-slt" id="exampleFormControlSelect1"
                                                    name={"selectedTransport"} onChange={this.handleChangeAccommodation}>

                                                <option>Wybierz</option>
                                                {this.state.accommodations.map(accommodation =>
                                                    <option key={accommodation.idAccommodation}>{accommodation.nameAccommodation}</option>
                                                )}
                                            </select>
                                            <div style={this.typeOfRoomsStep() || {height: '140px'}}>
                                                {this.state.typeOfRooms.map(typeOfRoom =>
                                                    <div className={"row mt-4"} key={typeOfRoom.idTypeOfRoom}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col w-50">
                                                                    <div className={"form-check"}>
                                                                        <input className="form-check-input" type="checkbox" value={typeOfRoom.type} id={typeOfRoom.type} name={typeOfRoom.type} checked={this.state.checkedItems[typeOfRoom.type] || false} onChange={this.handleCheckboxChangeTypeOfRooms}/>
                                                                        <label className="form-check-label" htmlFor={typeOfRoom.type}>{typeOfRoom.type}</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20" >
                                                                    <label className="col-form-label">Ilość pokojów</label>
                                                                </div>
                                                                <div className="col  w-50" >
                                                                    <input type="number" min={"0"} className="form-control" placeholder={"0"} style={{width: '10rem'}} aria-describedby="passwordHelpInline" name={typeOfRoom.type} value={this.state.numberOfRooms[typeOfRoom.type] || ''} onChange={this.handleNumberInputChange} />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                )}
                                            </div>
                                        </div>

                                            <nav className="tabs" style={{marginTop: '5%'}}>
                                                <ul>
                                                    <li> <a style={{ textDecoration: 'none' }} href="#tab-two">Wstecz</a></li>
                                                    <li  style={this.sectionStyleStepFour()}> <a style={{ textDecoration: 'none' }} href="#tab-four">Dalej</a></li>
                                                </ul>
                                            </nav>

                                        <Modal show={this.state.showModal} onHide={this.handleCloseModal}>
                                            <Modal.Header closeButton>
                                                <Modal.Title>Zakwaterowanie</Modal.Title>
                                            </Modal.Header>
                                            <Modal.Body style={{textAlign:'justify'}}>
                                                Cena zakwaterowania jest zależna od standardu hotelu.<br/>
                                                Podane poniżej ceny odnoszą się do jednej osoby. <br/>
                                                <table className={"table"}>
                                                    <thead>
                                                    <tr>
                                                        <th scope={"col"} style={{textAlign:'center'}}>Standard</th>
                                                        <tr>
                                                            <th scope={"col"} style={{textAlign:'center'}}>Rodzaj pokoju</th>
                                                            <th scope={"col"} style={{textAlign:'center'}}>Cena</th>
                                                        </tr>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    {this.state.accommodations.map(accommodation =>
                                                    <tr key={accommodation.idAccommodation}>
                                                        <th scope={"row"}  style={{fontWeight: 'normal', textAlign:'center'}}>{accommodation.nameAccommodation}</th>
                                                        {this.state.typeOfRooms.map(typeOfRoom =>
                                                            <tr key={typeOfRoom.idTypeOfRoom}>
                                                                <th style={{fontWeight: 'normal', textAlign:'center', width: '80px'}}>{typeOfRoom.type}</th>
                                                                <td style={{fontWeight: 'normal', textAlign:'center', width: '70px'}}>{accommodation.priceAccommodation + typeOfRoom.roomPrice}</td>
                                                            </tr>
                                                        )}
                                                    </tr>
                                                    )}
                                                    </tbody>
                                                </table>
                                            </Modal.Body>
                                            <Modal.Footer>
                                                <Button variant="secondary" onClick={this.handleCloseModal}>
                                                    Wróć
                                                </Button>
                                            </Modal.Footer>
                                        </Modal>
                                    </section>

                                    <section className="tab-content" id="tab-four" style={{marginLeft: '10%', marginRight: '10%'}}>
                                        <div className={"ownTrip"}>
                                            <p className={"filterNameOwnTrip"}>Atrakcje:</p>
                                            <div>
                                                {this.state.attractions.map(attraction =>
                                                    <div className={"row mt-4"} key={attraction.idAttraction}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col w-50">
                                                                    <div className={"form-check"}>
                                                                        <input className="form-check-input" type="checkbox" value={attraction.nameAttraction} id={attraction.nameAttraction} name={attraction.nameAttraction} checked={this.state.checkedAttractions[attraction.nameAttraction]} onChange={this.handleCheckboxChangeAttractions}/>
                                                                        <label className="form-check-label" htmlFor={attraction.nameAttraction}>{attraction.nameAttraction}   -   {attraction.priceAttraction} zł</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                )}
                                            </div>
                                        </div>

                                        <nav className="tabs" style={{marginTop: '5%'}}>
                                            <ul>
                                                <li> <a style={{ textDecoration: 'none' }} href="#tab-three">Wstecz</a></li>
                                                <li  style={this.sectionStyleStepFive()}> <a style={{ textDecoration: 'none' }} href="#tab-five">Dalej</a></li>
                                            </ul>
                                        </nav>
                                    </section>

                                    <section className="tab-content" id="tab-five"  style={{marginLeft: '10%', marginRight: '10%'}}>
                                        <form className="row gy-2 gx-3 align-items-center">
                                            <div className={"row mt-4"} >
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20" >
                                                            <label className="col-form-label">Liczba dzieci</label>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" className="form-control" min={"0"}  placeholder={"0"} style={{width: '10rem'}} aria-describedby="passwordHelpInline" onChange={this.handleNumberOfChildrenChange} />
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20" >
                                                            <label className="col-form-label">Liczba dorosłych</label>
                                                        </div>
                                                        <div className="col colReservation w-50" >
                                                            <input type="number" className="form-control" min={"0"} placeholder={"0"} style={{width: '10rem'}} aria-describedby="passwordHelpInline"  onChange={this.handleNumberOfAdultsChange}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className={"row mt-4"} >
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20" >
                                                            <label className="col-form-label">Data wyjazdu</label>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <input type="date" className="form-control" style={{width: '10rem'}} aria-describedby="passwordHelpInline"  onChange={this.handleChangeDepartureDate}/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20" >
                                                            <label className="col-form-label">Ilość dni</label>
                                                        </div>
                                                        <div className="col colReservation w-50" >
                                                            <input type="number" className="form-control" min={"0"} placeholder={"0"} style={{width: '10rem'}} aria-describedby="passwordHelpInline"  onChange={this.handleNumberOfDaysChange}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className={"row mt-4"} >
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20" >
                                                            <div className={"form-check"}>
                                                                <input className={"form-check-input"} type={"checkbox"} onChange={(e) => {this.setState({food: e.target.checked})}} style={{marginLeft: '90px'}}/>
                                                                <label className={"form-check-label"}><span data-bs-toggle="tooltip" title="Wyżywienie obejmuje śniadanie-obiad-kolację. Koszt to 50zł/os na dzień.">Wyżywienie *</span></label>
                                                            </div>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20" >
                                                        </div>
                                                        <div className="col colReservation w-50" >
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                        <nav className="tabs" style={{marginTop: '10%'}}>
                                            <ul>
                                                <li> <a style={{ textDecoration: 'none' }} href="#tab-four">Wstecz</a></li>
                                                <li  style={this.sectionStyleStepSix()}> <a style={{ textDecoration: 'none' }} href="#tab-six" onClick={this.totalCost}>Dalej</a></li>
                                            </ul>
                                        </nav>
                                    </section>

                                    <section className="tab-content" id="tab-six"  style={{marginLeft: '10%', marginRight: '10%',overflowY: 'scroll', height: '500px'}}>
                                       <div className={"ownTrip"} >
                                           <div>
                                               <p>Dane osobowe <section className='d-flex justify-content-center justify-content-lg-between p-1 border-bottom'></section></p>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Imię</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.userInfo.firstname} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Nazwisko</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.userInfo.lastname} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Email</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.userInfo.email} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Numer telefonu</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.userInfo.phoneNumber} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Miasto</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.userInfo.city} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Adres</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={'ul.' + this.state.userInfo.street + ' ' + this.state.userInfo.streetNumber + ' ' + this.state.userInfo.zipCode} disabled/>
                                                   </div>
                                               </div>
                                           </div>
                                           <div className={"mt-4"}>
                                               <p>Dane wycieczki <section className='d-flex justify-content-center justify-content-lg-between p-1 border-bottom'></section></p>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Kraj</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.selectedCountry} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Miasto</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.selectedCity} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Hotel</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.selectedAccommodation} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '600px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '2%'}}>
                                                       <label className="col-form-label">Pokoje:</label>
                                                   </div>
                                                   <div className="colReservation mt-2" style={{width: '500px'}}>
                                                       {numberInputChangeNames.map(({room, quantity}) =>
                                                           <div className={"row mt-1"}>
                                                               <div className={"col"}>
                                                               <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" key={room}
                                                                      value={room} disabled/>
                                                               </div>
                                                               <div className="col" style={{display: 'flex', alignItems: 'center'}}>
                                                                   <label>Ilość</label>
                                                               </div>
                                                               <div className={"col"}>
                                                               <input type="text" className="form-control" style={{width: '5rem'}} aria-describedby="passwordHelpInline" key={room}
                                                                      value={quantity} disabled/>
                                                               </div>
                                                           </div>
                                                       )}
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '2%'}}>
                                                       <label className="col-form-label">Atrakcje:</label>
                                                   </div>
                                                   <div className="colReservation mt-2" style={{width: '200px'}}>
                                                       {checkedAttractionsName.map(name =>
                                                           <div className={"mt-1"}>
                                                                   <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" key={name}
                                                                          value={name} disabled/>
                                                           </div>
                                                       )}
                                                   </div>
                                               </div>
                                           </div>
                                           <div className={"mt-4"}>
                                               <p>Informacje dodatkowe <section className='d-flex justify-content-center justify-content-lg-between p-1 border-bottom'></section></p>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Liczba dorosłych</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.numberOfAdults} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Liczba dzieci</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.numberOfChildren} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Data wyjazdu</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.departureDate} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Ilość dni</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.state.numberOfDays} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-2" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">Data powrotu</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '20rem'}} aria-describedby="passwordHelpInline" value={this.returnTripFormat(this.state.departureDate)} disabled/>
                                                   </div>
                                               </div>
                                               <div className="row g-1 align-items-center mt-4" style={{width: '300px'}}>
                                                   <div className="col colReservation" style={{width: '100px', marginRight: '4%'}}>
                                                       <label className="col-form-label">CAŁKOWITY KOSZT:</label>
                                                   </div>
                                                   <div className="col colReservation" style={{width: '200px'}}>
                                                       <input type="text" className="form-control" style={{width: '10rem'}} aria-describedby="passwordHelpInline" value={this.state.price} disabled/>
                                                   </div>
                                               </div>
                                           </div>
                                       </div>
                                        <button className="btn btn-primary reservation mt-4" type="submit" onClick={this.handlePostOwnOffer}
                                        >Zarezerwuj
                                        </button>
                                    </section>
                                </div>
                            </div>
                        </Container>
                    </section>
                </header>
                <Footer/>
            </div>
        )
    }
}
export default YourOwnOffer;