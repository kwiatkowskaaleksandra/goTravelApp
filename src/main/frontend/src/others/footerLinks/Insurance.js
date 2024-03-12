import NavigationBar from "../NavigationBar";
import Footer from "../Footer";
import React, {useEffect, useState} from "react";
import {FaUmbrella} from "react-icons/fa";
import {BsCheck2All} from "react-icons/bs";
import {useTranslation} from "react-i18next";
import {goTravelApi} from "../GoTravelApi";

function Insurance() {
    const [insurances, setInsurances] = useState([]);

    useEffect(() => {
        const fetchInsurances = async () => {
            try {
                const insurancesData = await goTravelApi.getAllInsurances();
                setInsurances(insurancesData.data);
                console.log(insurances); // Insurances data after setting state
            } catch (error) {
                console.error('Error fetching insurances:', error);
            }
        };
        fetchInsurances()
    }, [insurances]);


    const {t} = useTranslation()
    return (
        <div>
            <NavigationBar/>
            <section className='d-flex justify-content-center justify-content-lg-between p-2 border-bottom'></section>
            <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%', color: '#4ec3ff', marginTop: '40px'}}>
                <FaUmbrella style={{width: "40px", height: "40px"}}/> {t('insurance')}</p>

            <div style={{margin: '1rem 10%', height: '500px'}}>
                <div className="d-flex flex-row justify-content-center justify-content-md-between">

                    <div className="card insurance-card" style={{marginLeft: '10px'}}>
                        <div className="card-body">
                            <h5 className="card-title">{t('standard').toUpperCase()}</h5>
                            <h6 className="card-subtitle mb-2 text-muted"/>
                            <p className="card-text">
                                <p><BsCheck2All color={'#4ec3ff'}
                                                style={{width: "20px", height: "20px"}}/>{t('standardPart1')}</p>
                                <p><BsCheck2All color={'#4ec3ff'}
                                                style={{width: "20px", height: "20px"}}/>{t('standardPart2')}</p>
                            </p>
                        </div>
                        <div className={"card-footer"}>
                            {insurances.map(i => {
                                    if (i.idInsurance === 1) {
                                        return (
                                            <div key={i.idInsurance}>
                                                <h6 className="card-subtitle mb-2 text-muted" style={{textAlign: 'center'}}>{t('goTravelNamespace2:price')}: {i.price} PLN</h6>
                                            </div>
                                        );
                                    }
                                    return null;
                                })
                            }
                        </div>
                    </div>

                    <div className="card insurance-card" style={{marginLeft: '10px'}}>
                        <div className="card-body">
                            <h5 className="card-title">{t('standardAndResignation').toUpperCase()}</h5>
                            <h6 className="card-subtitle mb-2 text-muted">{t('mostFrequentlyChosen')}</h6>
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
                        <div className={"card-footer"}>
                            {insurances.map(i => {
                                if (i.idInsurance === 2) {
                                    return (
                                        <div key={i.idInsurance}>
                                            <h6 className="card-subtitle mb-2 text-muted" style={{textAlign: 'center'}}>{t('goTravelNamespace2:price')}: {i.price} PLN</h6>
                                        </div>
                                    );
                                }
                                return null;
                            })
                            }
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
                        <div className={"card-footer"}>
                            {insurances.map(i => {
                                if (i.idInsurance === 3) {
                                    return (
                                        <div key={i.idInsurance}>
                                            <h6 className="card-subtitle mb-2 text-muted" style={{textAlign: 'center'}}>{t('goTravelNamespace2:price')}: {i.price} PLN</h6>
                                        </div>
                                    );
                                }
                                return null;
                            })
                            }
                        </div>
                    </div>

                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default Insurance