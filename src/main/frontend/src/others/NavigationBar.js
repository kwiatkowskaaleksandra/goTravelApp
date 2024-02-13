import 'bootstrap/dist/css/bootstrap.min.css';
import React, {useEffect, useState} from 'react'
import {Link} from 'react-router-dom'
import {useAuth} from "./AuthContext";
import {Col, Nav, Navbar, NavDropdown, Row} from "react-bootstrap";
import './NavigationBar.css'
import logo from '../assets/image/logo2.svg'
import exotic from '../assets/image/exotic.svg'
import cruise from '../assets/image/cruise.svg'
import allInclusive from '../assets/image/allInclusive.svg'
import longTrip from '../assets/image/longTrip.svg'
import shortTrip from '../assets/image/shortTrip.svg'
import familyTrip from '../assets/image/familyTrip.svg'
import {CgProfile} from "react-icons/cg";
import {useTranslation} from "react-i18next";
import FlagSelect from 'react-flags-select';

const languages = [
    {value: 'pl', text: 'Polish', code: 'PL'},
    {value: 'en', text: 'English', code: 'GB'}
]

function NavigationBar() {

    const {t} = useTranslation()
    const [lang, setLang] = useState('')

    useEffect(() => {
        const storedLang = localStorage.getItem('selectedLang')
        if (storedLang) {
            setLang(storedLang);
        }
    },[]);

    const handleChangeLang = (e) => {
        const selectedLang = languages.find(lang => lang.code === e)
        setLang(selectedLang.value);
        localStorage.setItem('selectedLang', selectedLang.code);
        let loc = "http://localhost:3000/";
        window.location.replace(loc + "?lng=" + selectedLang.value);
    }

    const {getUser, userIsAuthenticated, userLogout} = useAuth()
    const logout = async () => {
        await userLogout()
    }

    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const checkAuthentication = async () => {
            console.log("Checking authentication...");
            const isAuthenticated = await userIsAuthenticated();
            console.log("Authentication:", isAuthenticated);
            setIsAuthenticated(isAuthenticated);
        };

        checkAuthentication();
    }, );

    const enterMenuStyle = () => {
        return isAuthenticated ? {"display": "none"} : {"display": "block"}
    }

    const logoutMenuStyle = () => {
        return isAuthenticated ? {"display": "block"} : {"display": "none"}
    }

    const getUserName = () => {
        const user = getUser()
        return user ? user.data.sub : ''
    }

    return (
        <Navbar bg="white" expand="lg" className="navbar-visible">

            <Navbar.Brand as={Link} to={"/"} className="navbar-brand-menu">
                <img alt="" src={logo} width="100" height="113" className="d-inline-block align top"/>
                {' '}</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="ml-auto">
                    <Nav.Link as={Link} to={"/"} className="navbar-menu">{t('home').toUpperCase()}</Nav.Link>
                    <NavDropdown title={t('offer').toUpperCase()} id="basic-nav-dropdown" className={"custom-dropdown-menu"}
                                 aria-expanded="true">
                        <Row>
                            <Col>
                                <center><NavDropdown.Item className="dropdown-item">
                                    <Nav.Link as={Link} to={"/allOffers/exotics"} style={{color: 'black'}}> <img alt=""
                                                                                                       src={exotic}
                                                                                                       width="80"
                                                                                                       height="80"
                                                                                                       className="d-inline-block align top"/><br/>
                                        {t('exotics')}</Nav.Link>
                                </NavDropdown.Item></center>

                                <center><NavDropdown.Item>
                                    <Nav.Link as={Link} to={"/allOffers/cruises"} style={{color: 'black'}}>
                                        <img alt="" src={cruise} width="80" height="80"
                                             className="d-inline-block align top"/><br/>
                                        {t('cruises')}</Nav.Link></NavDropdown.Item></center>

                                <center><NavDropdown.Item>
                                    <Nav.Link as={Link} to={"/allOffers/allInclusive"} style={{color: 'black'}}>
                                        <img alt="" src={allInclusive} width="80" height="80"
                                             className="d-inline-block align top"/><br/>
                                        {t('allInclusive')}</Nav.Link></NavDropdown.Item></center>
                            </Col>
                            <Col>
                                <center><NavDropdown.Item>
                                    <Nav.Link as={Link} to={"/allOffers/longTrips"} style={{color: 'black'}}>
                                        <img alt="" src={longTrip} width="80" height="80"
                                             className="d-inline-block align top"/><br/>
                                        {t('longTrips')}</Nav.Link></NavDropdown.Item></center>
                                <center><NavDropdown.Item>
                                    <Nav.Link as={Link} to={"/allOffers/shortTrips"} style={{color: 'black'}}>
                                        <img alt="" src={shortTrip} width="80" height="80"
                                             className="d-inline-block align top"/><br/>
                                        {t('shortTrips')}</Nav.Link></NavDropdown.Item></center>
                                <center><NavDropdown.Item>
                                    <Nav.Link as={Link} to={"/allOffers/familyTrips"} style={{color: 'black'}}>
                                        <img alt="" src={familyTrip} width="80" height="80"
                                             className="d-inline-block align top"/><br/>
                                        {t('familyTrips')}</Nav.Link></NavDropdown.Item></center>
                            </Col>
                        </Row>
                    </NavDropdown>
                    <Nav.Link as={Link} to={"/allOffers/lastMinute"} className="navbar-menu">{t('lastMinute').toUpperCase()}</Nav.Link>
                    <Nav.Link as={Link} to={"/allOffers/promotions"} className="navbar-menu">{t('promotions').toUpperCase()}</Nav.Link>
                    <Nav.Link as={Link} to={"/yourOwnOffer"} className="navbar-menu">{t('yourOwnOffer').toUpperCase()}</Nav.Link>

                    <Nav.Link as={Link} to={"/customerZone/login"} className="btn btn-primary btn-sm float-right mt-5"
                              style={enterMenuStyle()}>{t('customerZone').toUpperCase()}</Nav.Link>

                    <Nav.Link as={Link} to={"/myProfile"} style={logoutMenuStyle()}
                              className={"btn btn-primary btn-sm float-right user-info-navbar"}>
                        <CgProfile/> {t('hello')} {` ${getUserName()} !`}
                    </Nav.Link>

                    <Nav.Link as={Link} to={"/"} style={logoutMenuStyle()} onClick={logout}
                              className={"btn btn-primary btn-sm float-right logout-navbar"}>{t('logout')}</Nav.Link>

                </Nav>
            </Navbar.Collapse>
            <FlagSelect className={"flag-select-container"} countries={languages.map(lang => lang.code)}
                        customLabels={languages.reduce((acc, lang) => {
                            acc[lang.code] = lang.text;
                            return acc;
                        }, {})}
                        selected={lang}
                        onSelect={handleChangeLang}
            />
        </Navbar>
    )
}

export default NavigationBar
