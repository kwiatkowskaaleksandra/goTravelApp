import NavigationBar from "../../pages/home/NavigationBar";
import Footer from "../../pages/home/Footer";
import React from "react";
import './Company.css'
import logo from '../../assets/image/logo2.svg'
import {useTranslation} from "react-i18next";

function About(){
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
                            <h2 className={"company"}>{t('aboutCompany').toUpperCase()}</h2>
                            <p className={"info"}>Go Travel Sp. z o.o. </p>
                        </div>

                    </div>

                    <section className='d-flex justify-content-center justify-content-lg-between p-2 border-bottom'></section>
                    <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%', color: '#0066f0'}}>
                        {t('aboutCompanyTitle').toUpperCase()}
                    </p>
                    <p style={{textAlign: 'justify',textJustify: 'inter-word', marginLeft: '20%', marginRight: '20%'}}>{t('aboutCompanyPart1')}</p>

                    <p style={{textAlign: 'justify',textJustify: 'inter-word', marginLeft: '20%', marginRight: '20%'}}>
                        {t('aboutCompanyPart2')}
                        <ul>
                            <li>
                                {t('aboutCompanyPart3')}
                            </li>
                            <li>
                                {t('aboutCompanyPart4')}
                            </li>
                            <li>
                                {t('aboutCompanyPart5')}
                            </li>
                            <li>
                                {t('aboutCompanyPart6')}
                            </li>
                            <li>
                                {t('aboutCompanyPart7')}
                            </li>
                            <li>
                                {t('aboutCompanyPart8')}
                            </li>
                            <li>
                                {t('aboutCompanyPart9')}
                            </li>
                            <li>
                                {t('aboutCompanyPart10')}
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