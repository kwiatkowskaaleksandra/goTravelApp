import '../../others/NavigationBar.css'
import React, {Component} from 'react'
import {orderApi} from "../../others/OrderApi";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import './TypeOfTrips.css'
import {BsCalendarWeek} from 'react-icons/bs'
import {MdOutlineFoodBank} from 'react-icons/md'
import {FaRegPaperPlane} from 'react-icons/fa'
import Button from 'react-bootstrap/Button';

class SearchedTrips extends Component {

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

    }


    componentDidMount() {
        // const Auth = this.context
        //  const trip = Auth.getTrips()
      //  this.handleGetTransports()

        this.handleSetIds()
        this.handleGetTrips()
this.handleSetFilters()
    }

    handleSetIds = () => {
        const url = window.location.href
        const urlArr = url.split(/[\s/]+/)

        this.state.selectedCountryId = parseInt(urlArr[3])
        this.state.selectedTransportId = parseInt(urlArr[4])
        this.state.numberOfDaysMin = urlArr[5]
        this.state.numberOfDaysMax = urlArr[6]

    }

    handleSetFilters = () => {

        orderApi.getTransports().then(res => {
            this.setState({transports: res.data})
            res.data.map(tran => {
            if(this.state.selectedTransportId !== 0 && this.state.selectedTransport.trim() === ""){
                if(this.state.selectedTransportId === tran.idTransport){
                    this.setState({selectedTransport: tran.nameTransport})
                }
            }
        })
        })

        orderApi.getCountries().then(res => {
            this.setState({countries: res.data})

            res.data.map(country => {
                if(this.state.selectedCountryId !==0 && this.state.selectedCountry.trim() === ""){
                    if(this.state.selectedCountryId === country.idCountry){
                        this.setState({selectedCountry: country.nameCountry})
                    }
                }
            })
        })
    }

    handleGetTrips = () => {
        orderApi.getSearchedTrips(this.state.selectedCountryId, this.state.selectedTransportId, this.state.numberOfDaysMin, this.state.numberOfDaysMax).then(res => {
            this.setState({trips: res.data})
        })
    }


    handleSubmit = () => {
        orderApi.getTripsByFilters('lastMinute', this.state.selectedCountryId, this.state.selectedTransportId, this.state.priceMin, this.state.priceMax, this.state.numberOfDaysMin, this.state.numberOfDaysMax).then(res => {
            this.setState({trips: res.data})
        })
    }

    handleChangeTransport = (e) => {
        this.setState({selectedTransport: e.target.value})

        this.state.transports.map(tran => {
            if (tran.nameTransport === e.target.value) {
                this.setState({selectedTransportId: tran.idTransport, selectedTransport: ''})
            } else if (e.target.value.trim() === "") {
                this.setState({selectedTransportId: 0, selectedTransport: ''})
            }
        })
    }

    handleChangeCountry = (e) => {
        this.setState({selectedCountry: e.target.value})

        this.state.countries.map(country => {
            if (country.nameCountry === e.target.value) {
                this.setState({selectedCountryId: country.idCountry, selectedCountry: ''})
            } else if (e.target.value === '') {
                this.setState({selectedCountryId: 0, selectedCountry: ''})
            }
        })
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
        })
        window.location.href = "/searchedTrips/0/0/0/0"
    }

    render() {

        return (

            <main>
                <NavigationBar/>

                <header className={"head"}>
                    <section className={"d-flex justify-content-center mb-4 ms-5 me-5"}>
                        <div className="d-flex justify-content-around w-100 ">

                            <div className="p-2 w-25 ">
                                <div className={"mb-3"}>
                                    <p className={"filtrName"}>Transport: </p>
                                    <select className="form-control search-slt" id="exampleFormControlSelect1"
                                            name={"selectedTransport"} onChange={this.handleChangeTransport}>

                                        <option disabled selected value>{this.state.selectedTransport}</option>
                                        <option value={this.state.selectedTransport}>Wybierz</option>
                                        {this.state.transports.map(transport =>
                                            <option key={transport.idTransport}>{transport.nameTransport}</option>
                                        )}
                                    </select>
                                </div>

                                <div className="input-group mb-3">
                                    <p className={"filtrName"}>Ilosć dni: </p>
                                    <span className="input-group-text">Od:</span>
                                    <input type="number" className="form-control" aria-label="Cena od" placeholder="0"
                                           name={"numberOfDaysMin"} onChange={this.handleChangeNumberOfDaysMin} value={this.state.numberOfDaysMin}/>
                                    <span className="input-group-text">Do:</span>
                                    <input type="number" className="form-control" aria-label="Cena do" placeholder="0"
                                           name={"numberOfDaysMax"} onChange={this.handleChangeNumberOfDaysMax} value={this.state.numberOfDaysMax}/>
                                </div>

                                <div className={"mb-3"}>
                                    <p className={"filtrName"}>Kraj</p>
                                    <select className="form-control search-slt" id="exampleFormControlSelect1"
                                            name={"selectedCountry"} onChange={this.handleChangeCountry}>
                                        <option disabled selected value>{this.state.selectedCountry}</option>
                                        <option value={this.state.selectedCountry}>Wybierz</option>
                                        {this.state.countries.map(country =>
                                            <option key={country.idCountry}>{country.nameCountry}</option>
                                        )}
                                    </select>
                                </div>

                                <div className="input-group mb-3">
                                    <span className="input-group-text">Cena od:</span>
                                    <input type="number" className="form-control" aria-label="Cena od" placeholder="0"
                                           name={"priceMin"} onChange={this.handleChangePriceMin}/>
                                    <span className="input-group-text">Cena do:</span>
                                    <input type="number" className="form-control" aria-label="Cena do" placeholder="0"
                                           name={"priceMax"} onChange={this.handleChangePriceMax}/>
                                </div>

                                <Button type="submit" class="btn btn-primary"
                                        onClick={this.handleSubmit}>Filtruj</Button>
                                <Button type="submit" class="btn btn-primary" style={{float: 'right'}}
                                        onClick={this.handleClear}>Wyczyść filtry</Button>
                            </div>

                            <div className="p-2 w-75">
                                <section>
                                    <div>
                                        {this.state.trips.map(tr =>
                                            <Card key={tr.idTrip}  on>

                                                <div className={"d-flex justify-content-around"}>
                                                    <div className={"container"}>
                                                        <div className={"d-flex row gx-5"}>

                                                            <div className="col">
                                                                <div className="p-3 ">
                                                                    <Card.Img className={"justify-content-center"}
                                                                              style={{
                                                                                  marginTop: '10px',
                                                                                  marginLeft: '2%'
                                                                              }} variant="top" src={tr.representativePhoto}/>

                                                                </div>
                                                            </div>

                                                            <div className="col">
                                                                <div className="p-3">

                                                                    <Card.Body>
                                                                        <Card.Title className={"titleTrip"}>
                                                                            {tr.tripCity.nameCity}, {tr.tripCity.country.nameCountry}
                                                                        </Card.Title>
                                                                    </Card.Body>

                                                                    <ListGroup className="list-group-flush listTrip">
                                                                        <ListGroup.Item> <BsCalendarWeek style={{
                                                                            width: '15px',
                                                                            height: '15px',
                                                                            marginLeft: '5px'
                                                                        }}/> {tr.numberOfDays} noclegów</ListGroup.Item>

                                                                        <ListGroup.Item> <MdOutlineFoodBank style={{
                                                                            width: '25px',
                                                                            height: '25px'
                                                                        }}/> {tr.food}</ListGroup.Item>

                                                                        <ListGroup.Item> <FaRegPaperPlane style={{
                                                                            width: '15px',
                                                                            height: '15px',
                                                                            marginLeft: '2px'
                                                                        }}/> transport: {tr.tripTransport.nameTransport}
                                                                        </ListGroup.Item>
                                                                    </ListGroup></div>

                                                                <Card.Body>
                                                                    <Card.Text className={"priceTrip"}>
                                                                        Cena: {tr.price} zł
                                                                    </Card.Text>

                                                                    <Card.Link
                                                                        className={"seeTheOffer btn btn-primary btn-sm"}
                                                                        href={"lastMinute/"+tr.idTrip}>Zobacz ofertę</Card.Link>
                                                                </Card.Body>

                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </Card>
                                        )}
                                    </div>
                                </section>
                            </div>
                        </div>
                    </section>
                </header>
                <Footer/>
            </main>
        );
    }
}

export default SearchedTrips;