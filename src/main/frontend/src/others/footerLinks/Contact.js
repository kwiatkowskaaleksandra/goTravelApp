import NavigationBar from "../../pages/home/NavigationBar";
import Footer from "../../pages/home/Footer";
import React from "react";
import './Company.css'
import {BiHomeAlt2} from "react-icons/bi";
import {MdOutlineAlternateEmail, MdOutlineContactSupport} from "react-icons/md";
import {BsFillTelephoneInboundFill} from "react-icons/bs";
import logo from '../../assets/image/logo2.svg'
import {useTranslation} from "react-i18next";

function Contact(){
    const {t} = useTranslation()
    return(
        <div>
            <NavigationBar/>
            <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'></section>
            <section>
                <div>
                    <div className={"row"}>
                        <div className={"col"} style={{display:'contents'}}>
                            <img alt="" src={logo} width="150" height="163" className="d-inline-block align top" />
                        </div>
                      <div  className={"col-6"} style={{width: '75%'}}>
                          <h2 className={"info"}>Go Travel Sp. z o.o. </h2>
                      </div>

                    </div>

                    <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%'}}> <BiHomeAlt2/>{t('headquarters')}</p>
                    <p className={"info"} style={{ marginLeft: '20%'}}>ul. Wesoła 76A</p>
                    <p className={"info"} style={{ marginLeft: '20%'}}>02-122 Kielce</p>
                    <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%'}}> <MdOutlineContactSupport/>{t('contactDetails')}</p>
                    <p className={"info"}><BsFillTelephoneInboundFill style={{marginRight:'5px', marginLeft: '20%'}}/>+48 555 666 777</p>
                    <p className={"info"}><MdOutlineAlternateEmail style={{marginRight:'5px', marginLeft: '20%'}}/>gotravel.travelagency@gmail.com</p>
                </div>
            </section>
            <Footer/>
        </div>
    );
}

export default Contact;