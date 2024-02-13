import '../../others/NavigationBar.css'
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import React, {Component} from "react";
import {Modal, OverlayTrigger, Tab, Tabs} from "react-bootstrap";
import "./ReservationForm.css"
import AuthContext from "../../others/AuthContext";
import {goTravelApi} from "../../others/GoTravelApi";
import Button from "react-bootstrap/Button";
import {handleLogError} from "../../others/JWT";
import {Message} from "semantic-ui-react";
import { BsCheck2Circle } from "react-icons/bs";
import {withTranslation} from "react-i18next";
import Tooltip from 'react-bootstrap/Tooltip';

class ReservationForm extends Component {

    static contextType = AuthContext

    state = {
        idTripSelected: '',
        tripOffer: [],
        city: '',
        country: '',
        transport: '',
        accommodation: '',
        user: [],
        typeOfRoom: [],
        rooms: [{ type: "", quantity: "" }],
        numberOfAdults: 0,
        numberOfChildren: 0,
        departureDate: 0,
        showModal: false,
        isError: false,
        errorMessage: '',
        key: 'personalData',
        userInfo: null,
        bookedCorrectlyVisible: false,
        message: ''
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            goTravelApi.getUserInfo(user).then(res => {
                this.setState({
                    user: res.data,
                    departureDate: this.getCurrentDate(),
                    userInfo: user
                })
            })
        }
        this.state.idTripSelected = document.location.href.split("/").pop()
        this.handleGetTrip()
        this.handleGetTypeOfRoom()
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

    handleGetTrip = () => {
        goTravelApi.getTripById(this.state.idTripSelected).then(res => {
            this.setState({
                tripOffer: res.data,
                city: res.data.tripCity.nameCity,
                country: res.data.tripCity.country.nameCountry,
                transport: res.data.tripTransport.nameTransport,
                accommodation: res.data.tripAccommodation.nameAccommodation
            })
        })
    }

    handleGetTypeOfRoom = () => {
        goTravelApi.getAllTypeOfRoom().then(res => {
            this.setState({typeOfRoom: res.data})
        })
    }

    handleRoomQuantityChange = (index, value) => {
        const rooms = [...this.state.rooms];
        rooms[index].quantity = value;
        this.setState({ rooms });
    }

    addRoom = () => {
        this.setState(prevState => ({
            rooms: [...prevState.rooms, { type: "", quantity: "" }]
        }));
    }

    removeRoom = (index) => {
        this.setState(prevState => ({
            rooms: prevState.rooms.filter((_, i) => i !== index)
        }));
    }

    handleRoomTypeChange = (index, value) => {
        const rooms = [...this.state.rooms];
        rooms[index].type = value;
        this.setState({ rooms });
    }

    handleChangeDepartureDate = (e) => {
        this.setState({departureDate: this.formatDate(e.target.value)})
    }

    handlePostReservation = async () => {
        const {t} = this.props;
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        const reservation = {
            departureDate: this.state.departureDate,
            numberOfAdults: this.state.numberOfAdults,
            numberOfChildren: this.state.numberOfChildren,
            trip: this.state.tripOffer,
            typeOfRoomReservation: this.state.rooms.map(room => ({
                numberOfRoom: room.quantity,
                typeOfRoom: {
                    type: room.type
                }
            }))
        }

        if (this.state.rooms.some(room => room.type === "" || room.type === "choose" || room.quantity === "" || room.quantity === "0")) {
            this.setState({
                isError: true,
                errorMessage: t('goTravelNamespace3:pleaseIndicateTheNumberAndTypeOfRoomsWhenBooking')
            });
            return;
        }

        goTravelApi.createReservation(this.state.userInfo, csrfToken, reservation).then((res) => {
            this.handleCloseModal()
            this.setState({
                bookedCorrectlyVisible: true,
                message: res.data
            })
        }).catch(error => {
            handleLogError(error)
            const errorData = error.response
            this.setState({isError: true, errorMessage: t('goTravelNamespace3:'+errorData.data.message)})
        })
    }

    handleCloseModal = () => {
        this.setState({
            showModal: false,
            isError: false,
            errorMessage: ' '
        });
    };

    handleCloseModalBookedCorrectly = () => {
        this.setState({
            bookedCorrectlyVisible: false,
            message: ''
        });
        window.location.href = "/myProfile/invoices"
    }

    handleShowModal = () => {
        this.setState({showModal: true});
    };

    formatDate = (date) => {
        const parts = date.split('.');
        const reversed = parts.reverse();
        return reversed.join('-')
    }

    returnTripFormat = (date) => {
        if (date !== 0) {
            let newDate = new Date(date.toString());
            newDate.setDate(newDate.getDate() + this.state.tripOffer.numberOfDays);
            return newDate.toISOString().slice(0, 10);
        }
    }

    handleTabSelect = (selectedKey) => {
        this.setState({key: selectedKey});
    };

    render() {
        const {key} = this.state
        const {t} = this.props

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
                                    style={{width: '900px'}}
                                >
                                    <Tab eventKey="personalData" title={<OverlayTrigger placement="top" overlay={<Tooltip>{t('goTravelNamespace3:dataCanBeChangedInProfileSettings')}</Tooltip>}><span>{t('goTravelNamespace3:personalData')+'*'}</span></OverlayTrigger>}></Tab>
                                    <Tab eventKey="tripDetails" title={t('goTravelNamespace3:tripDetails')}/>
                                    <Tab eventKey="reservationDetails" title={t('goTravelNamespace3:reservationDetails')}/>
                                    <Tab eventKey="summary" title={t('goTravelNamespace3:summary')}/>
                                </Tabs>
                                <div className="ml-auto">
                                    {key === 'personalData' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '900px'}}>
                                            <div style={{textAlign: 'center'}}>
                                                <form className="row gy-2 gx-3 align-items-center">

                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('firstname')}</label>
                                                                </div>
                                                                <div className="col colReservation w-80">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.user.firstname} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('lastname')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.user.lastname} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div className={"row mt-4"}>
                                                        <div className={"col mt-3"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">Email</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <label className="visually-hidden"
                                                                           htmlFor="autoSizingInputGroup">Email</label>
                                                                    <div className="input-group" style={{width: '20rem'}}>
                                                                        <div className="input-group-text"
                                                                             style={{width: '15%'}}>@
                                                                        </div>
                                                                        <input type="text" className="form-control"
                                                                               id="autoSizingInputGroup"
                                                                               value={this.state.user.email} disabled/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('goTravelNamespace2:phoneNumber')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.user.phoneNumber} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('goTravelNamespace2:city')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.user.city} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('goTravelNamespace2:street')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.user.street} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('goTravelNamespace2:streetNumber')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.user.streetNumber} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('goTravelNamespace2:zipCode')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.user.zipCode} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <nav className="tabs mt-4">
                                                        <ul style={{ justifyContent: 'flex-end' }}>
                                                            <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('tripDetails')}}>{t('goTravelNamespace3:next')}</a></li>
                                                        </ul>
                                                    </nav>
                                                </form>
                                            </div>
                                        </div>
                                    }
                                    {key === 'tripDetails' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '960px'}}>
                                            <div style={{textAlign: 'center'}}>
                                                <form className="row gy-2 gx-3 align-items-center">

                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('country')}</label>
                                                                </div>
                                                                <div className="col colReservation w-80">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={t('goTravelNamespace2:'+this.state.country)} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('goTravelNamespace2:city')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={t('goTravelNamespace2:'+this.state.city)} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('goTravelNamespace3:food')}</label>
                                                                </div>
                                                                <div className="col colReservation w-80">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={t('goTravelNamespace2:'+this.state.tripOffer.food)} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('numberOfDays')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.tripOffer.numberOfDays} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('goTravelNamespace3:accommodation')}</label>
                                                                </div>
                                                                <div className="col colReservation w-80">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={t('goTravelNamespace2:'+this.state.accommodation)} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation">
                                                                    <label className="col-form-label">{t('transport')}</label>
                                                                </div>
                                                                <div className="col colReservation">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={t('goTravelNamespace1:'+this.state.transport)} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div className={"row mt-4"}>
                                                        <div className={"col"}>
                                                            <div className="row g-2 align-items-center">
                                                                <div className="col colReservation w-20">
                                                                    <label className="col-form-label">{t('goTravelNamespace3:pricePerPerson')}</label>
                                                                </div>
                                                                <div className="col colReservation w-80">
                                                                    <input type="text" className="form-control"
                                                                           style={{width: '20rem'}}
                                                                           aria-describedby="passwordHelpInline"
                                                                           value={this.state.tripOffer.price} disabled/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}></div>
                                                    </div>

                                                    <nav className="tabs mt-4">
                                                        <ul style={{ justifyContent: 'flex-end' }}>
                                                            <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('personalData')}}>{t('goTravelNamespace3:back')}</a></li>
                                                            <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('reservationDetails')}}>{t('goTravelNamespace3:next')}</a></li>
                                                        </ul>
                                                    </nav>

                                                </form>

                                            </div>
                                        </div>
                                    }
                                    {key === 'reservationDetails' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '960px'}}>
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
                                                                           onChange={(e) => {
                                                                               this.setState({numberOfAdults: e.target.value})
                                                                           }}/>
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
                                                                           onChange={(e) => {
                                                                               this.setState({numberOfChildren: e.target.value})
                                                                           }}/>
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
                                                                           onChange={this.handleChangeDepartureDate}/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div className={"col"}></div>
                                                    </div>

                                                    <div className={"row mt-4"}>
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
                                                                        {this.state.typeOfRoom.map(room =>
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
                                                    </div>

                                                    <nav className="tabs">
                                                        <ul style={{ justifyContent: 'flex-end' }}>
                                                            <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('tripDetails')}}>{t('goTravelNamespace3:back')}</a></li>
                                                            <li><a href={"#!"} onClick={(e) => { e.preventDefault(); this.handleTabSelect('summary')}}>{t('goTravelNamespace3:next')}</a></li>
                                                        </ul>
                                                    </nav>
                                                </form>
                                            </div>
                                        </div>
                                    }
                                    {key === 'summary' &&
                                        <div className={"d-flex "} style={{width: '960px', marginLeft: '10%',
                                            marginRight: '10%',
                                            overflowY: 'scroll',
                                            height: '500px'}}>
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
                                                               value={this.state.user.firstname} disabled/>
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
                                                               value={this.state.user.lastname} disabled/>
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
                                                               value={this.state.user.email} disabled/>
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
                                                               value={this.state.user.phoneNumber} disabled/>
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
                                                               value={this.state.user.city} disabled/>
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
                                                               value={ this.state.user.street + ' ' + this.state.user.streetNumber + ' ' + this.state.user.zipCode}
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
                                                               value={t('goTravelNamespace2:'+this.state.country) + ' - ' + t('goTravelNamespace2:'+this.state.city)}
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
                                                               value={t('goTravelNamespace2:'+this.state.tripOffer.food)}
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
                                                               value={this.state.tripOffer.numberOfDays}
                                                               disabled/>
                                                    </div>
                                                </div>
                                                <div className="row g-1 align-items-center mt-2"
                                                     style={{width: '350px'}}>
                                                    <div className="col colReservation"
                                                         style={{width: '150px', marginRight: '4%'}}>
                                                        <label className="col-form-label">{t('transport')}</label>
                                                    </div>
                                                    <div className="col colReservation" style={{width: '200px'}}>
                                                        <input type="text" className="form-control"
                                                               style={{width: '20rem'}}
                                                               aria-describedby="passwordHelpInline"
                                                               value={t('goTravelNamespace1:'+this.state.transport)}
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
                                                               value={t('goTravelNamespace2:'+this.state.accommodation)}
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
                                                        <label className="col-form-label">{t('goTravelNamespace3:pricePerPerson')}</label>
                                                    </div>
                                                    <div className="col colReservation" style={{width: '200px'}}>
                                                        <input type="text" className="form-control"
                                                               style={{width: '20rem'}}
                                                               aria-describedby="passwordHelpInline"
                                                               value={this.state.tripOffer.price}
                                                               disabled/>
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
                                                        <label className="col-form-label">{t('goTravelNamespace3:totalPrice')}</label>
                                                    </div>
                                                    <div className="col colReservation" style={{width: '200px'}}>
                                                        <input type="text" className="form-control"
                                                               style={{width: '20rem'}}
                                                               aria-describedby="passwordHelpInline"
                                                               value={(this.state.numberOfChildren * (this.state.tripOffer.price / 2) + this.state.numberOfAdults * this.state.tripOffer.price)}
                                                               disabled/>
                                                    </div>
                                                </div>

                                                <button className="btn btn-primary reservation" type="submit"
                                                        onClick={this.handleShowModal}>{t('goTravelNamespace3:book')}
                                                </button>

                                                <Modal show={this.state.showModal} onHide={this.handleCloseModal}>
                                                    <Modal.Header closeButton>
                                                        <Modal.Title style={{fontFamily: "Comic Sans MS"}}>{t('goTravelNamespace3:reservationConfirmation')}</Modal.Title>
                                                    </Modal.Header>
                                                    <Modal.Body style={{fontFamily: "Comic Sans MS"}}>
                                                        {t('goTravelNamespace3:areYouSureYouWantToBook')}
                                                    </Modal.Body>
                                                    <Modal.Footer>
                                                        <Button variant="secondary" onClick={this.handleCloseModal} style={{fontFamily: "Comic Sans MS"}}>
                                                            {t('goTravelNamespace3:cancel')}
                                                        </Button>
                                                        <Button variant="primary confirmBtn" onClick={this.handlePostReservation} style={{fontFamily: "Comic Sans MS"}}>
                                                            {t('goTravelNamespace3:confirm')}
                                                        </Button>
                                                        {this.state.isError &&
                                                            <Message negative  className={"mt-3 messageError"} style={{fontFamily: "Comic Sans MS", width: '400px'}}>{this.state.errorMessage}</Message>}
                                                    </Modal.Footer>
                                                </Modal>

                                                <Modal show={this.state.bookedCorrectlyVisible} onHide={this.handleCloseModalBookedCorrectly}>
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
                                                    </Modal.Footer>
                                                </Modal>

                                            </div>
                                        </div>
                                    }
                                </div>
                            </div>
                        </div>
                    </section>
                </header>
                <Footer/>
            </div>
        );
    }
}

export default withTranslation()(ReservationForm);