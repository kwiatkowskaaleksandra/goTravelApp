import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react'
import { Link } from 'react-router-dom'
import {useAuth} from "./AuthContext";
import {Col, Nav, Navbar, NavDropdown, Row} from "react-bootstrap";
import './NavigationBar.css'
import logo from '../assets/image/logo2.svg'
import phone from '../assets/image/phone.svg'
import exotic from '../assets/image/exotic.svg'
import cruise from '../assets/image/cruise.svg'
import allInclusive from '../assets/image/allInclusive.svg'
import longTrip from '../assets/image/longTrip.svg'
import shortTrip from '../assets/image/shortTrip.svg'
import familyTrip from '../assets/image/familyTrip.svg'
import {CgProfile} from "react-icons/cg";

function NavigationBar() {
    const { getUser, userIsAuthenticated, userLogout } = useAuth()

    const logout = () => {
        userLogout()
    }

    const enterMenuStyle = () => {
        return userIsAuthenticated() ? { "display": "none" } : { "display": "block" }
    }

    const logoutMenuStyle = () => {
        return userIsAuthenticated() ? { "display": "block" } : { "display": "none" }
    }

    const getUserName = () => {
        const user = getUser()
        return user ? user.data.firstname : ''
    }

    return (
        <Navbar bg="white" expand="lg" className="navbar-visible">
            <Navbar.Brand as={Link} to={"/"} className="navbar-brand-menu">
                <img alt="" src={logo} width="100" height="113" className="d-inline-block align top"/>
                {' '}</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="ml-auto">
                    <Nav.Link as={Link} to={"/"}  className="navbar-menu">HOME</Nav.Link>
                    <NavDropdown title="OFERTA" id="basic-nav-dropdown" className={"custom-dropdown-menu"} aria-expanded="true">
                        <Row>
                            <Col>
                                <center> <NavDropdown.Item  className="dropdown-item">
                                    <Nav.Link as={Link} to={"/exotics"}  style={{color: 'black'}} >   <img alt="" src={exotic} width="80" height="80" className="d-inline-block align top"/><br/>
                                    {' '}Egzotyka</Nav.Link>
                                    </NavDropdown.Item></center>

                                <center><NavDropdown.Item >
                                    <Nav.Link as={Link} to={"/cruises"}  style={{color: 'black'}} >
                                    <img alt="" src={cruise} width="80" height="80" className="d-inline-block align top"/><br/>
                                    {' '}Rejsy</Nav.Link></NavDropdown.Item></center>

                                <center><NavDropdown.Item >
                                    <Nav.Link as={Link} to={"/allInclusive"}  style={{color: 'black'}} >
                                    <img alt="" src={allInclusive} width="80" height="80" className="d-inline-block align top"/><br/>
                                        {' '}All inclusive</Nav.Link></NavDropdown.Item></center>
                            </Col>
                            <Col>
                                <center><NavDropdown.Item>
                                    <Nav.Link as={Link} to={"/longTrips"}  style={{color: 'black'}} >
                                    <img alt="" src={longTrip} width="80" height="80" className="d-inline-block align top"/><br/>
                                        {' '}Długi pobyt</Nav.Link></NavDropdown.Item></center>
                                <center><NavDropdown.Item >
                                    <Nav.Link as={Link} to={"/shortTrips"}  style={{color: 'black'}} >
                                    <img alt="" src={shortTrip} width="80" height="80" className="d-inline-block align top"/><br/>
                                        {' '}Krótki urlop</Nav.Link></NavDropdown.Item></center>
                                <center><NavDropdown.Item >
                                    <Nav.Link as={Link} to={"/familyTrips"}  style={{color: 'black'}} >
                                    <img alt="" src={familyTrip} width="80" height="80" className="d-inline-block align top"/><br/>
                                        {' '}Wakacje z dziećmi</Nav.Link></NavDropdown.Item></center>
                            </Col>
                        </Row>
                    </NavDropdown>
                    <Nav.Link as={Link} to={"/lastMinute"} className="navbar-menu">LAST MINUTE</Nav.Link>
                    <Nav.Link as={Link} to={"/promotions"} className="navbar-menu">PROMOCJE</Nav.Link>
                    <Nav.Link as={Link} to={"/customerZone/login"} className="btn btn-primary btn-sm float-right" style={enterMenuStyle()}>STREFA KLIENTA</Nav.Link>
                    <Nav.Link as={Link} to={"/help"} className="navbar-menu-help"> <img alt="" src={phone} width="25" height="25" />41 780 23 32</Nav.Link>
                    <Nav.Link as={Link} to={"/help"} className="navbar-menu-help"> <img alt="" src={phone} width="25" height="25" />41 780 23 32</Nav.Link>



                    <Nav.Link as={Link} to={"/myProfile"} style={logoutMenuStyle()}  className={"btn btn-primary btn-sm float-right user-info-navbar"}>
                       <CgProfile/> {`Witaj ${getUserName()} !`}
                    </Nav.Link>

                    <Nav.Link as={Link} to={"/"} style={logoutMenuStyle()} onClick={logout} className={"btn btn-primary btn-sm float-right logout-navbar"}>Wyloguj się</Nav.Link>

                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )
}
export default NavigationBar
