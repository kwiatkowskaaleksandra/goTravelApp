import React, {Component} from "react";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import "./MyProfile.css"
import {Col, Container, Row} from "react-bootstrap";
import Card from "react-bootstrap/Card";
import { FaUserCog } from "react-icons/fa";
import {withTranslation} from "react-i18next";
import { TbMessages } from "react-icons/tb";
import { HiOutlineClipboardDocumentList } from "react-icons/hi2";
import { SiYourtraveldottv } from "react-icons/si";

class Settings extends Component {

    render() {
        const {t} = this.props

        return (
            <div>
                <NavigationBar/>
                <header className={"head"}>
                    <section>
                        <Container style={{width: '700px'}}>
                            <Row className="justify-content-center">

                                <Col md={6} className="mb-4">
                                    <Card style={{ marginBottom: '30px' }} className="my-card" onMouseEnter={() => { document.getElementById('userSetting').style.color = '#3273dc' }} onMouseLeave={() => { document.getElementById('userSetting').style.color = '#4ec3ff' }}>
                                        <Card.Body>
                                            <Card.Link href="/myProfile/settings" style={{ textDecoration: 'none' }}>
                                                <div className="settingCard">
                                                    <FaUserCog id="userSetting" className="icon" />
                                                    <span style={{ marginLeft: '20px', flex: '2', width: '66%'}}>
                                                        <Card.Title>{t('goTravelNamespace2:myProfile')}</Card.Title>
                                                    </span>
                                                </div>
                                            </Card.Link>
                                        </Card.Body>
                                    </Card>
                                    <Card style={{ marginBottom: '30px' }} className="my-card" onMouseEnter={() => { document.getElementById('messages').style.color = '#3273dc' }} onMouseLeave={() => { document.getElementById('messages').style.color = '#4ec3ff' }}>
                                        <Card.Body>
                                            <Card.Link href="/" style={{ textDecoration: 'none' }}>
                                                <div className="settingCard">
                                                    <TbMessages id="messages" className="icon" />
                                                    <span style={{ marginLeft: '20px', flex: '2', width: '66%' }}>
                                                        <Card.Title>{t('goTravelNamespace2:contactWithTheEmployee')}</Card.Title>
                                                    </span>
                                                </div>
                                            </Card.Link>
                                        </Card.Body>
                                    </Card>
                                </Col>

                                <Col md={6} className="mb-4">
                                    <Card style={{ marginBottom: '30px' }} className="my-card" onMouseEnter={() => { document.getElementById('invoices').style.color = '#3273dc' }} onMouseLeave={() => { document.getElementById('invoices').style.color = '#4ec3ff' }}>
                                        <Card.Body>
                                            <Card.Link href="/myProfile/invoices" style={{ textDecoration: 'none' }}>
                                                <div className="settingCard">
                                                    <HiOutlineClipboardDocumentList id="invoices" className="icon" />
                                                    <span style={{ marginLeft: '20px', flex: '2', width: '66%' }}>
                                                        <Card.Title>{t('goTravelNamespace2:invoices')}</Card.Title>
                                                    </span>
                                                </div>
                                            </Card.Link>
                                        </Card.Body>
                                    </Card>
                                    <Card style={{ marginBottom: '30px' }} className="my-card" onMouseEnter={() => { document.getElementById('recommendationSettings').style.color = '#3273dc' }} onMouseLeave={() => { document.getElementById('recommendationSettings').style.color = '#4ec3ff' }}>
                                        <Card.Body>
                                            <Card.Link href="/" style={{ textDecoration: 'none' }}>
                                                <div className="settingCard">
                                                    <SiYourtraveldottv id="recommendationSettings" className="icon" />
                                                    <span style={{ marginLeft: '20px', flex: '2', width: '66%' }}>
                                                        <Card.Title>{t('goTravelNamespace2:recommendationSettings')}</Card.Title>
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
            </div>
        )
    }
}

export default withTranslation() (Settings);