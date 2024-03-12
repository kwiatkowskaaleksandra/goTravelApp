import React, {Component} from "react";
import AuthContext from "../../others/AuthContext";
import {withTranslation} from "react-i18next";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import {Tab, Tabs, Accordion, Row, Col, Modal} from "react-bootstrap";
import { GiEmptyHourglass } from "react-icons/gi";
import {goTravelApi} from "../../others/GoTravelApi";
import {handleLogError} from "../../others/JWT";
import Button from "react-bootstrap/Button";
import {Message} from "semantic-ui-react";
import StripeButton from "../../others/StripeButton";

class SettingsInvoices extends Component {

    static contextType = AuthContext

    state = {
        key: 'activeOrders',
        user: null,
        reservationsActiveOrders: [],
        ownOffersActiveOrders: [],
        reservationsPurchasedTrips: [],
        ownOffersPurchasedTrips: [],
        reservationOrigin: 'reservations',
        showModalCancelReservation: false,
        selectedIdReservation: 0,
        message: '',
        showConfirmation: false,
        isError: false,
        errorMessage: '',
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        this.setState({ user: user }, () => {
            this.getActiveOrders(this.state.reservationOrigin)
        })
    }

    getActiveOrders = (e) => {
        this.setState({ reservationOrigin: e });
        if (!this.state.user) {
            console.error("User is not set yet.");
            return;
        }

        const resetState = {
            ownOffersActiveOrders: [],
            reservationsActiveOrders: [],
            reservationsPurchasedTrips: [],
            ownOffersPurchasedTrips: [],
        };

        goTravelApi.getActiveOrders(this.state.user, e, this.state.key).then(res => {
            let newState = {};
            if (e === 'reservations') newState = this.state.key === 'activeOrders' ? { reservationsActiveOrders: res.data } : { reservationsPurchasedTrips: res.data };
            else if (e === 'ownOffer') newState = this.state.key === 'activeOrders' ? { ownOffersActiveOrders: res.data } : { ownOffersPurchasedTrips: res.data };

            this.setState({ ...resetState, ...newState });
        }).catch(error => {
            this.setState({ ...resetState });
            handleLogError(error);
        });
    }

    cancelReservationModal = (idReservation) => {
        this.setState({
            showModalCancelReservation: true,
            selectedIdReservation: idReservation
        })
    }

    cancelReservationModalClose = () => {
        this.setState({
            showModalCancelReservation: false,
            selectedIdReservation: 0
        });
    };

    cancelConfirmation = () => {
        this.setState({
            showConfirmation: false,
            message: ''
        });
        window.location.reload()
    };

    cancelReservation = async () => {
        const {t} = this.props;
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.deleteReservation(this.state.user, csrfToken, this.state.selectedIdReservation, this.state.reservationOrigin).then(() => {
            this.setState({
                message: t('goTravelNamespace3:reservationSuccessfullyCanceled'),
                showModalCancelReservation: false,
                selectedIdReservation: 0,
                showConfirmation: true
            })
        }).catch(error => {
            handleLogError(error)
            this.setState({
                isError: true,
                errorMessage: t('goTravelNamespace3:errorPleaseTryAgainInAMoment')
            })
        })
    }

    generateInvoice = (idReservation) => {
        goTravelApi.generateInvoice(this.state.user, idReservation, this.state.reservationOrigin).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'invoice.pdf');
            document.body.appendChild(link);
            link.click();
        }).catch(error => {
            handleLogError(error)
        })
    }

    handleTabSelect = (selectedKey) => {
        this.setState({key: selectedKey}, () =>
            this.getActiveOrders(this.state.reservationOrigin)
        );
    };

    render() {
        const {t} = this.props
        const {key} = this.state;

        return (
            <div>
                <NavigationBar/>
                <section className='d-flex justify-content-center justify-content-lg-between p-2  mt-4'></section>
                <header className={"head"}>
                    <section className={"d-flex justify-content-center"}>
                        <div className="d-flex justify-content-center w-75" >
                            <div className="d-flex flex-column align-items-start">
                                <Tabs
                                    id="controlled-tab"
                                    activeKey={key}
                                    onSelect={this.handleTabSelect}
                                    className="mb-3 flex-column"
                                >
                                    <Tab eventKey="activeOrders" title={t('goTravelNamespace3:activeOrders')} />
                                    <Tab eventKey="purchasedTrips" title={t('goTravelNamespace3:purchasedTrips')} className="custom-tab"/>
                                </Tabs>
                            </div>
                                <div className={"ml-auto"} style={{marginLeft: '5%'}}>
                                    <div className="d-flex flex-row">
                                        <div className="form-check mr-3">
                                            <input className="form-check-input" type="radio" name="flexRadioDefault"
                                                   id="flexRadioDefault1" checked={this.state.reservationOrigin === 'reservations'} onClick={(e) => this.getActiveOrders('reservations')}/>
                                            <label className="form-check-label" htmlFor="flexRadioDefault1">
                                                {t('goTravelNamespace3:readyMadeOffers')}
                                            </label>
                                        </div>
                                        <div className="form-check" style={{marginLeft: '20px', marginBottom: '5%'}}>
                                            <input className="form-check-input" type="radio" name="flexRadioDefault"
                                                   id="flexRadioDefault2" checked={this.state.reservationOrigin === 'ownOffer'} onClick={(e) => this.getActiveOrders('ownOffer')}/>
                                            <label className="form-check-label" htmlFor="flexRadioDefault2">
                                                {t('goTravelNamespace3:ownTrips')}
                                            </label>
                                        </div>
                                    </div>
                                    {key === 'activeOrders' &&
                                        <div className={"justify-content-center"} style={{width: '1000px'}}>
                                            {this.state.ownOffersActiveOrders.length === 0 && this.state.reservationsActiveOrders.length === 0 ? (
                                                <div style={{borderRadius: '10px', backgroundColor: '#efe8e8', width: '800px', height: '200px', marginBottom: '5%', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                                                    <GiEmptyHourglass style={{width: '80px', height: '80px', color: '#4ec3ff'}}/>
                                                    <label>{t('goTravelNamespace3:theresNothingToShow')}</label>
                                                </div>
                                            ) : (
                                                ( this.state.reservationOrigin === 'reservations' && (
                                                <Accordion style={{marginBottom: '10%'}}>
                                                    {this.state.reservationsActiveOrders.map(reservation => (
                                                        <Accordion.Item eventKey = {reservation.idReservation} >
                                                            <Accordion.Header>
                                                                <Row className="w-100">
                                                                    <Col xs={6}>
                                                                        <div>{t('goTravelNamespace1:' + reservation.trip.typeOfTrip.name)}</div>
                                                                        <div>{t('goTravelNamespace2:' + reservation.trip.tripCity.country.nameCountry)} - {t('goTravelNamespace2:' + reservation.trip.tripCity.nameCity)}</div>
                                                                    </Col>
                                                                    <Col xs={6} className="text-end">
                                                                        <div>{t('goTravelNamespace3:dateOfReservation')}</div>
                                                                        <div>{reservation.dateOfReservation}</div>
                                                                    </Col>
                                                                </Row>
                                                            </Accordion.Header>
                                                            <Accordion.Body>
                                                                <Row className="w-100">
                                                                    <Col xs={6}>
                                                                        <div>{t('goTravelNamespace3:totalPrice')}: {reservation.totalPrice}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:departureDate')}: {reservation.departureDate}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:numberOfAdults')}: {reservation.numberOfAdults}</div>
                                                                    </Col>
                                                                    <Col xs={6}>
                                                                        <div style={{ color: reservation.payment ? 'green' : 'red' }}>
                                                                            <span style={{ color: 'black' }}>{t('goTravelNamespace3:paymentStatus')}: </span>
                                                                            <span style={{fontWeight: 'bold'}}>{reservation.payment ? t('goTravelNamespace3:paid') : t('goTravelNamespace3:isNotPaid')}</span>
                                                                        </div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace1:numberOfDays')}: {reservation.trip.numberOfDays}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:numberOfChildren')}: {reservation.numberOfChildren}</div>
                                                                    </Col>
                                                                </Row>
                                                                <Row className={"w-100"} style={{display: 'flex'}}>
                                                                    <Col xs={12}>
                                                                        <div style={{ display: 'flex', justifyContent: 'center' }}>
                                                                            <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => this.cancelReservationModal(reservation.idReservation)}>{t('goTravelNamespace3:cancelYourReservation')}</button>
                                                                            {reservation.payment && <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => this.generateInvoice(reservation.idReservation)}>{t('goTravelNamespace3:downloadTheInvoice')}</button>}
                                                                            {!reservation.payment && <StripeButton price={reservation.totalPrice} email={this.state.user.data.sub} user={this.state.user} type={"reservations"} id={reservation.idReservation} style={{marginTop: '40px'}}/>}
                                                                        </div>
                                                                    </Col>
                                                                </Row>
                                                            </Accordion.Body>
                                                        </Accordion.Item>
                                                    ))}
                                                </Accordion>
                                                )) || (this.state.reservationOrigin === 'ownOffer' && (
                                                    <Accordion style={{marginBottom: '10%'}}>
                                                        {this.state.ownOffersActiveOrders.map(ownOffer => (
                                                            <Accordion.Item eventKey = {ownOffer.idOwnOffer} >
                                                                <Accordion.Header>
                                                                    <Row className="w-100">
                                                                        <Col xs={6}>
                                                                            <div>{t('goTravelNamespace2:' + ownOffer.offerCity.country.nameCountry)} - {t('goTravelNamespace2:' + ownOffer.offerCity.nameCity)}</div>
                                                                        </Col>
                                                                        <Col xs={6} className="text-end">
                                                                            <div>{t('goTravelNamespace3:dateOfReservation')}</div>
                                                                            <div>{ownOffer.dateOfReservation}</div>
                                                                        </Col>
                                                                    </Row>
                                                                </Accordion.Header>
                                                                <Accordion.Body>
                                                                    <Row className="w-100">
                                                                        <Col xs={6}>
                                                                            <div>{t('goTravelNamespace3:totalPrice')}: {ownOffer.totalPrice}</div>
                                                                            <div className={"mt-2"}>{t('goTravelNamespace3:departureDate')}: {ownOffer.departureDate}</div>
                                                                            <div className={"mt-2"}>{t('goTravelNamespace3:numberOfAdults')}: {ownOffer.numberOfAdults}</div>
                                                                        </Col>
                                                                        <Col xs={6}>
                                                                            <div style={{ color: ownOffer.payment ? 'green' : 'red' }}>
                                                                                <span style={{ color: 'black' }}>{t('goTravelNamespace3:paymentStatus')}: </span>
                                                                                <span style={{fontWeight: 'bold'}}>{ownOffer.payment ? t('goTravelNamespace3:paid') : t('goTravelNamespace3:isNotPaid')}</span>
                                                                            </div>
                                                                            <div className={"mt-2"}>{t('goTravelNamespace1:numberOfDays')}: {ownOffer.numberOfDays}</div>
                                                                            <div className={"mt-2"}>{t('goTravelNamespace3:numberOfChildren')}: {ownOffer.numberOfChildren}</div>
                                                                        </Col>
                                                                    </Row>
                                                                    <Row className={"w-100"} style={{display: 'flex'}}>
                                                                        <Col xs={12}>
                                                                            <div style={{ display: 'flex', justifyContent: 'center' }}>
                                                                                <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => this.cancelReservationModal(ownOffer.idOwnOffer)}>{t('goTravelNamespace3:cancelYourReservation')}</button>
                                                                                {ownOffer.payment && <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => this.generateInvoice(ownOffer.idOwnOffer)}>{t('goTravelNamespace3:downloadTheInvoice')}</button>}
                                                                                {!ownOffer.payment && <StripeButton price={ownOffer.totalPrice} email={this.state.user.data.sub} user={this.state.user} type={"ownOffer"} id={ownOffer.idOwnOffer} style={{marginTop: '40px'}}/>}
                                                                            </div>
                                                                        </Col>
                                                                    </Row>
                                                                </Accordion.Body>
                                                            </Accordion.Item>
                                                        ))}
                                                    </Accordion>
                                            ))
                                            )}
                                        </div>
                                    }

                                {key === 'purchasedTrips' &&
                                    <div className={"d-flex justify-content-center"} style={{width: '1000px'}}>
                                        {this.state.reservationsPurchasedTrips.length === 0 && this.state.ownOffersPurchasedTrips.length === 0 ? (
                                                <div style={{borderRadius: '10px', backgroundColor: '#efe8e8', width: '800px', height: '200px', marginBottom: '5%', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                                                    <GiEmptyHourglass style={{width: '80px', height: '80px', color: '#4ec3ff'}}/>
                                                    <label>{t('goTravelNamespace3:theresNothingToShow')}</label>
                                                </div>
                                            ) : (
                                            ( this.state.reservationOrigin === 'reservations' && (
                                                <Accordion style={{marginBottom: '10%', width: '1000px'}}>
                                                    {this.state.reservationsPurchasedTrips.map(reservation => (
                                                        <Accordion.Item eventKey = {reservation.idReservation} >
                                                            <Accordion.Header>
                                                                <Row className="w-100">
                                                                    <Col xs={6}>
                                                                        <div>{t('goTravelNamespace1:' + reservation.trip.typeOfTrip.name)}</div>
                                                                        <div>{t('goTravelNamespace2:' + reservation.trip.tripCity.country.nameCountry)} - {t('goTravelNamespace2:' + reservation.trip.tripCity.nameCity)}</div>
                                                                    </Col>
                                                                    <Col xs={6} className="text-end">
                                                                        <div>{t('goTravelNamespace3:dateOfReservation')}</div>
                                                                        <div>{reservation.dateOfReservation}</div>
                                                                    </Col>
                                                                </Row>
                                                            </Accordion.Header>
                                                            <Accordion.Body>
                                                                <Row className="w-100">
                                                                    <Col xs={6}>
                                                                        <div>{t('goTravelNamespace3:totalPrice')}: {reservation.totalPrice}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:departureDate')}: {reservation.departureDate}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:numberOfAdults')}: {reservation.numberOfAdults}</div>
                                                                    </Col>
                                                                    <Col xs={6}>
                                                                        <div style={{ color: reservation.payment ? 'green' : 'red' }}>
                                                                            <span style={{ color: 'black' }}>{t('goTravelNamespace3:paymentStatus')}: </span>
                                                                            <span style={{fontWeight: 'bold'}}>{reservation.payment ? t('goTravelNamespace3:paid') : t('goTravelNamespace3:isNotPaid')}</span>
                                                                        </div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace1:numberOfDays')}: {reservation.trip.numberOfDays}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:numberOfChildren')}: {reservation.numberOfChildren}</div>
                                                                    </Col>
                                                                </Row>
                                                                <Row className={"w-100"} style={{display: 'flex'}}>
                                                                    <Col xs={12}>
                                                                        <div style={{ display: 'flex', justifyContent: 'center' }}>
                                                                            {reservation.payment && <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => this.generateInvoice(reservation.idReservation)}>{t('goTravelNamespace3:downloadTheInvoice')}</button>}
                                                                        </div>
                                                                    </Col>
                                                                </Row>
                                                            </Accordion.Body>
                                                        </Accordion.Item>
                                                    ))}
                                                </Accordion>
                                            )) || (this.state.reservationOrigin === 'ownOffer' && (
                                                <Accordion style={{marginBottom: '15%', width: '1000px'}}>
                                                    {this.state.ownOffersPurchasedTrips.map(ownOffer => (
                                                        <Accordion.Item eventKey = {ownOffer.idOwnOffer} >
                                                            <Accordion.Header>
                                                                <Row className="w-100">
                                                                    <Col xs={6}>
                                                                        <div>{t('goTravelNamespace2:' + ownOffer.offerCity.country.nameCountry)} - {t('goTravelNamespace2:' + ownOffer.offerCity.nameCity)}</div>
                                                                    </Col>
                                                                    <Col xs={6} className="text-end">
                                                                        <div>{t('goTravelNamespace3:dateOfReservation')}</div>
                                                                        <div>{ownOffer.dateOfReservation}</div>
                                                                    </Col>
                                                                </Row>
                                                            </Accordion.Header>
                                                            <Accordion.Body>
                                                                <Row className="w-100">
                                                                    <Col xs={6}>
                                                                        <div>{t('goTravelNamespace3:totalPrice')}: {ownOffer.totalPrice}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:departureDate')}: {ownOffer.departureDate}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:numberOfAdults')}: {ownOffer.numberOfAdults}</div>
                                                                    </Col>
                                                                    <Col xs={6}>
                                                                        <div style={{ color: ownOffer.payment ? 'green' : 'red' }}>
                                                                            <span style={{ color: 'black' }}>{t('goTravelNamespace3:paymentStatus')}: </span>
                                                                            <span style={{fontWeight: 'bold'}}>{ownOffer.payment ? t('goTravelNamespace3:paid') : t('goTravelNamespace3:isNotPaid')}</span>
                                                                        </div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace1:numberOfDays')}: {ownOffer.numberOfDays}</div>
                                                                        <div className={"mt-2"}>{t('goTravelNamespace3:numberOfChildren')}: {ownOffer.numberOfChildren}</div>
                                                                    </Col>
                                                                </Row>
                                                                <Row className={"w-100"} style={{display: 'flex'}}>
                                                                    <Col xs={12}>
                                                                        <div style={{ display: 'flex', justifyContent: 'center' }}>
                                                                            {ownOffer.payment && <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => this.generateInvoice(ownOffer.idOwnOffer)}>{t('goTravelNamespace3:downloadTheInvoice')}</button>}
                                                                        </div>
                                                                    </Col>
                                                                </Row>
                                                            </Accordion.Body>
                                                        </Accordion.Item>
                                                    ))}
                                                </Accordion>
                                            ))
                                        )
                                        }
                                    </div>
                                }

                                    <Modal show={this.state.showModalCancelReservation} onHide={this.cancelReservationModalClose}>
                                        <Modal.Header closeButton>
                                            <Modal.Title style={{fontFamily: "Comic Sans MS"}}>{t('goTravelNamespace3:cancelYourReservation')}</Modal.Title>
                                        </Modal.Header>
                                        <Modal.Body style={{fontFamily: "Comic Sans MS"}}>
                                            {t('goTravelNamespace3:areYouSureYouWantToCancelYourReservation')}
                                        </Modal.Body>
                                        <Modal.Footer>
                                            <Row className={"w-100 text-end"}>
                                                <Col xs={12}>
                                                    <Button variant="secondary" onClick={this.cancelReservationModalClose} style={{fontFamily: "Comic Sans MS"}}>
                                                        {t('goTravelNamespace3:cancel')}
                                                    </Button>
                                                    <Button variant="primary confirmBtn" onClick={this.cancelReservation} style={{fontFamily: "Comic Sans MS", marginLeft: '10px'}}>
                                                        {t('goTravelNamespace3:confirm')}
                                                    </Button>
                                                </Col>
                                            </Row>
                                            <Row className={"w-100"}>
                                                <Col xs={12}>
                                                    {this.state.isError &&
                                                        <Message negative  className={"mt-2 messageError"} style={{fontFamily: "Comic Sans MS", width: '400px'}}>{this.state.errorMessage}</Message>}
                                                </Col>
                                            </Row>
                                        </Modal.Footer>
                                    </Modal>

                                    <Modal show={this.state.showConfirmation} onHide={this.cancelConfirmation}>
                                        <Modal.Header closeButton>
                                            <Modal.Title style={{fontFamily: "Comic Sans MS"}}>{t('goTravelNamespace3:cancelYourReservation')}</Modal.Title>
                                        </Modal.Header>
                                        <Modal.Body style={{fontFamily: "Comic Sans MS"}}>
                                            {this.state.message}
                                        </Modal.Body>
                                        <Modal.Footer>
                                            <Button variant="secondary" onClick={this.cancelConfirmation} style={{fontFamily: "Comic Sans MS"}}>
                                                {t('goTravelNamespace3:close')}
                                            </Button>
                                        </Modal.Footer>
                                    </Modal>
                        </div>
                        </div>
                    </section>
                </header>
                <Footer/>
            </div>
        )
    }
}

export default withTranslation()(SettingsInvoices)