import NavigationBar from "../NavigationBar";
import Footer from "../Footer";
import React from "react";

function Airlines() {
    return (
        <div>
            <NavigationBar/>
            <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'></section>
            <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%',  color: '#0066f0'}}> Główne linie czarterowe</p>
            <div>
                <div className={"row"} style={{marginLeft: "10%"}}>
                    <div className={"col"} >
                        <a href={"https://www.smartwings.com/pl/"}>
                            <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/smartwings_logo.jpg"}   className="d-inline-block align top" />
                        </a>
                    </div>
                    <div className={"col"} >
                        <a href={"https://www.lot.com/pl/pl"}>
                            <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/logo_lot.jpg"}  className="d-inline-block align top" />
                        </a>
                    </div>
                    <div className={"col"} >
                        <a href={"https://www.enterair.pl"}>
                            <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/logo_enter.jpg"}  className="d-inline-block align top" />
                        </a>
                    </div>
                </div>
                <div className={"row"} style={{marginLeft: "10%",  marginTop: "3%"}}>
                    <div className={"col"} >
                        <a href={"https://www.electra-airways.com/"}>
                            <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/electra-airwaysv2.png"}   className="d-inline-block align top" />
                        </a>
                    </div>
                    <div className={"col"} >
                        <a href={"https://www.buzzair.com/"}>
                            <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/logo-buzz-full.jpg"}  className="d-inline-block align top" />
                        </a>
                    </div>
                    <div className={"col"} >
                        <a href={"https://www.skyexpress.gr/en"}>
                            <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/sky_express.jpg"}  className="d-inline-block align top" />
                        </a>
                    </div>
                </div>
            </div>

            <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'></section>
            <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%',  color: '#0066f0'}}> Linie rejsowe</p>
            <div className={"row"} style={{marginLeft: "10%"}}>
                <div className={"col"} >
                    <a href={"https://www.klm.pl/"}>
                        <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/logo_klm.jpg"}   className="d-inline-block align top" />
                    </a>
                </div>
                <div className={"col"} >
                    <a href={"https://www.easyjet.com/pl"}>
                        <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/easy_jet.png"}  className="d-inline-block align top" />
                    </a>
                </div>
                <div className={"col"} >
                    <a href={"https://www.finnair.com/pl-pl"}>
                        <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/finnair.jpg"}  className="d-inline-block align top" />
                    </a>
                </div>
            </div>
            <div className={"row"} style={{marginLeft: "10%",  marginTop: "3%"}}>
                <div className={"col"} >
                    <a href={"https://www.britishairways.com/travel/home/public/pl_pl/"}>
                        <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/british_airways.png?v2"}   className="d-inline-block align top" />
                    </a>
                </div>
                <div className={"col"} >
                    <a href={"https://en.aegeanair.com/"}>
                        <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/logo_aegean.jpg"}  className="d-inline-block align top" />
                    </a>
                </div>
                <div className={"col"} >
                    <a href={"https://wwws.airfrance.pl/pl"}>
                        <img alt="" src={"https://www.itaka.pl/cms/img/u/podstrony/linie_main/logo_airfrance.jpg"}  className="d-inline-block align top" />
                    </a>
                </div>
            </div>
            <Footer/>
        </div>
    );
}

export default Airlines