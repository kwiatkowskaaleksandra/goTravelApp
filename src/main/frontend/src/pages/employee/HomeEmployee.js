import React, {Component} from "react";
import {withTranslation} from "react-i18next";
import NavigationBar from "../home/NavigationBar";
import {Col, Container, Row} from "react-bootstrap";
import Card from "react-bootstrap/Card";
import {FaUserCog} from "react-icons/fa";
import { SiYourtraveldottv } from "react-icons/si";
import { GiBookPile } from "react-icons/gi";
import Footer from "../home/Footer";

class HomeEmployee extends Component {

    render() {
        const {t} = this.props
        return (
            <main>
                <NavigationBar/>
                <header>
                    <section>
                        <Container style={{marginTop: '5%', marginBottom: '5%', width: '70%'}}>
                            <Row className="justify-content-center">

                                <Col md={4} className="mb-4">
                                    <Card style={{ marginBottom: '80px' }} className="my-card" onMouseEnter={() => { document.getElementById('manageTourOffers').style.color = '#3273dc' }} onMouseLeave={() => { document.getElementById('manageTourOffers').style.color = '#4ec3ff' }}>
                                        <Card.Body>
                                            <Card.Link href="/employee/manageTourOffers" style={{ textDecoration: 'none' }}>
                                                <div className="settingCard">
                                                    <SiYourtraveldottv id="manageTourOffers" className="icon" />
                                                    <span style={{ marginLeft: '15px', flex: '2', width: '66%'}}>
                                                        <Card.Title>{t('goTravelNamespace4:manageTourOffers')}</Card.Title>
                                                    </span>
                                                </div>
                                            </Card.Link>
                                        </Card.Body>
                                    </Card>
                                </Col>

                                <Col md={4} className="mb-4">
                                    <Card style={{ marginBottom: '30px' }} className="my-card" onMouseEnter={() => { document.getElementById('reservationManagement').style.color = '#3273dc' }} onMouseLeave={() => { document.getElementById('reservationManagement').style.color = '#4ec3ff' }}>
                                        <Card.Body>
                                            <Card.Link href="/employee/reservationManagement" style={{ textDecoration: 'none' }}>
                                                <div className="settingCard">
                                                    <GiBookPile id="reservationManagement" className="icon" />
                                                    <span style={{ marginLeft: '15px', flex: '2', width: '66%' }}>
                                                        <Card.Title>{t('goTravelNamespace4:reservationManagement')}</Card.Title>
                                                    </span>
                                                </div>
                                            </Card.Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                                <Col md={4} className="mb-4">
                                    <Card style={{ marginBottom: '30px' }} className="my-card" onMouseEnter={() => { document.getElementById('userSetting').style.color = '#3273dc' }} onMouseLeave={() => { document.getElementById('userSetting').style.color = '#4ec3ff' }}>
                                        <Card.Body>
                                            <Card.Link href="/employee/accountSettings" style={{ textDecoration: 'none' }}>
                                                <div className="settingCard">
                                                    <FaUserCog id="userSetting" className="icon" />
                                                    <span style={{ marginLeft: '15px', flex: '2', width: '66%'}}>
                                                        <Card.Title>{t('goTravelNamespace4:accountSettings')}</Card.Title>
                                                    </span>
                                                </div>
                                            </Card.Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>
                        </Container>
                    </section>
                </header>
                <Footer/>
            </main>
        )
    }
}

export default withTranslation()(HomeEmployee);