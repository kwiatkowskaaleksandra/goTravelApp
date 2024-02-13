import NavigationBar from "../NavigationBar";
import Footer from "../Footer";
import React from "react";
import {FaUmbrella} from "react-icons/fa";
import {BsCheck2All} from "react-icons/bs";
import {useTranslation} from "react-i18next";

function Insurance() {
    const {t} = useTranslation()
    return (
        <div>
            <NavigationBar/>
            <section className='d-flex justify-content-center justify-content-lg-between p-2 border-bottom'></section>
            <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%', color: '#4ec3ff'}}>
                <FaUmbrella style={{width: "40px", height: "40px"}}/> {t('insurance')}</p>

            <div style={{margin: '1rem 10%', height: '500px'}}>
                <div className="d-flex flex-row justify-content-center justify-content-md-between">

                    <div className="card insurance-card" style={{marginLeft: '10px'}}>
                        <div className="card-body">
                            <h5 className="card-title">{t('standard').toUpperCase()}</h5>
                            <h6 className="card-subtitle mb-2 text-muted">{t('mostFrequentlyChosen')}</h6>
                            <p className="card-text">
                                <p><BsCheck2All color={'#4ec3ff'}
                                                style={{width: "20px", height: "20px"}}/>{t('standardPart1')}</p>
                                <p><BsCheck2All color={'#4ec3ff'}
                                                style={{width: "20px", height: "20px"}}/>{t('standardPart2')}</p>
                            </p>
                        </div>
                    </div>

                    <div className="card insurance-card" style={{marginLeft: '10px'}}>
                        <div className="card-body">
                            <h5 className="card-title">{t('standardAndResignation').toUpperCase()}</h5>
                            <h6 className="card-subtitle mb-2 text-muted"></h6>
                            <p className="card-text">
                                <p><BsCheck2All color={'#4ec3ff'} style={{
                                    width: "20px",
                                    height: "20px"
                                }}/>{t('standardAndResignationPart1')}</p>
                                <p><BsCheck2All color={'#4ec3ff'} style={{
                                    width: "20px",
                                    height: "20px"
                                }}/>{t('standardAndResignationPart2')}</p>
                                <p><BsCheck2All color={'#4ec3ff'} style={{
                                    width: "20px",
                                    height: "20px"
                                }}/>{t('standardAndResignationPart3')}</p>
                            </p>
                        </div>
                    </div>

                    <div className="card insurance-card" style={{marginLeft: '10px'}}>
                        <div className="card-body">
                            <h5 className="card-title">{t('optimumAndResignation').toUpperCase()}</h5>
                            <h6 className="card-subtitle mb-2 text-muted"></h6>
                            <p className="card-text">
                                <p><BsCheck2All color={'#4ec3ff'} style={{
                                    width: "20px",
                                    height: "20px"
                                }}/>{t('optimumAndResignationPart1')}</p>
                                <p><BsCheck2All color={'#4ec3ff'} style={{
                                    width: "20px",
                                    height: "20px"
                                }}/>{t('optimumAndResignationPart2')}</p>
                                <p><BsCheck2All color={'#4ec3ff'} style={{
                                    width: "20px",
                                    height: "20px"
                                }}/>{t('optimumAndResignationPart3')}</p>
                            </p>
                        </div>
                    </div>

                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default Insurance