import '../../others/NavigationBar.css'
import React, {Component} from 'react'
import {goTravelApi} from "../../others/GoTravelApi";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import CardTrip from './CardTrip';
import './TypeOfTrips.css'
import Filtration from "./Filtration";
import {useLocation} from "react-router-dom";
import {withTranslation} from "react-i18next";
import { GiArchiveResearch } from "react-icons/gi";

class AllOffers extends Component {

    state = {
        trips: [],
        transports: [],
        countries: [],

        selectedCountry: '',
        selectedCountryId: 0,
        selectedTransport: '',
        selectedTransportId: 0,
        priceMin: 0,
        priceMax: 0,
        numberOfDaysMin: 0,
        numberOfDaysMax: 100000,
        page: 1,
        size: 3,
        howManyPages: 1,
        typeOfTrips: '',
        filters: false,
        message: '',
        error: false
    }

    componentDidMount() {
        this.loadDataBasedOnLocation(this.props.location.pathname);

        if (localStorage.getItem('search') !== null) {
            const savedState = JSON.parse(localStorage.getItem('search'));
            const type = this.props.location.pathname.split('/allOffers/').pop();

            this.setState({
                typeOfTrips: type,
                selectedCountryId: savedState.selectedCountryId,
                selectedTransportId: savedState.selectedTransportId,
                numberOfDaysMin: savedState.numberOfDaysMin,
                numberOfDaysMax: savedState.numberOfDaysMax
            }, () => {
                this.handleSubmit(this.state.page)
            })
        }
    }

    componentDidUpdate(prevProps) {
        if (prevProps.location.pathname !== this.props.location.pathname) {
            this.setState({page: 1})
            this.loadDataBasedOnLocation(this.props.location.pathname);
        }
    }

    loadDataBasedOnLocation(pathname) {
        const type = pathname.split('/allOffers/').pop();
        this.setState({typeOfTrips: type}, () => {
            this.handleGetTrips(this.state.page);
            this.handleGetTransports()
            this.handleGetCountries()
            goTravelApi.countTrips(type).then(res => {
                const howMany = Math.ceil(res.data / this.state.size);
                this.setState({howManyPages: howMany});
            });
        });
    }

    handleGetTrips = (page) => {
        const size = this.state.size
        let currentPage = page !== undefined ? page : 1;

        if (page < 1) currentPage = 1
        else if (page > this.state.howManyPages) currentPage = this.state.howManyPages

        goTravelApi.getTrips(this.state.typeOfTrips, currentPage - 1, size).then(res => {
            this.setState({
                trips: res.data.content,
                page: currentPage
            })

        }).catch(error => {
            console.error('Error fetching trips:', error);
        });
    }

    handleChangePage = (currentPage) => {
        this.setState({page: currentPage})
        if (this.state.filters) {
            this.handleSubmit(currentPage)
        } else this.handleGetTrips(currentPage)
    }

    handleGetTransports = () => {
        goTravelApi.getTransports().then(res => {
            this.setState({transports: res.data})
        })
    }

    handleGetCountries = () => {
        goTravelApi.getCountries().then(res => {
            this.setState({countries: res.data})
        })
    }

    handleSubmit = (page) => {
        this.setState({filters: true})
        const {t} = this.props

        let currentPage = typeof page === 'number' && page >= 1 ? page : 1;

        if (page < 1) currentPage = 1
        else if (page > this.state.howManyPages) currentPage = this.state.howManyPages

        const {typeOfTrips, selectedCountryId, selectedTransportId, priceMin, priceMax, numberOfDaysMin, numberOfDaysMax, size} = this.state

        const filters = {
            typeOfTrip: typeOfTrips,
            idCountry: selectedCountryId,
            typeOfTransport: selectedTransportId,
            minPrice: priceMin,
            maxPrice: priceMax,
            minDays: numberOfDaysMin,
            maxDays: numberOfDaysMax,
            page: currentPage,
            size: size
        }

        goTravelApi.getTripsByFilters(filters).then(res => {

            this.setState({
                page: currentPage,
                trips: res.data.content,
                howManyPages: res.data.totalPages
            });

            if (res.data.content.length === 0) {
                this.setState({message: t('goTravelNamespace2:filtrationError'), error: true})
            } else {
                this.setState({message: '', error: false})
            }

        }).catch(error => {
            console.error('Error:', error);
        });
    }

    handleChangeTransport = (e) => {
        const {t} = this.props
        const selectedTransportName = e.target.value;
        let selectedTransportId = 0;

        this.setState({ selectedTransport: selectedTransportName });

        this.state.transports.forEach(tran => {
            if (t(tran.nameTransport) === selectedTransportName) {
                selectedTransportId = tran.idTransport;
            }
        });

        this.setState({ selectedTransportId: selectedTransportId });
    }

    handleChangeCountry = (e) => {
        const {t} = this.props
        const selectedCountryName = e.target.value;
        let selectedCountryId = 0;

        this.setState({ selectedCountry: selectedCountryName });

        this.state.countries.forEach(country => {
            if (t('goTravelNamespace2:' + country.nameCountry) === selectedCountryName) {
                selectedCountryId = country.idCountry;
            }
        });

        this.setState({ selectedCountryId: selectedCountryId });
    }

    handleChangeNumberOfDaysMin = (e) => {
        this.setState({numberOfDaysMin: e.target.value})
    }

    handleChangeNumberOfDaysMax = (e) => {
        this.setState({numberOfDaysMax: e.target.value})
    }

    handleChangePriceMin = (e) => {
        this.setState({priceMin: e.target.value})
    }

    handleChangePriceMax = (e) => {
        this.setState({priceMax: e.target.value})
    }

    handleClear = () => {
        this.setState({
            selectedCountry: '',
            selectedCountryId: 0,
            selectedTransport: '',
            selectedTransportId: 0,
            priceMin: 0,
            priceMax: 0,
            numberOfDaysMin: 0,
            numberOfDaysMax: 0,
            filters: false,
            error: false,
            message: '',
            page: 1
        })
        localStorage.removeItem('search')
        window.location.href = "/allOffers/" + this.state.typeOfTrips
    }

    render() {
        const pages = Array.from({ length: this.state.howManyPages }, (_, index) => index + 1);
        return (
            <main>
                <NavigationBar/>

                <header className={"head"}>
                    <section className={"d-flex justify-content-center mb-4 ms-5 me-5"}>
                        <div className="d-flex justify-content-around w-100 ">

                            <Filtration handleChangeTransport={this.handleChangeTransport}
                                        handleChangeNumberOfDaysMin={this.handleChangeNumberOfDaysMin}
                                        handleChangeNumberOfDaysMax={this.handleChangeNumberOfDaysMax}
                                        handleChangePriceMin={this.handleChangePriceMin}
                                        handleChangePriceMax={this.handleChangePriceMax}
                                        handleChangeCountry={this.handleChangeCountry}
                                        handleSubmit={this.handleSubmit}
                                        handleClear={this.handleClear}
                            />
                            <div className="p-2 w-75">
                                <CardTrip trips={this.state.trips} mode={"allTrips"}/>
                                {this.state.error ? (
                                    <div className="message">
                                        <GiArchiveResearch style={{ width: '180px', height: '180px', color: '#4ec3ff' }} />
                                        <span style={{ marginLeft: '20px' }}>{this.state.message}</span>
                                    </div>
                                ) : (
                                <section className={"mt-5"}>
                                    <nav aria-label="Page navigation example">
                                        <ul className="pagination justify-content-end">
                                            <li className="page-item">
                                                <button className="page-link" onClick={() => this.handleChangePage(this.state.page - 1)} aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </button>
                                            </li>
                                            {pages.map(pageNumber => (
                                                <li className={`page-item ${this.state.page === pageNumber ? 'active' : ''}`} key={pageNumber}>
                                                    <button className="page-link" onClick={() => this.handleChangePage(pageNumber)}>
                                                        {pageNumber}
                                                    </button>
                                                </li>
                                            ))}
                                            <li className="page-item">
                                                <button className="page-link" onClick={() => this.handleChangePage(this.state.page + 1)} aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </button>
                                            </li>
                                        </ul>
                                    </nav>
                                </section>
                            )}
                            </div>

                        </div>
                    </section>
                </header>
                <Footer/>
            </main>
        );
    }
}

function withLocation(Component) {
    return function WrappedComponent(props) {
        const location = useLocation();

        return <Component {...props} location={location} />;
    };
}

export default withTranslation()(withLocation(AllOffers));