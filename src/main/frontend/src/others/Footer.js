import React from 'react';
import {MDBCol, MDBContainer, MDBFooter, MDBRow} from "mdb-react-ui-kit";
import {MDBIcon} from 'mdbreact';
import logo from '../assets/image/logo2.svg'
import './Footer.css'
import {BsFillTelephoneInboundFill} from "react-icons/bs";
import {MdMarkEmailUnread} from "react-icons/md";
import {HiHome} from "react-icons/hi"
import rodo from '../assets/RODO.pdf'
import privacyPolicy from '../assets/POLITYKA_PRYWATNOŚCI.pdf'
import {useTranslation} from "react-i18next";


function Footer() {
    const {t} = useTranslation()
    
    return (
        <MDBFooter bgColor='light' className='text-center text-lg-start text-muted'>
            <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'></section>

            <section className=''>
                <MDBContainer className='text-center text-md-start mt-5'>
                    <MDBRow className='mt-3'>
                        <MDBCol md="3" lg="4" xl="3" className='mx-auto mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'>
                                <MDBIcon className="me-3" icon={logo}> <img alt="" src={logo} width="170" height="183"
                                                                className="d-inline-block align top"/></MDBIcon>
                            </h6>
                        </MDBCol>

                        <MDBCol md="2" lg="2" xl="2" className='mx-auto mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'>{t('aboutUs').toUpperCase()}</h6>
                            <p>
                                <a href='/contact' className='text-reset'>
                                    {t('contact')}
                                </a>
                            </p>
                            <p>
                                <a href='/about' className='text-reset'>
                                    {t('aboutGoTravel')}
                                </a>
                            </p>
                            <p>
                                <a href={rodo} className='text-reset'>
                                    {t('rodo')}
                                </a>
                            </p>
                            <p>
                                <a href={privacyPolicy} className='text-reset'>
                                    {t('privacyPolicy')}
                                </a>
                            </p>
                        </MDBCol>

                        <MDBCol md="3" lg="2" xl="2" className='mx-auto mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'> {t('forCustomers').toUpperCase()}</h6>
                            <p>
                                <a href='/airlines' className='text-reset'>
                                    {t('airlines')}
                                </a>
                            </p>
                            <p>
                                <a href='/insurance' className='text-reset'>
                                    {t('insurance')}
                                </a>
                            </p>
                        </MDBCol>

                        <MDBCol md="4" lg="3" xl="3" className='mx-auto mb-md-0 mb-4'>
                            <h6 className='text-uppercase fw-bold mb-4'>{t('contact').toUpperCase()}</h6>
                            <p>
                                <HiHome/> ul. Wesoła 76A, Kielce
                            </p>
                            <p>
                                <MdMarkEmailUnread/> gotravel.travelagency@gmail.com
                            </p>
                            <p>
                                <BsFillTelephoneInboundFill/> +48 555 666 777<br/><p/>
                                <center>{t('everyDayDuringHours')} 8:00 - 22:00</center>

                            </p>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>
            </section>
            <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'></section>
            <div className='text-center p-4'>
                © 2024 <b> Go Travel Poland Sp. z o.o.</b>
            </div>
            <section className='d-flex justify-content-center justify-content-lg-between p-1 border-bottom'></section>
        </MDBFooter>
    );
}

export default Footer;