import React from 'react';
import {MDBCol, MDBContainer, MDBFooter, MDBRow} from "mdb-react-ui-kit";
import {MDBIcon} from 'mdbreact';
import logo from '../assets/image/logo2.svg'
import './Footer.css'
import {BsFillTelephoneInboundFill} from "react-icons/bs";
import {MdMarkEmailUnread} from "react-icons/md";
import {HiHome} from "react-icons/hi"

function Footer() {
    return (
        <MDBFooter bgColor='light' className='text-center text-lg-start text-muted'>
            <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'></section>

            <section className=''>
                <MDBContainer className='text-center text-md-start mt-5'>
                    <MDBRow className='mt-3'>
                        <MDBCol md="3" lg="4" xl="3" className='mx-auto mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'>
                                <MDBIcon className="me-3"> <img alt="" src={logo} width="170" height="183"
                                                                className="d-inline-block align top"/></MDBIcon>
                            </h6>
                        </MDBCol>

                        <MDBCol md="2" lg="2" xl="2" className='mx-auto mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'>O NAS</h6>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    Kontakt
                                </a>
                            </p>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    O Go Travel
                                </a>
                            </p>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    RODO
                                </a>
                            </p>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    Polityka prywatności
                                </a>
                            </p>
                        </MDBCol>

                        <MDBCol md="3" lg="2" xl="2" className='mx-auto mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'>DLA KLIENTÓW</h6>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    Lista hoteli
                                </a>
                            </p>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    Lista lotnisk
                                </a>
                            </p>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    Ubezpieczenia
                                </a>
                            </p>
                            <p>
                                <a href='src/main/frontend/src/misc/Footer#!' className='text-reset'>
                                    Pomoc
                                </a>
                            </p>
                        </MDBCol>

                        <MDBCol md="4" lg="3" xl="3" className='mx-auto mb-md-0 mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'>KONTAKT</h6>
                            <p>
                                <HiHome/> Warszawa, 10012, US
                            </p>
                            <p>
                                <MdMarkEmailUnread/> callcenter@gotravel.pl
                            </p>
                            <p>
                                <BsFillTelephoneInboundFill/> + 41 780 23 32<br/><p/>
                                <center>codziennie w godzinach 8:00 - 22:00</center>

                            </p>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>
            </section>
            <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'></section>
            <div className='text-center p-4'>
                © 2023 <b> Go Travel Poland Sp. z o.o.</b>
            </div>
            <section className='d-flex justify-content-center justify-content-lg-between p-1 border-bottom'></section>
        </MDBFooter>
    );
}

export default Footer;