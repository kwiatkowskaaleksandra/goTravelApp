import React, {Component} from "react";
import Button from "react-bootstrap/Button";
import {goTravelApi} from "../../../others/GoTravelApi";
import {withTranslation} from "react-i18next";

class Filtration extends Component {

    state = {
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
    }

    componentDidMount() {
        this.handleGetTransports()
        this.handleGetCountries()
        if (localStorage.getItem('search') !== null) {
            const savedState = JSON.parse(localStorage.getItem('search'));
            this.setState({
                numberOfDaysMin: savedState.numberOfDaysMin,
                numberOfDaysMax: savedState.numberOfDaysMax,
                selectedTransportId: savedState.selectedTransportId,
                selectedCountryId: savedState.selectedCountryId,
            })
        }
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

    render() {
        const {t} = this.props
        return (
            <div className="p-2 w-25 ">
                <div className={"mb-3"}>
                    <p className={"filterName"}>{t('transport')}: </p>
                    <select className="form-control search-slt" id="exampleFormControlSelect1"
                            name={"selectedTransport"} onChange={this.props.handleChangeTransport}>

                        <option value={this.state.selectedTransport}>{t('choose')}</option>
                        {this.state.transports.map(transport =>
                            <option key={transport.idTransport}>{t(transport.nameTransport)}</option>
                        )}
                    </select>
                </div>

                <div className="input-group mb-3">
                    <p className={"filterName"}>{t('numberOfDays')}: </p>
                    <span className="input-group-text" style={{width: '2.5rem', height: "3rem"}}>{t('from')}:</span>
                    <input type="number" className="form-control" aria-label="Cena od" placeholder="0" min="0"
                           name={"numberOfDaysMin"} onChange={this.props.handleChangeNumberOfDaysMin} style={{width: '3rem',  height: "3rem"}}/>
                    <span className="input-group-text" style={{width: '3rem',  height: "3rem"}}>{t('to')}:</span>
                    <input type="number" className="form-control" aria-label="Cena do" placeholder="0" min="0"
                           name={"numberOfDaysMax"} onChange={this.props.handleChangeNumberOfDaysMax} style={{width: '3rem',  height: "3rem"}}/>
                </div>

                <div className={"mb-3"}>
                    <p className={"filterName"}>{t('country')}:</p>
                    <select className="form-control search-slt" id="exampleFormControlSelect1"
                            name={"selectedCountry"} onChange={this.props.handleChangeCountry}>
                        <option value={this.state.selectedCountry}>{t('choose')}</option>
                        {this.state.countries.map(country =>
                            <option key={country.idCountry}>{t('goTravelNamespace2:' + country.nameCountry)}</option>
                        )}
                    </select>
                </div>

                <div className="input-group mb-3">
                    <span className="input-group-text" style={{width: "80px"}}>{t('tripPriceFrom')}:</span>
                    <input type="number" className="form-control" aria-label="Cena od" placeholder="0" min="0"
                           name={"priceMin"} onChange={this.props.handleChangePriceMin}/>
                    <span className="input-group-text" style={{width: "80px"}}>{t('tripPriceTo')}:</span>
                    <input type="number" className="form-control" aria-label="Cena do" placeholder="0" min="0"
                           name={"priceMax"} onChange={this.props.handleChangePriceMax}/>
                </div>

                <Button type="submit" className="btn btn-primary filterBtn"
                        onClick={this.props.handleSubmit}>{t('filter')}</Button>
                <Button type="submit" className="btn btn-primary filterBtn" style={{float: 'right'}}
                        onClick={this.props.handleClear}>{t('clearFilters')}</Button>
            </div>
        )
    }
}

export default withTranslation()(Filtration)