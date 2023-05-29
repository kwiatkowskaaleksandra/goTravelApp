import '../../others/NavigationBar.css'
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import React, {Component} from "react";
import {Container, Modal} from "react-bootstrap";
import "./ReservationForm.css"
import AuthContext from "../../others/AuthContext";
import {orderApi} from "../../others/OrderApi";
import Button from "react-bootstrap/Button";
import {handleLogError} from "../../others/Helpers";
import {Message} from "semantic-ui-react";
import axios from "axios";

class ReservationForm extends Component {

    static contextType = AuthContext

    state = {
        idTripSelected: '',
        tripOffer: [],
        city: '',
        country: '',
        transport: '',
        accommodation: '',
        isUserLogin: false,
        username: '',
        userInfo: [],
        typeOfRoom: [],
        rooms: [],

        numberOfAdults: 0,
        numberOfChildren: 0,
        departureDate: 0,
        singleRoom: 0,
        twoPersonRoom: 0,
        roomWithTwoSingleBeds: 0,
        tripleRoom: 0,
        apartment: 0,
        showModal: false,
        isError: false,
        errorMessage: '',
    }


    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({isUserLogin: true})
            orderApi.getUserInfo(user).then(res => {
                this.setState({
                    userInfo: res.data,
                    username: res.data.username
                })
            })
        }
        this.state.idTripSelected = document.location.href.split("/").pop()
        this.handleGetTrip()
        this.handleGetTypeOfRoom()
    }

    handleGetTrip = () => {
        orderApi.getTripById(this.state.idTripSelected).then(res => {
            this.setState({tripOffer: res.data})

            this.setState({
                city: res.data.tripCity.nameCity,
                country: res.data.tripCity.country.nameCountry,
                transport: res.data.tripTransport.nameTransport,
                accommodation: res.data.tripAccommodation.nameAccommodation
            })
        })
    }

    handleGetTypeOfRoom = () => {
        orderApi.getAllTypeOfRoom().then(res => {
            this.setState({typeOfRoom: res.data})
        })
    }

    handleChangeDepartureDate = (e) => {
        this.setState({departureDate: this.formatDate(e.target.value)})
    }

    handlePostReservation = () => {
        const reservation = {
            departureDate: this.state.departureDate,
            numberOfAdults: this.state.numberOfAdults,
            numberOfChildren: this.state.numberOfChildren
        }


        if (this.state.singleRoom === 0 && this.state.twoPersonRoom === 0 && this.state.roomWithTwoSingleBeds === 0 && this.state.tripleRoom === 0 && this.state.apartment === 0) {
            this.setState({isError: true, errorMessage: "Proszę wskazać do rezerwacji ilość oraz rodzaj pokoi."})
            return;
        }


        orderApi.csrf().then(res => {
            axios.post("http://localhost:8080/api/reservations/addReservation/" + this.state.username + "/" + this.state.idTripSelected, reservation, {
                withCredentials: true,
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': res.data.token
                }
            }).then(() => {
                if (this.state.singleRoom !== 0) {
                    const reservationsTypOfRooms = {numberOfRoom: this.state.singleRoom}
                    axios.post("http://localhost:8080/api/reservationsTypOfRooms/addReservationsTypOfRooms/"+ 1, reservationsTypOfRooms, {
                        withCredentials: true,
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': res.data.token
                        }}).then(() => {})
                }
                if (this.state.twoPersonRoom !== 0) {
                    const reservationsTypOfRooms = {numberOfRoom: this.state.twoPersonRoom}
                    axios.post("http://localhost:8080/api/reservationsTypOfRooms/addReservationsTypOfRooms/"+ 2, reservationsTypOfRooms, {
                        withCredentials: true,
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': res.data.token
                        }}).then(() => {})
                }
                if (this.state.roomWithTwoSingleBeds !== 0) {
                    const reservationsTypOfRooms = {numberOfRoom: this.state.roomWithTwoSingleBeds}
                    axios.post("http://localhost:8080/api/reservationsTypOfRooms/addReservationsTypOfRooms/"+ 3, reservationsTypOfRooms, {
                        withCredentials: true,
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': res.data.token
                        }}).then(() => {})
                }
                if (this.state.tripleRoom !== 0) {
                    const reservationsTypOfRooms = {numberOfRoom: this.state.tripleRoom}
                    axios.post("http://localhost:8080/api/reservationsTypOfRooms/addReservationsTypOfRooms/"+ 4, reservationsTypOfRooms, {
                        withCredentials: true,
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': res.data.token
                        }}).then(() => {})
                }
                if (this.state.apartment !== 0) {
                    const reservationsTypOfRooms = {numberOfRoom: this.state.apartment}
                    axios.post("http://localhost:8080/api/reservationsTypOfRooms/addReservationsTypOfRooms/"+ 5, reservationsTypOfRooms, {
                        withCredentials: true,
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': res.data.token
                        }}).then(() => {})
                }
                window.location.href = "/"
            }).catch(error => {
                handleLogError(error)
                const errorData = error.response.data
                this.setState({isError: true, errorMessage: errorData.message})
            })
        })
    }

    handleCloseModal = () => {
        this.setState({showModal: false});
        this.setState({isError: false, errorMessage: ' '})
    };

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


        return (
            <div>
                <NavigationBar/>
                <header className={"head"}>
                    <section>
                        <Container className={"containerForm"}>

                            <div className="card-body">
                                <div className="my-tabs">
                                    <nav className="tabs">
                                        <ul>
                                            <li className="is-active"><a href="#tab-one"><span data-bs-toggle="tooltip"
                                                                                               title="Dane mogą zostać zmienione w ustawieniach profilu.">Dane osobowe *</span></a>
                                            </li>
                                            <li><a href="#tab-two">Dane wycieczki</a></li>
                                            <li><a href="#tab-three">Dane do rezerwacji</a></li>
                                            <li><a href="#tab-four">Podsumowanie</a></li>
                                        </ul>
                                    </nav>

                                    <section className="tab-content is-active" id="tab-one">

                                        <form className="row gy-2 gx-3 align-items-center">

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Imię</label>
                                                        </div>
                                                        <div className="col colReservation w-80">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.firstname} disabled/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Nazwisko</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.lastname} disabled/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-4"}>
                                                <div className={"col mt-3"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Adres email</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <label className="visually-hidden"
                                                                   htmlFor="autoSizingInputGroup">Adres email</label>
                                                            <div className="input-group" style={{width: '20rem'}}>
                                                                <div className="input-group-text"
                                                                     style={{width: '15%'}}>@
                                                                </div>
                                                                <input type="text" className="form-control"
                                                                       id="autoSizingInputGroup" placeholder="Username"
                                                                       value={this.state.userInfo.email} disabled/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Numer telefonu</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.phoneNumber} disabled/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Miasto</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.city} disabled/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Ulica</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.street} disabled/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Numer</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.streetNumber} disabled/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Kod pocztowy</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.userInfo.zipCode} disabled/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <nav className="tabs mt-4">
                                                <ul>
                                                    <li><a href="#tab-two" style={{justifyContent: 'center'}}>Dalej</a>
                                                    </li>
                                                </ul>
                                            </nav>
                                        </form>
                                    </section>

                                    <section className="tab-content" id="tab-two">

                                        <form className="row gy-2 gx-3 align-items-center">

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Kraj</label>
                                                        </div>
                                                        <div className="col colReservation w-80">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.country} disabled/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Miasto</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.city} disabled/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Wyżywienie</label>
                                                        </div>
                                                        <div className="col colReservation w-80">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.tripOffer.food} disabled/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Liczba dni</label>
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
                                                            <label className="col-form-label">Nocleg</label>
                                                        </div>
                                                        <div className="col colReservation w-80">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.accommodation} disabled/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation">
                                                            <label className="col-form-label">Transport</label>
                                                        </div>
                                                        <div className="col colReservation">
                                                            <input type="text" className="form-control"
                                                                   style={{width: '20rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   value={this.state.transport} disabled/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Cena za osobę</label>
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
                                                <ul>
                                                    <li className="is-active"><a href="#tab-one"><span>Wstecz</span></a>
                                                    </li>
                                                    <li><a href="#tab-three">Dalej</a></li>
                                                </ul>
                                            </nav>

                                        </form>

                                    </section>

                                    <section className="tab-content" id="tab-three">

                                        <form className="row gy-2 gx-3 align-items-center">

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Liczba dzieci</label>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" className="form-control" min={"0"}
                                                                   placeholder={"0"} style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={(e) => {
                                                                       this.setState({numberOfChildren: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Liczba dorosłych</label>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" className="form-control" min={"0"}
                                                                   placeholder={"0"} style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={(e) => {
                                                                       this.setState({numberOfAdults: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Rodzaj pokoju</label>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <select className="form-control search-slt"
                                                                    style={{width: '15rem'}}
                                                                    id="exampleFormControlSelect1">
                                                                <option>pokój jednoosobowy</option>
                                                                {this.state.typeOfRoom.map(type =>
                                                                    <option key={type.idTypeOfRoom}
                                                                            disabled>{type.type}</option>
                                                                )}
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Ilość pokojów</label>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" min={"0"} className="form-control"
                                                                   placeholder={"0"} style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={(e) => {
                                                                       this.setState({singleRoom: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-2"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">

                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <select className="form-control search-slt"
                                                                    style={{width: '15rem'}}
                                                                    id="exampleFormControlSelect1">
                                                                <option>pokój dwuosobowy</option>
                                                                {this.state.typeOfRoom.map(type =>
                                                                    <option key={type.idTypeOfRoom}
                                                                            disabled>{type.type}</option>
                                                                )}
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20"></div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" min={"0"} className="form-control"
                                                                   placeholder={"0"} style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={(e) => {
                                                                       this.setState({twoPersonRoom: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-2"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20"></div>
                                                        <div className="col colReservation w-50">
                                                            <select className="form-control search-slt"
                                                                    style={{width: '15rem'}}
                                                                    id="exampleFormControlSelect1">
                                                                <option>pokój z dwoma jednoosobowymi łóżkami</option>
                                                                {this.state.typeOfRoom.map(type =>
                                                                    <option key={type.idTypeOfRoom}
                                                                            disabled>{type.type}</option>
                                                                )}
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20"></div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" min={"0"} className="form-control"
                                                                   placeholder={"0"} style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={(e) => {
                                                                       this.setState({roomWithTwoSingleBeds: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-2"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20"></div>
                                                        <div className="col colReservation w-50">
                                                            <select className="form-control search-slt"
                                                                    style={{width: '15rem'}}
                                                                    id="exampleFormControlSelect1">
                                                                <option>pokój trzyosobowy</option>
                                                                {this.state.typeOfRoom.map(type =>
                                                                    <option key={type.idTypeOfRoom}
                                                                            disabled>{type.type}</option>
                                                                )}
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20"></div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" min={"0"} className="form-control"
                                                                   placeholder={"0"} style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={(e) => {
                                                                       this.setState({tripleRoom: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-2"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20"></div>
                                                        <div className="col colReservation w-50">
                                                            <select className="form-control search-slt"
                                                                    style={{width: '15rem'}}
                                                                    id="exampleFormControlSelect1">
                                                                <option>apartament</option>
                                                                {this.state.typeOfRoom.map(type =>
                                                                    <option key={type.idTypeOfRoom}
                                                                            disabled>{type.type}</option>
                                                                )}
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20"></div>
                                                        <div className="col colReservation w-50">
                                                            <input type="number" min={"0"} className="form-control"
                                                                   placeholder={"0"} style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={(e) => {
                                                                       this.setState({apartment: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"row mt-4"}>
                                                <div className={"col"}>
                                                    <div className="row g-2 align-items-center">
                                                        <div className="col colReservation w-20">
                                                            <label className="col-form-label">Data wyjazdu</label>
                                                        </div>
                                                        <div className="col colReservation w-50">
                                                            <input type="date" className="form-control"
                                                                   style={{width: '10rem'}}
                                                                   aria-describedby="passwordHelpInline"
                                                                   onChange={this.handleChangeDepartureDate}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"col"}></div>
                                            </div>

                                            <nav className="tabs">
                                                <ul>
                                                    <li><a href="#tab-two">Wstecz</a></li>
                                                    <li><a href="#tab-four">Dalej</a></li>
                                                </ul>
                                            </nav>

                                        </form>
                                    </section>

                                    <section className="tab-content" id="tab-four">

                                        <div className={"row mt-4"}>
                                            <div className={"col "}>
                                                <div className="row g-2 align-items-center">
                                                    <div className="col colReservation w-30">
                                                        <label className="col-form-label" style={{fontWeight: 'bold'}}>Dane
                                                            rezerwującego:</label>
                                                    </div>
                                                    <div className="col w-80"></div>

                                                    <div className="col colReservation w-30">
                                                        <label className="col-form-label" style={{fontWeight: 'bold'}}>Dane
                                                            wycieczki:</label>
                                                    </div>
                                                    <div className="col w-80"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className={"row"}>
                                            <div className={"col"}>
                                                <div className="row g-2 align-items-center">
                                                    <div className="col w-30"></div>
                                                    <div className="col w-40">
                                                        <label
                                                            className="col-form-label">{this.state.userInfo.firstname} {this.state.userInfo.lastname}</label><br/>
                                                        <label
                                                            className="col-form-label">{this.state.userInfo.email}</label><br/>
                                                        <label
                                                            className="col-form-label">{this.state.userInfo.phoneNumber}</label><br/>
                                                        <label
                                                            className="col-form-label">miasto: {this.state.userInfo.city}</label><br/>
                                                        <label
                                                            className="col-form-label">ulica: {this.state.userInfo.street}</label><br/>
                                                        <label
                                                            className="col-form-label">numer: {this.state.userInfo.streetNumber}</label><br/>
                                                        <label className="col-form-label">kod
                                                            pocztowy: {this.state.userInfo.zipCode}</label><br/>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={"col"}>
                                                <div className="row g-2 align-items-center">
                                                    <div className="col w-30"></div>
                                                    <div className="col w-70">
                                                        <label
                                                            className="col-form-label">{this.state.country} - {this.state.city}</label><br/>
                                                        <label
                                                            className="col-form-label">wyżywienie: {this.state.tripOffer.food}</label><br/>
                                                        <label
                                                            className="col-form-label">{this.state.tripOffer.numberOfDays} dni</label><br/>
                                                        <label
                                                            className="col-form-label">zakwaterowanie: {this.state.accommodation}</label><br/>
                                                        <label
                                                            className="col-form-label">transport: {this.state.transport}</label><br/>
                                                        <label
                                                            className="col-form-label">{this.state.tripOffer.price} zł
                                                            za osobę</label><br/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div className={"row mt-4"}>
                                            <div className={"col "}>
                                                <div className="row g-2 align-items-center">
                                                    <div className="col colReservation w-30">
                                                        <label className="col-form-label" style={{fontWeight: 'bold'}}>Dane
                                                            rezerwacji:</label>
                                                    </div>
                                                    <div className="col w-80"></div>

                                                    <div className="col colReservation w-30"></div>
                                                    <div className="col w-80"></div>
                                                </div>
                                            </div>
                                        </div>

                                        <div className={"row"}>
                                            <div className={"col"}>
                                                <div className="row g-2 align-items-center">

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">liczba
                                                                dzieci:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.numberOfChildren}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">liczba
                                                                dorosłych:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.numberOfAdults}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">data wyjazdu:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.departureDate}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">data powrotu:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.returnTripFormat(this.state.departureDate)}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">liczba pokoi
                                                                jednoosobowych:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.singleRoom}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">liczba pokoi
                                                                dwuosobowych:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.twoPersonRoom}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">liczba pokoi z dwoma
                                                                jednoosobowymi łóżkami:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.roomWithTwoSingleBeds}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">liczba pokoi
                                                                trzyosobowych:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.tripleRoom}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label">liczba
                                                                apartamentów:</label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label
                                                                className="col-form-label">{this.state.apartment}</label><br/>
                                                        </div>
                                                    </div>

                                                    <div className={"row"}>
                                                        <div className="col colReservation w-30">
                                                            <label className="col-form-label"
                                                                   style={{fontWeight: 'bold', fontSize: '20px'}}>cena
                                                                całkowita: </label><br/>
                                                        </div>

                                                        <div className="col w-40">
                                                            <label className="col-form-label" style={{
                                                                fontWeight: 'bold',
                                                                fontSize: '20px'
                                                            }}>{(this.state.numberOfChildren * (this.state.tripOffer.price / 2) + this.state.numberOfAdults * this.state.tripOffer.price)}</label><br/>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>

                                            <div className={"col"}>
                                                <div className="row g-2 align-items-center">
                                                    <div className="col w-30"></div>
                                                    <div className="col w-70"></div>
                                                </div>
                                            </div>
                                        </div>


                                        <button className="btn btn-primary reservation" type="submit"
                                                onClick={this.handleShowModal}>Zarezerwuj
                                        </button>

                                        <Modal show={this.state.showModal} onHide={this.handleCloseModal}>
                                            <Modal.Header closeButton>
                                                <Modal.Title>Potwierdzenie rezerwacji</Modal.Title>
                                            </Modal.Header>
                                            <Modal.Body>
                                                Czy na pewno chcesz zarezerwować?
                                            </Modal.Body>
                                            <Modal.Footer>
                                                <Button variant="secondary" onClick={this.handleCloseModal}>
                                                    Anuluj
                                                </Button>
                                                <Button variant="primary" onClick={this.handlePostReservation}>
                                                    Potwierdź
                                                </Button>
                                            </Modal.Footer>
                                            {this.state.isError &&
                                                <Message negative>{this.state.errorMessage}</Message>}
                                        </Modal>

                                    </section>

                                </div>
                            </div>


                        </Container>
                    </section>
                </header>
                <Footer/>
            </div>
        );
    }
}

export default ReservationForm;