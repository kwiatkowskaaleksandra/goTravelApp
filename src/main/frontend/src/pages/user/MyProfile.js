import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import React, {Component} from "react";
import {Container, Modal,} from "react-bootstrap";
import "./MyProfile.css"
import AuthContext from "../../others/AuthContext";
import {orderApi} from "../../others/OrderApi";
import {CgProfile} from "react-icons/cg";
import {RiLockPasswordLine} from "react-icons/ri";
import {HiOutlineClipboardDocumentList} from "react-icons/hi2";
import {MdOutlineDeleteSweep} from "react-icons/md";
import {handleLogError} from "../../others/Helpers";
import Button from "react-bootstrap/Button";
import {Checkbox, Message} from "semantic-ui-react";
import {IoTrashBin} from "react-icons/io5";

class MyProfile extends Component {

    static contextType = AuthContext

    state = {

        isUserLogin: false,
        usernameID: '',
        userInfo: [],
        reservations: [],
        ownOffers: [],

        username: '',
        firstname: '',
        lastname: '',
        email: '',
        phoneNumber: 0,
        city: '',
        street: '',
        streetNumber: '',
        zipCode: 0,
        isError: false,
        errorMessage: '',
        showModal: false,
        showModalDelete: false,
        showModalVerify: false,
        passwordOld: '',
        passwordNew: '',
        passwordNew2: '',
        using2FA: false,
        qrCode: "",
        verifyButtonShow: false
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({isUserLogin: true})

            orderApi.getUserInfo(user).then(res => {
                this.setState({
                    userInfo: res.data,
                    usernameID: res.data.username,
                    username: res.data.username,
                    firstname: res.data.firstname,
                    lastname: res.data.lastname,
                    email: res.data.email,
                    phoneNumber: res.data.phoneNumber,
                    city: res.data.city,
                    street: res.data.street,
                    streetNumber: res.data.streetNumber,
                    zipCode: res.data.zipCode,
                    using2FA: res.data.using2FA
                })

                this.handleGetReservations(res.data.username)
                this.handleGetOwnOffers(res.data.username)
            })
        }

    }

    handleGetOwnOffers = (username) => {
        orderApi.getOwnOffersByUsername(username).then(res => {
            this.setState({ownOffers: res.data})
        })
    }

    handleGetReservations = (username) => {
        orderApi.getReservationByUser(username).then(res => {
            this.setState({reservations: res.data})
        })
    }

    save = () => {
        const userDetails = {
            username: this.state.username,
            firstname: this.state.firstname,
            lastname: this.state.lastname,
            email: this.state.email,
            phoneNumber: this.state.phoneNumber,
            city: this.state.city,
            street: this.state.street,
            streetNumber: this.state.streetNumber,
            zipCode: this.state.zipCode,
            using2FA: this.state.using2FA
        }

        orderApi.putUserUpdate(this.state.usernameID, userDetails).then((res) => {
            const Auth = this.context

            if(this.state.using2FA === true){
                if(res.data !== ""){
                    this.setState({qrCode: res.data, verifyButtonShow: true})
                }
            }
            else{
                Auth.userLogout()
                window.location.href = "/"
            }
        }).catch(error => {
            handleLogError(error)
            const errorData = error.response.data
            this.handleShowModal()
            let errorMessage = 'Pola zostały błędnie uzupełnione.'
            if (errorData.status === 409) {
                errorMessage = errorData.message
            } else if (errorData.status === 400) {
                errorMessage = errorData.errors[0].defaultMessage
            }
            this.setState({isError: true, errorMessage: errorMessage})
        })
    }

    savePassword = () => {

        const passwords = {
            oldPassword: this.state.passwordOld,
            newPassword: this.state.passwordNew,
            newPassword2: this.state.passwordNew2
        }

        orderApi.putUserPasswordUpdate(this.state.usernameID, passwords).then(() => {
            const Auth = this.context
            Auth.userLogout()
            window.location.href = "/"
        }).catch(error => {
            handleLogError(error)
            const errorData = error.response.data
            this.handleShowModal()
            let errorMessage = 'Pola zostały błędnie uzupełnione.'
            if (errorData.status === 409) {
                errorMessage = errorData.message
            } else if (errorData.status === 400) {
                errorMessage = errorData.errors[0].defaultMessage
            }
            this.setState({isError: true, errorMessage: errorMessage})
        })
    }

    deleteAccount = () => {

        this.showModalDelete()

        orderApi.deleteUser(this.state.usernameID).then(() => {
            const Auth = this.context
            Auth.userLogout()
            handleLogError("konto usuniete")
            window.location.href = "/"
        })
    }

    handleCloseModal = () => {
        this.setState({showModal: false});
        this.setState({isError: false, errorMessage: ' '})
    };

    handleShowModal = () => {
        this.setState({showModal: true});
    };

    showModalDelete = () => {
        this.setState({showModalDelete: true});
    };

    handleCloseModalDelete = () => {
        this.setState({showModalDelete: false});
    };

    handleCloseModalVerify = () => {
        this.setState({showModalVerify: false});
    };

    handleShowModalVerify = () => {
        this.setState({showModalVerify: true});
    }

    showVerifyModal = () => {
        this.handleShowModalVerify()
    }

    deleteOwnOffer = (id) => {
        orderApi.deleteOwnOffer(id).then(() => {
            window.location.reload()
        })
    }

    deleteReservation = (id) => {
        orderApi.deleteReservation(id).then(() => {
            window.location.reload()
        })
    }
    verifyButtonShowStyle = () => {
        return this.state.verifyButtonShow ? {"display": "block"} : {"display": "none"}
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
                                            <li className="is-active"><a href="#tab-one"> <CgProfile/> Dane osobowe</a>
                                            </li>
                                            <li><a href="#tab-two"> <RiLockPasswordLine/> Zmiana hasła</a></li>
                                            <li><a href="#tab-three"> <HiOutlineClipboardDocumentList/> Rezerwacje</a>
                                            </li>
                                            <li><a href="#tab-four"> <HiOutlineClipboardDocumentList/> Własne wycieczki</a>
                                            </li>
                                            <li><a href="#tab-five"> <MdOutlineDeleteSweep/> Usuń konto</a></li>
                                        </ul>

                                    </nav>

                                    <section className="tab-content is-active" id="tab-one">

                                        <form className="form-floating row gy-2 gx-3"
                                              style={{justifyContent: 'center'}}>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text ">Imię</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({firstname: e.target.value})
                                                       }} placeholder={this.state.userInfo.firstname}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text" id="basic-addon1">Nazwisko</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({lastname: e.target.value})
                                                       }} placeholder={this.state.userInfo.lastname}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text" id="basic-addon1">Adres email</span>
                                                <input type="email" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({email: e.target.value})
                                                       }} placeholder={this.state.userInfo.email}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text"
                                                      id="basic-addon1">Nazwa użytkownika</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({username: e.target.value})
                                                       }} placeholder={this.state.userInfo.username}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text"
                                                      id="basic-addon1">Numer telefonu</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({phoneNumber: e.target.value})
                                                       }} placeholder={this.state.userInfo.phoneNumber}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text" id="basic-addon1">Miasto</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({city: e.target.value})
                                                       }} placeholder={this.state.userInfo.city}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text" id="basic-addon1">Ulica</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({street: e.target.value})
                                                       }} placeholder={this.state.userInfo.street}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text" id="basic-addon1">Numer</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({streetNumber: e.target.value})
                                                       }} placeholder={this.state.userInfo.streetNumber}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <span className="input-group-text" id="basic-addon1">Kod pocztowy</span>
                                                <input type="text" className="form-control" id="floatingInputValue"
                                                       onChange={(e) => {
                                                           this.setState({zipCode: e.target.value})
                                                       }} placeholder={this.state.userInfo.zipCode}/>
                                            </div>

                                            <div className="input-group mb-3 w-75">
                                                <Checkbox toggle label={'Włącz logowanie dwufazowe'} checked={this.state.using2FA} onClick={(e, data) => this.setState({using2FA: data.checked})}/>
                                            </div>
                                            <div className="input-group mb-3 w-75 d-flex justify-content-center" style={{alignContent:'center', textAlign: 'center'}}>
                                            <p className="btn btn-primary w-25"  style={this.verifyButtonShowStyle()}
                                                    onClick={this.showVerifyModal}>Pokaż kod QR
                                            </p>
                                            </div>
                                        </form>
                                        <button className="btn btn-primary reservation w-25" type="submit" onClick={this.save}>Zapisz zmiany
                                        </button>
                                        <label>* Po zmianie danych, zostaniesz automatycznie wylogowany.</label>
                                    </section>

                                    <section className="tab-content" id="tab-two">


                                        <form className="form-floating row gy-2 gx-3"
                                              style={{justifyContent: 'center'}}>

                                            <div className="input-group mb-3" style={{width: '60%'}}>
                                                <span className="input-group-text ">Stare hasło</span>
                                                <input type={"password"} className={"form-control"} onChange={(e) => {
                                                    this.setState({passwordOld: e.target.value})
                                                }}/>
                                            </div>

                                            <div className="input-group mb-3" style={{width: '60%'}}>
                                                <span className="input-group-text ">Nowe hasło</span>
                                                <input type={"password"} className={"form-control"} onChange={(e) => {
                                                    this.setState({passwordNew: e.target.value})
                                                }}/>
                                            </div>

                                            <div className="input-group mb-3" style={{width: '60%'}}>
                                                <span className="input-group-text ">Powtórz nowe hasło</span>
                                                <input type={"password"} className={"form-control"} onChange={(e) => {
                                                    this.setState({passwordNew2: e.target.value})
                                                }}/>
                                            </div>
                                        </form>

                                        <button className="btn btn-primary reservation w-25" type="submit"
                                                onClick={this.savePassword}>Zmień hasło
                                        </button>
                                        <label>* Po zmianie danych, zostaniesz automatycznie wylogowany.</label>
                                    </section>

                                    <section className="tab-content" id="tab-three">

                                        <table className={"table"}>
                                            <thead>
                                            <tr>
                                                <th scope={"col"}>Kraj</th>
                                                <th scope={"col"}>Miasto</th>
                                                <th scope={"col"}>Data rezerwacji</th>
                                                <th scope={"col"}>Data wyjazdu</th>
                                                <th scope={"col"}>Ilość dni</th>
                                                <th scope={"col"}>Cena całkowita</th>
                                                <th scope={"col"}></th>
                                            </tr>
                                            </thead>
                                            <tbody> {this.state.reservations.map(res =>
                                                <tr key={res.idReservation}>

                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{res.trip.tripCity.country.nameCountry}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{res.trip.tripCity.nameCity}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{res.dateOfReservation}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{res.departureDate}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{res.trip.numberOfDays}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{res.totalPrice}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td><IoTrashBin
                                                            onClick={() => this.deleteReservation(res.idReservation)}/>
                                                        </td>
                                                    </th>

                                                </tr>)}
                                            </tbody>
                                        </table>

                                    </section>

                                    <section className="tab-content" id="tab-four">

                                        <table className={"table"}>
                                            <thead>
                                            <tr>
                                                <th scope={"col"}>Kraj</th>
                                                <th scope={"col"}>Miasto</th>
                                                <th scope={"col"}>Data rezerwacji</th>
                                                <th scope={"col"}>Data wyjazdu</th>
                                                <th scope={"col"}>Ilość dni</th>
                                                <th scope={"col"}>Cena całkowita</th>
                                                <th scope={"col"}></th>
                                            </tr>
                                            </thead>
                                            <tbody> {this.state.ownOffers.map(offer =>
                                                <tr key={offer.idOwnOffer}>

                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{offer.offerCity.country.nameCountry}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{offer.offerCity.nameCity}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{offer.dateOfReservation}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{offer.departureDate}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{offer.numberOfDays}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td>{offer.totalPrice}</td>
                                                    </th>
                                                    <th scope={"row"} style={{fontWeight: 'normal'}}>
                                                        <td><IoTrashBin
                                                            onClick={() => this.deleteOwnOffer(offer.idOwnOffer)}/></td>
                                                    </th>

                                                </tr>)}
                                            </tbody>
                                        </table>

                                    </section>

                                    <section className="tab-content" id="tab-five" style={{height: '300px'}}>

                                        <div style={{textAlignLast: 'center'}}>
                                            <label>Czy jesteś pewien że chcesz usunąć swoje konto ?</label>
                                        </div>

                                        <button className="btn btn-primary reservation" type="submit"
                                                onClick={this.showModalDelete} style={{
                                            backgroundColor: 'red',
                                            borderColor: 'red',
                                            marginTop: '5%'
                                        }}>Usuń konto
                                        </button>

                                    </section>

                                </div>
                            </div>

                            <Modal show={this.state.showModalVerify} onHide={this.handleCloseModalVerify}>
                                <Modal.Header closeButton>
                                    <Modal.Title>Włączenie weryfikacji dwufazowej</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    <Modal.Body>
                                        Zeskanują poniższy kod QR za pomocą aplikacji autentykatora. Kod QR będzie pokazany tylko raz!
                                        <img alt={"qr code"} src={this.state.qrCode} />
                                    </Modal.Body>
                                </Modal.Body>
                                <Modal.Footer>
                                </Modal.Footer>
                            </Modal>

                            <Modal show={this.state.showModal} onHide={this.handleCloseModal}>
                                <Modal.Header closeButton>
                                    <Modal.Title>Błąd danych</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    {this.state.isError && <Message negative>{this.state.errorMessage}</Message>}
                                </Modal.Body>
                                <Modal.Footer>
                                    <Button variant="secondary" onClick={this.handleCloseModal}>
                                        Wróć
                                    </Button>
                                </Modal.Footer>
                            </Modal>

                            <Modal show={this.state.showModalDelete} onHide={this.handleCloseModalDelete}>
                                <Modal.Header closeButton>
                                    <Modal.Title>Usunięcie konta</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    Czy na pewno chcesz usunąć konto ?
                                </Modal.Body>
                                <Modal.Footer>
                                    <Button variant="secondary" onClick={this.handleCloseModalDelete}>
                                        Anuluj
                                    </Button>
                                    <Button variant="primary" onClick={this.deleteAccount}>
                                        Potwierdź
                                    </Button>
                                </Modal.Footer>
                            </Modal>
                        </Container>
                    </section>
                </header>
                <Footer/>
            </div>
        );
    }
}

export default MyProfile;