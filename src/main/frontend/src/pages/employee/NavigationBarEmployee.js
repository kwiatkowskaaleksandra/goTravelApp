import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react'
import {Link} from 'react-router-dom'
import {Nav, Navbar} from "react-bootstrap";
import '../home/NavigationBar.css'
import logo from '../../assets/image/logo2.svg'
import {CgProfile} from "react-icons/cg";
import {useTranslation} from "react-i18next";
import FlagSelect from 'react-flags-select';

const languages = [
    {value: 'pl', text: 'Polish', code: 'PL'},
    {value: 'en', text: 'English', code: 'GB'}
]

function NavigationBarEmployee({ isAuthenticated, lang, onLogout, onChangeLang, username}) {

    const {t} = useTranslation()

    const enterMenuStyle = () => {
        return isAuthenticated ? {"display": "none"} : {"display": "block"}
    }

    const logoutMenuStyle = () => {
        return isAuthenticated ? {"display": "block"} : {"display": "none"}
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

                    <Nav.Link as={Link} to={"/customerZone/login"} className="btn btn-primary btn-sm float-right mt-5"
                              style={enterMenuStyle()}>{t('customerZone').toUpperCase()}</Nav.Link>

                    <Nav.Link as={Link} style={logoutMenuStyle()}
                              className={"btn btn-primary btn-sm float-right user-info-navbar"}>
                        <CgProfile/> {username}
                    </Nav.Link>

                    <Nav.Link as={Link} to={"/"} style={logoutMenuStyle()} onClick={onLogout}
                              className={"btn btn-primary btn-sm float-right logout-navbar"}>{t('logout')}</Nav.Link>

                </Nav>
            </Navbar.Collapse>
            <FlagSelect className={"flag-select-container"} countries={languages.map(lang => lang.code)}
                        customLabels={languages.reduce((acc, lang) => {
                            acc[lang.code] = lang.text;
                            return acc;
                        }, {})}
                        selected={lang}
                        onSelect={onChangeLang}
            />
        </Navbar>
    )
}

export default NavigationBarEmployee
