import NavigationBar from "../NavigationBar";
import Footer from "../Footer";
import React from "react";
import './Company.css'
import {BiHomeAlt2} from "react-icons/bi";
import {MdOutlineAlternateEmail, MdOutlineContactSupport} from "react-icons/md";
import {BsFillTelephoneInboundFill} from "react-icons/bs";
import logo from '../../assets/image/logo2.svg'

function Contact(){
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
                          <h2 className={"company"}>DANE FIRMY</h2>
                          <p className={"info"}>Go Travel Poland Sp. z o.o. </p>
                      </div>

                    </div>

                    <section className='d-flex justify-content-center justify-content-lg-between p-2 border-bottom'></section>
                    <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%'}}> <BiHomeAlt2/>Siedziba firmy</p>
                    <p className={"info"} style={{ marginLeft: '20%'}}>ul. Wesoła 76A</p>
                    <p className={"info"} style={{ marginLeft: '20%'}}>02-122 Kielce</p>
                    <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%'}}> <MdOutlineContactSupport/>Dane do kontaktu</p>
                    <p className={"info"}><BsFillTelephoneInboundFill style={{marginRight:'5px', marginLeft: '20%'}}/>biuro.pomocy@gotravel.pl</p>
                    <p className={"info"}><MdOutlineAlternateEmail style={{marginRight:'5px', marginLeft: '20%'}}/>+48 555 666 777</p>
                </div>
            </section>
            <Footer/>
        </div>
    );
}

export default Contact;