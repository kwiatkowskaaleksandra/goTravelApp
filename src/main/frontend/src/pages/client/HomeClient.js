import React, {Component} from 'react'
import '../home/Home.css'
import Carousel from 'react-bootstrap/Carousel';
import {TbBeach} from "react-icons/tb"
import {TiWeatherSunny} from "react-icons/ti"
import Turkey from '../../assets/image/turkiye.PNG'
import Cyprus from '../../assets/image/cyprus.png'
import Tunisia from '../../assets/image/tunisia.svg'
import Italy from '../../assets/image/italy.svg'
import Spain from '../../assets/image/spain.svg'
import Croatia from '../../assets/image/croatia.svg'
import Bulgaria from '../../assets/image/bulgaria.svg'
import Greece from '../../assets/image/greece.svg'
import Holiday from '../../assets/image/holiday.png'
import {Container} from "react-bootstrap";
import Footer from "../home/Footer";
import NavigationBar from "../home/NavigationBar";
import {goTravelApi} from "../../others/GoTravelApi";
import {withTranslation} from "react-i18next";
import Search from "./Search";

class HomeClient extends Component {

    state = {
        isExpandedPost1: false,
        isExpandedPost2: false,
        isExpandedPost3: false,
    }

    toggleExpandPost1 = () => {
        this.setState(prevState => ({
            isExpandedPost1: !prevState.isExpandedPost1
        }));
    };

    toggleExpandPost2 = () => {
        this.setState(prevState => ({
            isExpandedPost2: !prevState.isExpandedPost2
        }));
    };

    toggleExpandPost3 = () => {
        this.setState(prevState => ({
            isExpandedPost3: !prevState.isExpandedPost3
        }));
    };

    render() {
        const {t} = this.props
        const { isExpandedPost1, isExpandedPost2, isExpandedPost3  } = this.state;

        return (
            <main>
                <NavigationBar/>
                <header>
                    <section className="search-sec">
                        <Search/>
                    </section>
                    <section className='d-flex justify-content-center justify-content-lg-between p-1 '></section>
                    <Container>
                        <section>
                            <center>
                                <Carousel>
                                    <Carousel.Item interval={1000}>
                                        <img
                                            className="d-block"
                                            src={Turkey}
                                            alt=" "
                                        />
                                        <Carousel.Caption>
                                            <h3 className={"carusel-first"}>{t('türkiyeAndEgypt').toUpperCase()}<br/>{t('forWinter').toUpperCase()}</h3>
                                        </Carousel.Caption>
                                    </Carousel.Item>
                                    <Carousel.Item interval={1000}>
                                        <img
                                            src={Holiday}
                                            alt=""
                                        />
                                        <Carousel.Caption className={"carusel-second"}>
                                            <h3> {t('summer2024').toUpperCase()} - <br/>{t('presale').toUpperCase()}</h3>
                                            <p>
                                                <ul>
                                                    <li>
                                                        {t('advancePayment')}
                                                    </li>
                                                    <li>
                                                        {t('freeReservationChange')}
                                                    </li>
                                                </ul>
                                            </p>
                                        </Carousel.Caption>
                                    </Carousel.Item>
                                    <Carousel.Item interval={1000}>
                                        <img
                                            src={Cyprus}
                                            alt=""
                                        />
                                        <Carousel.Caption className={"carusel-third"}>
                                            <h3>{t('cyprus')}<br/>{t('moreToDiscover')}</h3>
                                            <p>
                                                <ul>
                                                    <li>
                                                        <TiWeatherSunny/> {t('goTravelNamespace1:impressiveBeachesAndCliffs')}
                                                    </li>
                                                    <li>
                                                        <TbBeach/> {t('idyllicAtmosphere')}
                                                    </li>
                                                </ul>
                                            </p>
                                        </Carousel.Caption>
                                    </Carousel.Item>
                                </Carousel>
                            </center>
                        </section>
                    </Container>
                    <section className='d-flex justify-content-center justify-content-lg-between p-1 '></section>

                    <Container>
                        <section>
                            <div className="index-header">
                                <center><h2 className="main">{t('recommendedDirections')}:</h2></center>
                                <hr className="main-line"></hr>

                                <div className="row">
                                    <div className="col-lg-4 col-md-6 trip_dir wlochy">
                                        <div className="country-image-container">
                                            <img src={Italy} alt={""}/>
                                            <div className="highlight_trip">
                                                <h3>{t('italy')}</h3>
                                                <span className="trip-price"> {t('priceFrom')} 1430 {t('perPerson')}</span>
                                                <div className="dark_cover">
                                                    <div className="info-container">
                                                        <div className="trip_desc">
                                                            {t('italyDescription')}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="col-lg-4 col-md-6 trip_dir tunezja">
                                        <div className="country-image-container">
                                            <img src={Tunisia} alt={""}/>
                                            <div className="highlight_trip">
                                                <h3>{t('tunisia')}</h3>
                                                <span className="trip-price"> {t('priceFrom')} 1734 {t('perPerson')}</span>
                                                <div className="dark_cover">
                                                    <div className="info-container">
                                                        <div className="trip_desc">
                                                            {t('tunisiaDescription')}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="col-lg-4 col-md-6 trip_dir hiszpania">
                                        <div className="country-image-container">
                                            <img src={Spain} alt={""}/>
                                            <div className="highlight_trip">
                                                <h3>{t('spain')}</h3>
                                                <span className="trip-price"> {t('priceFrom')} 2193 {t('perPerson')}</span>
                                                <div className="dark_cover">
                                                    <div className="info-container">
                                                        <div className="trip_desc">
                                                            {t('spainDescription')}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="col-lg-4 col-md-6 trip_dir grecja">
                                        <div className="country-image-container">
                                            <img src={Greece} alt={""}/>
                                            <div className="highlight_trip">
                                                <h3>{t('greece')}</h3>
                                                <span className="trip-price">  {t('priceFrom')} 1629 {t('perPerson')}</span>
                                                <div className="dark_cover">
                                                    <div className="info-container">
                                                        <div className="trip_desc">
                                                            {t('greeceDescription')}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="col-lg-4 col-md-6 trip_dir bulgaria">
                                        <div className="country-image-container">
                                            <img src={Bulgaria} alt={""}/>
                                            <div className="highlight_trip">
                                                <h3>{t('bulgaria')}</h3>
                                                <span className="trip-price">  {t('priceFrom')} 1562 {t('perPerson')}</span>
                                                <div className="dark_cover">
                                                    <div className="info-container">
                                                        <div className="trip_desc">
                                                            {t('bulgariaDescription')}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="col-lg-4 col-md-6 trip_dir chorwacja">
                                        <div className="country-image-container">
                                            <img src={Croatia} alt={""}/>
                                            <div className="highlight_trip">
                                                <h3>{t('croatia')}</h3>
                                                <span className="trip-price">  {t('priceFrom')} 1892 {t('perPerson')}</span>
                                                <div className="dark_cover">
                                                    <div className="info-container">
                                                        <div className="trip_desc">
                                                            {t('croatiaDescription')}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </section>
                    </Container>
                    <section className='d-flex justify-content-center justify-content-lg-between p-2 '></section>
                    <Container>
                        <section className="r-container r-container--gray trip-info">
                            <div className="r-wrapper">

                                <div className="trip-info-sections">
                                    <div className="trip-info-sect">
                                        <input type="checkbox" className="read-more-state" id="post-1" checked={isExpandedPost1} onChange={this.toggleExpandPost1} style={{ display: 'none' }}/>
                                        <h3 style={{color: "#4ec3ff"}}>{t('lastMinute')}</h3>
                                        <p className="read-more-wrap">
                                            {t('lastMinutePart1')}
                                            <span className="read-more-target">{t('lastMinutePart2')}</span>
                                        </p>
                                        <label htmlFor="post-1" className="read-more-trigger">
                                            {isExpandedPost1 ? t('readLess') : t('readMore')}
                                        </label>
                                    </div>

                                    <div className="trip-info-sect">
                                        <input type="checkbox" className="read-more-state" id="post-2" checked={isExpandedPost2} onChange={this.toggleExpandPost2} style={{ display: 'none' }}/>
                                        <h3 style={{color: "#4ec3ff"}}>{t('allInclusive')}</h3>
                                        <p className="read-more-wrap">
                                            {t('allInclusivePart1')}
                                            <span className="read-more-target">{t('allInclusivePart2')}</span>
                                        </p>
                                        <label htmlFor="post-2" className="read-more-trigger">
                                            {isExpandedPost2 ? t('readLess') : t('readMore')}
                                        </label>
                                    </div>

                                    <div className="trip-info-sect">
                                        <input type="checkbox" className="read-more-state" id="post-3" checked={isExpandedPost3} onChange={this.toggleExpandPost3} style={{ display: 'none' }}/>
                                        <h3 style={{color: "#4ec3ff"}}>{t('cruises')}</h3>
                                        <p className="read-more-wrap">
                                            {t('cruisesPart1')}
                                            <span className="read-more-target"> {t('cruisesPart2')}</span>
                                        </p>
                                        <label htmlFor="post-3" className="read-more-trigger">
                                            {isExpandedPost3 ? t('readLess') : t('readMore')}
                                        </label>
                                    </div>

                                </div>
                            </div>
                        </section>
                    </Container>
                </header>
                <Footer/>
            </main>
        )
    }
}

export default withTranslation()(HomeClient);