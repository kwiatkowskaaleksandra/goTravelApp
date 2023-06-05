import NavigationBar from "../NavigationBar";
import Footer from "../Footer";
import React from "react";
import './Company.css'
import {BiHomeAlt2} from "react-icons/bi";
import {MdOutlineAlternateEmail, MdOutlineContactSupport} from "react-icons/md";
import {BsFillTelephoneInboundFill} from "react-icons/bs";
import logo from '../../assets/image/logo2.svg'

function About(){
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
                            <h2 className={"company"}>O FIRMIE</h2>
                            <p className={"info"}>Go Travel Poland Sp. z o.o. </p>
                        </div>

                    </div>

                    <section className='d-flex justify-content-center justify-content-lg-between p-2 border-bottom'></section>
                    <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%', color: '#0066f0'}}>
                        GO TRAVEL POLAND – 23 LATA W POLSCE W TYM 15 LAT JAKO WEZYR HOLIDAYS
                    </p>
                    <p style={{textAlign: 'justify',textJustify: 'inter-word', marginLeft: '20%', marginRight: '20%'}}>
                        Jesteśmy jednym z czterech największych touroperatorów w Polsce. Powstaliśmy w 1999 roku. Pod obecną marką funkcjonujemy od chwili połączenia dwóch marek Grupy OTI Holding na polskim rynku: Wezyr Holidays i Go Travel Poland. To połączenie 15 lat doświadczenia Wezyr Holidays na lokalnym,
                        polskim rynku z międzynarodową wiedzą i siłą Go Travel Poland. Synergia rozwoju i działania scalone pod wspólną marką to wiele korzyści dla klientów, przede wszystkim bogatsza oferta oraz obsługa i serwis pod kontrolą. </p>

                    <p style={{textAlign: 'justify',textJustify: 'inter-word', marginLeft: '20%', marginRight: '20%'}}>
                        Kilka faktów o nas:
                        <ul>
                            <li>
                                proponujemy wakacje czarterowe do 15 kierunków z wylotami z 12 lotnisk lokalnych w Polsce,
                            </li>
                            <li>
                                jesteśmy specjalistami na kierunku Turcja, gdzie w szczycie sezonu latamy tam codziennie z Warszawy, Poznania, Wrocławia i Katowic,
                            </li>
                            <li>
                                blisko 40% wszystkich polskich turystów spędzających zorganizowane wakacje w Turcji to Klienci Go Travel Poland,
                            </li>
                            <li>
                                nieustannie wzbogacamy swoją ofertę o nowe produkty i obecnie proponujemy, obok Turcji, która jest kierunkiem flagowym, Grecję, Hiszpanię, Bułgarię, Egipt, Tunezję oraz kraje egzotyczne, takie jak Dominikana, Kuba, Tajlandia, Mauritius czy
                                Zjednoczone Emiraty Arabskie,
                            </li>
                            <li>
                                jesteśmy coraz bliżej naszych Klientów rozszerzając siatkę punktów sprzedaży w całej Polsce i dzisiaj mamy 150 autoryzowanych biur podróży, 52 salony własne i franczyzowe oraz współpracujemy z 2000 biur agencyjnych w całej Polsce,
                            </li>
                            <li>
                                posiadamy kapitał zakładowy 20 mln złotych,
                            </li>
                            <li>
                                mamy wykupioną gwarancją ubezpieczeniową 200 000 000 złotych dając naszym Klientom pewność i bezpieczeństwo wakacji,
                            </li>
                            <li>
                                od 2010 r posiadamy akredytowany certyfikat Systemu Zarządzania Jakością ISO 9001: 2015 i z powodzeniem kontynuujemy akredytację
                            </li>
                        </ul>
                    </p>
                </div>
            </section>
            <Footer/>
        </div>
    );
}

export default About;