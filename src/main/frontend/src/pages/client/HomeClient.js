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

class HomeClient extends Component {

    state = {
        countries: [],
        transports: [],
        selectedTransport: '',
        selectedTransportId: 0,
        selectedCountry: '',
        selectedCountryId: 0,
        numberOfDaysMin: 0,
        numberOfDaysMax: 0,
        isExpandedPost1: false,
        isExpandedPost2: false,
        isExpandedPost3: false,
    }

    componentDidMount() {
        this.handleGetTransports()
        this.handleGetCountries()
    }

    handleGetTransports = () => {
        goTravelApi.getTransports().then(res => {
            this.setState({transports: res.data})
        })
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

    handleGetCountries = () => {
        goTravelApi.getCountries().then(res => {
            this.setState({countries: res.data})
        })
    }

    handleChangeCountry = (e) => {
        const {t} = this.props
        this.setState({selectedCountry: e.target.value})

        this.state.countries.forEach(country => {
            if (t('goTravelNamespace2:' + country.nameCountry) === e.target.value) {
                this.setState({ selectedCountryId: country.idCountry, selectedCountry: '' });
            } else if (e.target.value === '') {
                this.setState({ selectedCountryId: 0, selectedCountry: '' });
            }
        });
    }

    handleChangeTransport = (e) => {
        const {t} = this.props
        this.setState({selectedTransport: e.target.value})

        this.state.transports.forEach(tran => {
            if (t(tran.nameTransport) === e.target.value) {
                this.setState({selectedTransportId: tran.idTransport, selectedTransport: ''})
            } else if (e.target.value.trim() === "") {
                this.setState({selectedTransportId: 0, selectedTransport: ''})
            }
        })
    }

    handleChangeNumberOfDays = (e) => {
        const {t} = this.props
        if (e.target.value === t('whatever')) {
            this.setState({numberOfDaysMin: 1, numberOfDaysMax: 100})
        } else if (e.target.value === ("> 15 " + t('days'))) {
            this.setState({numberOfDaysMin: 15, numberOfDaysMax: 100})
        } else {
            const textArr = e.target.value.split(" - ")
            const textArr2 = textArr[1].split(" " + t('days'))
            this.setState({numberOfDaysMin: textArr[0], numberOfDaysMax: textArr2[0]})
        }
    }

    handleClickSearch = () => {
        const {selectedCountryId, selectedTransportId, numberOfDaysMin, numberOfDaysMax} = this.state

        const stateToSave = {
            selectedCountryId,
            selectedTransportId,
            numberOfDaysMin,
            numberOfDaysMax
        };

        localStorage.setItem('search', JSON.stringify(stateToSave));
        window.location.href = "/allOffers/search"
    }

    render() {
        const {t} = this.props
        const { isExpandedPost1, isExpandedPost2, isExpandedPost3  } = this.state;

        return (
            <main>
                <NavigationBar/>
                <header>
                    <section className="search-sec">
                        <div className="container">
                            <form action="src/main/frontend/src/pages/home/Home#" method="post" noValidate="novalidate">
                                <div className="row">
                                    <div className="col-lg-12">
                                        <div className="row">
                                            <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                <p className="search-desc">{t('whereDoYouWantToGo')}</p>
                                                <select className="form-control search-slt"
                                                        id="exampleFormControlSelect1"
                                                        name={"selectedCountry"} onChange={this.handleChangeCountry} style={{fontFamily: "Comic Sans MS"}}>
                                                    <option value={this.state.selectedCountry}>{t('wherever')}</option>
                                                    {this.state.countries.map(country =>
                                                        <option key={country.idCountry}>{t('goTravelNamespace2:' + country.nameCountry)}</option>
                                                    )}
                                                </select>
                                            </div>
                                            <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                <p className="search-desc">{t('howLong')}</p>
                                                <select className="form-control search-slt"
                                                        id="exampleFormControlSelect1"
                                                        onChange={this.handleChangeNumberOfDays} style={{fontFamily: "Comic Sans MS"}}>
                                                    <option>{t('whatever')}</option>
                                                    <option>1 - 5 {t('days')}</option>
                                                    <option>5 - 10 {t('days')}</option>
                                                    <option>10 - 15 {t('days')}</option>
                                                    <option> > 15 {t('days')}</option>
                                                </select>
                                            </div>
                                            <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                <p className="search-desc">{t('what')}</p>
                                                <select className="form-control search-slt"
                                                        id="exampleFormControlSelect1"
                                                        name={"selectedTransport"}
                                                        onChange={this.handleChangeTransport} style={{fontFamily: "Comic Sans MS"}}>

                                                    <option value={this.state.selectedTransport} >{t('anything')}</option>
                                                    {this.state.transports.map(transport =>
                                                        <option
                                                            key={transport.idTransport}>{t('goTravelNamespace1:' + transport.nameTransport)}</option>
                                                    )}
                                                </select>
                                            </div>
                                            <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                <button type="button" className="btn btn-danger wrn-btn"
                                                        onClick={this.handleClickSearch}>{t('search')}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
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