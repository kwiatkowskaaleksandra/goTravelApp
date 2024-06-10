import React, {Component} from "react";
import AuthContext from "../../../others/AuthContext";
import {withTranslation} from "react-i18next";
import NavigationBar from "../../home/NavigationBar";
import Footer from "../../home/Footer";
import {Tab, Tabs, Row, Col, Modal} from "react-bootstrap";
import { GiEmptyHourglass } from "react-icons/gi";
import {goTravelApi} from "../../../others/GoTravelApi";
import {handleLogError} from "../../../others/JWT";
import Button from "react-bootstrap/Button";
import {Message} from "semantic-ui-react";
import ReservationAccordion from "./ReservationAccordion";
import OwnOfferAccordion from "./OwnOfferAccordion";

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
                                                   id="flexRadioDefault1" checked={this.state.reservationOrigin === 'reservations'} onClick={() => this.getActiveOrders('reservations')}/>
                                            <label className="form-check-label" htmlFor="flexRadioDefault1">
                                                {t('goTravelNamespace3:readyMadeOffers')}
                                            </label>
                                        </div>
                                        <div className="form-check" style={{marginLeft: '20px', marginBottom: '5%'}}>
                                            <input className="form-check-input" type="radio" name="flexRadioDefault"
                                                   id="flexRadioDefault2" checked={this.state.reservationOrigin === 'ownOffer'} onClick={() => this.getActiveOrders('ownOffer')}/>
                                            <label className="form-check-label" htmlFor="flexRadioDefault2">
                                                {t('goTravelNamespace3:ownTrips')}
                                            </label>
                                        </div>
                                    </div>
                                    {key === 'activeOrders' &&
                                        <div className={"d-flex justify-content-center"} style={{width: '1000px'}}>
                                            {this.state.ownOffersActiveOrders.length === 0 && this.state.reservationsActiveOrders.length === 0 ? (
                                                <div style={{borderRadius: '10px', backgroundColor: '#efe8e8', width: '800px', height: '200px', marginBottom: '5%', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                                                    <GiEmptyHourglass style={{width: '80px', height: '80px', color: '#4ec3ff'}}/>
                                                    <label>{t('goTravelNamespace3:theresNothingToShow')}</label>
                                                </div>
                                            ) : (
                                                ( this.state.reservationOrigin === 'reservations' && (
                                                    <ReservationAccordion t={t} reservations={this.state.reservationsActiveOrders} cancelReservationModal = {this.cancelReservationModal} generateInvoice={this.generateInvoice} user={this.state.user} mode = 'active' userType = 'client'/>
                                                )) || (this.state.reservationOrigin === 'ownOffer' && (
                                                    <OwnOfferAccordion t={t} ownOffers={this.state.ownOffersActiveOrders} generateInvoice={this.generateInvoice} cancelReservationModal = {this.cancelReservationModal} user={this.state.user} mode = 'active' userType = 'client'/>
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
                                                <ReservationAccordion t={t} reservations={this.state.reservationsPurchasedTrips} cancelReservationModal = {this.cancelReservationModal} generateInvoice={this.generateInvoice} user={this.state.user} mode = 'notActive' userType = 'client'/>
                                            )) || (this.state.reservationOrigin === 'ownOffer' && (
                                               <OwnOfferAccordion t={t} ownOffers={this.state.ownOffersPurchasedTrips} generateInvoice={this.generateInvoice} cancelReservationModal = {this.cancelReservationModal} user={this.state.user} mode = 'notActive' userType = 'client'/>
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