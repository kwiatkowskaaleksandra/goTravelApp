import React, {Component} from "react";
import Footer from "../home/Footer";
import NavigationBar from "../home/NavigationBar";
import {withTranslation} from "react-i18next";
import AuthContext from "../../others/AuthContext";
import {goTravelApi} from "../../others/GoTravelApi";
import {handleLogError} from "../../others/JWT";
import {Accordion, Col, Row, Tab, Tabs} from "react-bootstrap";
import {GiEmptyHourglass} from "react-icons/gi";
import "./reservation.css"
import ReservationAccordion from "../client/settings/ReservationAccordion";
import OwnOfferAccordion from "../client/settings/OwnOfferAccordion";

class ReservationManagement extends Component {

    static contextType = AuthContext

    state = {
        reservationsActiveOrders: [],
        ownOffersActiveOrders: [],
        page: 1,
        size: 7,
        key: 'reservations',
        userInfo: []
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        this.setState({ userInfo: user }, () => {
            this.getAllActiveReservationNotAccepted(user)
        })
    }

    getAllActiveReservationNotAccepted = (user) => {
        goTravelApi.getAllActiveReservationNotAccepted(user, this.state.page, this.state.size).then(res => {
            this.setState({reservationsActiveOrders: res.data.content})
        }).catch(err => {
            handleLogError(err)
        })
    }

    getAllActiveOwnOffersNotAccepted = (user) => {
        goTravelApi.getAllActiveOwnOffersNotAccepted(user, this.state.page, this.state.size).then(res => {
            this.setState({ownOffersActiveOrders: res.data.content})
        }).catch(err => {
            handleLogError(err)
        })
    }

    handleTabSelect = (selectedKey) => {
        this.setState({key: selectedKey}, () => {
            if (selectedKey === 'reservations') this.getAllActiveReservationNotAccepted(this.state.userInfo)
            else this.getAllActiveOwnOffersNotAccepted(this.state.userInfo)
        });
    };

    changeAcceptStatus = async (idOffer, acceptStatus, mode) => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.changeAcceptStatus(this.state.userInfo, csrfToken, idOffer, acceptStatus, mode).then(() => {
            if (mode === 'reservations') this.getAllActiveReservationNotAccepted(this.state.userInfo)
            else this.getAllActiveOwnOffersNotAccepted(this.state.userInfo)
        }).catch(err => {
            handleLogError(err)
        })
    }

    render() {
        const {t} = this.props
        const {key} = this.state;

        return (
            <main>
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
                                    <Tab eventKey="reservations" title={t('goTravelNamespace3:readyMadeOffers')} />
                                    <Tab eventKey="ownOffer" title={t('goTravelNamespace3:ownTrips')} className="custom-tab"/>
                                </Tabs>
                            </div>
                            <div className={"ml-auto"} style={{marginLeft: '5%'}}>
                                {key === 'reservations' &&
                                    <div className={"justify-content-center"} style={{width: '1000px'}}>
                                        {this.state.reservationsActiveOrders.length === 0 ? (
                                            <div style={{borderRadius: '10px', backgroundColor: '#efe8e8', width: '800px', height: '200px', marginBottom: '5%', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                                                <GiEmptyHourglass style={{width: '80px', height: '80px', color: '#4ec3ff'}}/>
                                                <label>{t('goTravelNamespace3:theresNothingToShow')}</label>
                                            </div>
                                        ) : (
                                            <ReservationAccordion t={t} reservations={this.state.reservationsActiveOrders}  user={this.state.user} mode = 'active' userType = 'employee' changeAcceptStatus={this.changeAcceptStatus}/>
                                        )}
                                    </div>
                                }

                                {key === 'ownOffer' &&
                                    <div className={"justify-content-center"} style={{width: '1000px'}}>
                                        {this.state.ownOffersActiveOrders.length === 0 ? (
                                            <div style={{borderRadius: '10px', backgroundColor: '#efe8e8', width: '800px', height: '200px', marginBottom: '5%', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                                                <GiEmptyHourglass style={{width: '80px', height: '80px', color: '#4ec3ff'}}/>
                                                <label>{t('goTravelNamespace3:theresNothingToShow')}</label>
                                            </div>
                                        ) : (
                                            <OwnOfferAccordion t={t} ownOffers={this.state.ownOffersActiveOrders}  user={this.state.user} mode = 'active' userType = 'employee' changeAcceptStatus={this.changeAcceptStatus}/>
                                        )}
                                    </div>
                                }
                            </div>
                        </div>
                    </section>
                </header>
                <Footer/>
            </main>
        )
    }
}

export default withTranslation()(ReservationManagement)