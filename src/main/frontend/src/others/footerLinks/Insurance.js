import NavigationBar from "../NavigationBar";
import Footer from "../Footer";
import React from "react";
import {FaUmbrella} from "react-icons/fa";
import {BsCheck2All} from "react-icons/bs";

function Insurance() {
    return(
        <div>
            <NavigationBar/>
            <section className='d-flex justify-content-center justify-content-lg-between p-2 border-bottom'></section>
            <p className={"info"} style={{fontSize: '20px', fontWeight: 'bold', marginLeft: '20%', color: '#0066f0'}}><FaUmbrella style={{width: "40px", height: "40px"}}/>    Ubezpieczenia</p>

            <div style={{marginLeft: '10%', marginRight: '10%', height: '500px'}}>
                <div className="row">
                    <div className="col-sm-4">
                        <div className="card" style={{width: '30rem'}}>
                            <div className="card-body">
                                <h5 className="card-title">STANDARDOWY</h5>
                                <h6 className="card-subtitle mb-2 text-muted">Najczęściej wybierane</h6>
                                <p className="card-text">
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Koszty leczenia, transportu i repatriacji - do 250 000 PLN</p>
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Pakiet ubezpieczeń: NNW, Assistance, bagażu podróżnego i OC</p>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div className="col-sm-4">
                        <div className="card" style={{width: '30rem'}}>
                            <div className="card-body">
                                <h5 className="card-title">STANDARDOWY + REZYGNACJA 100%</h5>
                                <h6 className="card-subtitle mb-2 text-muted"> </h6>
                                <p className="card-text">
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Koszty leczenia, transportu i repatriacji - do 250 000 PLN</p>
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Zwrot kosztów wyjazdu z tytułu rezygnacji albo skrócenia wyjazdu</p>
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Pakiet ubezpieczeń: NNW, Assistance, bagażu podróżnego i OC</p>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div className="col-sm-4">
                        <div className="card" style={{width: '30rem'}}>
                            <div className="card-body">
                                <h5 className="card-title">OPTYMALNY + REZYGNACJA 100%</h5>
                                <h6 className="card-subtitle mb-2 text-muted"> </h6>
                                <p className="card-text">
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Koszty leczenia, transportu i repatriacji - bez limitu</p>
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Zwrot kosztów wyjazdu z tytułu rezygnacji albo skrócenia wyjazdu</p>
                                    <p><BsCheck2All color={ '#0066f0'} style={{width: "20px", height: "20px"}}/>Pakiet ubezpieczeń: NNW, Assistance, bagażu podróżnego i OC</p>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default Insurance