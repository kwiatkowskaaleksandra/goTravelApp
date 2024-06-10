import React, {Component} from "react";
import {withTranslation} from "react-i18next";
import {goTravelApi} from "../../others/GoTravelApi";

class Search extends Component {

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
        return (
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
        )
    }
}

export default withTranslation()(Search)