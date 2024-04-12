import React, {Component} from "react";
import {Col, Modal, Row, InputGroup, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {withTranslation} from "react-i18next";
import AuthContext from "../../others/AuthContext";
import {goTravelApi} from "../../others/GoTravelApi";
import {handleLogError} from "../../others/JWT";
import {Message} from "semantic-ui-react";

class AddTripModal extends Component {

    static contextType = AuthContext

    state = {
        userInfo: [],
        countries: [],
        selectedCountryId: 0,
        cities: [],
        selectedCityId: 0,
        accommodations: [],
        selectedAccommodationId: 0,
        transports: [],
        selectedTransportId: 0,
        selectedFood: '',
        numberOfDays: 0,
        selectedTypeOfTripId: 0,
        activityLevel: 0.0,
        tripDescription: '',
        attractions: [],
        checkedAttractions: {},
        photoUrls: [{id: 0, url: ''}],
        nextPhotoId: 1,
        error: false,
        errorMessage: '',
        totalPrice: 0,
        dataToEditing: false,
        idTrip: 0,
        changesNotSaved: true
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()
        if (user != null) {
            this.setState({
                userInfo: user,
            })
        }

        this.handleGetAccommodations()
        this.handleGetCountries()
        this.handleGetTransports()
        this.handleGetAttractions()

        if (this.props.offerToEdit[0]) {
            this.setState({dataToEditing: true})
            this.loadDataForEditing(this.props.offerToEdit[0])
        }
    }

    loadDataForEditing = (editedOffer) => {
        const checkedAttractions = editedOffer.tripAttraction.reduce((acc, attraction) => {
            acc[attraction.idAttraction] = true;
            return acc;
        }, {});

        const photoUrls = editedOffer.tripPhoto.map(photo => ({
            id: photo.idPhoto,
            url: photo.urlPhoto
        }));

        this.setState({
            idTrip: editedOffer.idTrip,
            selectedFood: editedOffer.food,
            selectedCountryId: editedOffer.tripCity.country.idCountry,
            selectedCityId: editedOffer.tripCity.idCity,
            selectedAccommodationId: editedOffer.tripAccommodation.idAccommodation,
            selectedTransportId: editedOffer.tripTransport.idTransport,
            numberOfDays: editedOffer.numberOfDays,
            selectedTypeOfTripId: editedOffer.typeOfTrip.idTypeOfTrip,
            activityLevel: editedOffer.activityLevel,
            tripDescription: editedOffer.tripDescription,
            totalPrice: editedOffer.price,
            checkedAttractions: checkedAttractions,
            photoUrls: photoUrls,
            nextPhotoId: photoUrls.length ? Math.max(...photoUrls.map(photo => photo.id)) + 1 : 1,
        })
        goTravelApi.getCitiesByIdCountry(this.props.offerToEdit[0].tripCity.country.idCountry).then(response => {
            this.setState({cities: response.data, selectedCityId: this.props.offerToEdit[0].tripCity.idCity})
        })
    }

    handleGetAttractions = () => {goTravelApi.getAllAttractions().then(res => {this.setState({attractions: res.data})})}

    handleGetTransports = () => {goTravelApi.getTransports().then(res => {this.setState({transports: res.data})})}

    handleGetAccommodations = () => {goTravelApi.getAccommodations().then(res => {this.setState({accommodations: res.data})})}

    handleGetCountries = () => {goTravelApi.getCountries().then(res => {this.setState({countries: res.data})})}

    handleChangeCountry = (e) => {
        this.setState({
            selectedCountryId: e.target.value,
        })
        if (e.target.value === "0") {
            this.setState({
                cities: [],
                selectedCityId: "0"
            })
        }

        goTravelApi.getCitiesByIdCountry(e.target.value).then(response => {
            this.setState({cities: response.data, changesNotSaved: true})
        })
    }

    getCurrentDate() {
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        let month = currentDate.getMonth() + 1;
        if (month < 10) month = '0' + month;
        let day = currentDate.getDate();
        if (day < 10) day = '0' + day;
        return `${year}-${month}-${day}`;
    }

    handleCheckboxChangeAttractions = (e) => {
        const {id, checked} = e.target;
        this.setState(prevState => ({
            checkedAttractions: {
                ...prevState.checkedAttractions,
                [id]: checked
            },
            changesNotSaved: true
        }), this.checkIfAnyCheckboxCheckedAttractions);
    }

    checkIfAnyCheckboxCheckedAttractions = () => {
        const {checkedAttractions} = this.state;
        const anyCheckboxChecked = Object.values(checkedAttractions).some(checked => checked);
        if (!anyCheckboxChecked) {
            this.setState({checkedAttractions: {}})
        }
    }

    addPhotoUrl = () => {
        this.setState(prevState => ({
            photoUrls: [...prevState.photoUrls, { id: prevState.nextPhotoId, url: '' }],
            nextPhotoId: prevState.nextPhotoId + 1,
        }))
        this.setState({changesNotSaved: true})
    }

    removePhotoUrl = (id) => {
        this.setState(prevState => ({
            photoUrls: prevState.photoUrls.filter(photo => photo.id !== id)
        }));
        this.setState({changesNotSaved: true})
    }

    updatePhotoUrl = (id, url) => {
        this.setState(prevState => ({
            photoUrls: prevState.photoUrls.map(photo => photo.id === id ? { ...photo, url: url } : photo)
        }));
        this.setState({changesNotSaved: true})
    }

    saveTheOffer = async () => {
        if (this.state.totalPrice !== 0 && !this.state.dataToEditing && !this.state.changesNotSaved) {
            this.setState({ error: false, errorMessage: '' });

            const offerBody = this.createOfferBody();
            const csrfResponse = await goTravelApi.csrf();
            const csrfToken = csrfResponse.data.token;

            goTravelApi.saveNewTrip(offerBody, this.state.userInfo, csrfToken)
                .then(() => {
                    this.setState({changesNotSaved: false})
                    this.props.handleClose();
                    window.location.reload();
                })
                .catch(this.handleError);
        }
    }

    validate = async () => {
        this.setState({ error: false, errorMessage: '' , dataToEditing: false });

        const offerBody = this.createOfferBody();
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.validateTrip(offerBody, this.state.userInfo, csrfToken)
            .then(res => this.setState({ totalPrice: res.data ,  changesNotSaved: false}))
            .catch(this.handleError);
    }

    handleError = (err) => {
        handleLogError(err);
        if (err.response && err.response.data.status === 409) {
            this.setState({
                error: true,
                errorMessage: err.response.data.message
            });
        }
    }

    createOfferBody = () => {
        const { idTrip, totalPrice, selectedCityId, selectedTransportId, selectedAccommodationId, selectedFood, selectedTypeOfTripId, numberOfDays, tripDescription, activityLevel, checkedAttractions, photoUrls } = this.state;

        const checkedAttractionsName = Object.keys(checkedAttractions).filter(key => checkedAttractions[key]);

        return {
            idTrip: idTrip,
            price: totalPrice,
            tripCity: { idCity: selectedCityId },
            tripTransport: { idTransport: selectedTransportId },
            tripAccommodation: { idAccommodation: selectedAccommodationId },
            food: selectedFood,
            typeOfTrip: { idTypeOfTrip: selectedTypeOfTripId },
            numberOfDays: numberOfDays,
            tripDescription: tripDescription,
            activityLevel: activityLevel,
            tripAttraction: checkedAttractionsName.map(atr => ({ idAttraction: atr })),
            tripPhoto: photoUrls.map(photo => ({ urlPhoto: photo.url }))
        };
    }


    render() {
        const {t, allTypeOfTrips} = this.props;

        return (
            <Modal size={"xl"} show={this.props.show} onHide={this.props.handleClose}>
                <Modal.Header closeButton style={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <Modal.Title style={{fontFamily: 'Comic Sans MS', textAlign: 'center', margin: '0' }}>{t('goTravelNamespace4:addNewOffer')}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>

                        <Row className="mt-2">
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace4:typeOfTrip')}: </p>
                            </Col>
                            <Col>
                                <select style={{width: '300px', fontFamily: 'Comic Sans MS'}} className="form-control search-slt" id="exampleFormControlSelect1" name="selectedTypeOfTrip" onChange={(e) => this.setState({selectedTypeOfTripId: e.target.value, changesNotSaved: true})} value={this.state.selectedTypeOfTripId}>
                                    <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                    {allTypeOfTrips.map(typeOfTrip =>
                                        <option key={typeOfTrip.idTypeOfTrip} value={typeOfTrip.idTypeOfTrip}>{t('goTravelNamespace1:'+typeOfTrip.name)}</option>
                                    )}
                                </select>
                            </Col>
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace4:activityLevel')}: </p>
                            </Col>
                            <Col>
                                <input type="number" className="form-control" aria-label="activityLevel" placeholder="0" min="0.0" max={"10.0"} step={0.1}
                                       name={"activityLevel"} value={this.state.activityLevel}
                                       onChange={(e) => this.setState({activityLevel: e.target.value, changesNotSaved: true})}  style={{width: '300px', marginRight: '170px'}}/>
                            </Col>
                        </Row>

                        <Row className="mt-2">
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace1:country')}: </p>
                            </Col>
                            <Col>
                                <select style={{width: '300px', fontFamily: 'Comic Sans MS'}} className="form-control search-slt" id="exampleFormControlSelect1" name="selectedCountry" onChange={(e) => this.handleChangeCountry(e)} value={this.state.selectedCountryId}>
                                    <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                    {this.state.countries.map(country =>
                                        <option key={country.idCountry} value={country.idCountry}>{t('goTravelNamespace2:'+country.nameCountry)}</option>
                                    )}
                                </select>
                            </Col>
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace2:city')}: </p>
                            </Col>
                            <Col>
                                <select style={{width: '300px', fontFamily: 'Comic Sans MS', marginRight: '170px' }} className="form-control search-slt" id="exampleFormControlSelect2" name="selectedCity" onChange={(e) => this.setState({selectedCityId: e.target.value, changesNotSaved: true})} value={this.state.selectedCityId}>
                                    <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                    {this.state.cities.map(city =>
                                        <option key={city.idCity} value={city.idCity}>{t('goTravelNamespace2:'+city.nameCity)}</option>
                                    )}
                                </select>
                            </Col>
                        </Row>

                        <Row className="mt-2">
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace3:accommodation')}: </p>
                            </Col>
                            <Col>
                                <select style={{width: '300px', fontFamily: 'Comic Sans MS'}} className="form-control search-slt" id="exampleFormControlSelect1" name="selectedAccommodation" onChange={(e) => this.setState({selectedAccommodationId: e.target.value, changesNotSaved: true})} value={this.state.selectedAccommodationId}>
                                    <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                    {this.state.accommodations.map(accommodation =>
                                        <option key={accommodation.idAccommodation} value={accommodation.idAccommodation}>{t('goTravelNamespace2:'+accommodation.nameAccommodation)}</option>
                                    )}
                                </select>
                            </Col>
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace1:transport')}: </p>
                            </Col>
                            <Col>
                                <select style={{width: '300px', fontFamily: 'Comic Sans MS', marginRight: '170px' }} className="form-control search-slt" id="exampleFormControlSelect2" name="selectedTransport" onChange={(e) => this.setState({selectedTransportId: e.target.value, changesNotSaved: true})} value={this.state.selectedTransportId}>
                                    <option value={0}>{t('goTravelNamespace1:choose')}</option>
                                    {this.state.transports.map(transport =>
                                        <option key={transport.idTransport} value={transport.idTransport}>{t('goTravelNamespace1:'+transport.nameTransport)}</option>
                                    )}
                                </select>
                            </Col>
                        </Row>

                        <Row className="mt-2">
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace1:numberOfDays')}: </p>
                            </Col>
                            <Col>
                                <input type="number" className="form-control" aria-label="numberOfDays" placeholder="0" min="0" value={this.state.numberOfDays}
                                       name={"numberOfDays"} onChange={(e) => this.setState({numberOfDays: e.target.value, changesNotSaved: true})} style={{width: '300px'}}/>
                            </Col>
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace3:food')}: </p>
                            </Col>
                            <Col>
                                <select style={{width: '300px', fontFamily: 'Comic Sans MS', marginRight: '170px' }} className="form-control search-slt" id="exampleFormControlSelect2" name="selectedTransport" onChange={(e) => this.setState({selectedFood: e.target.value, changesNotSaved: true})} value={this.state.selectedFood}>
                                    <option value={""}>{t('goTravelNamespace1:choose')}</option>
                                    <option value={"none"}>{t('goTravelNamespace2:none')}</option>
                                    <option value={"breakfast"}>{t('goTravelNamespace2:breakfast')}</option>
                                    <option value={"dinner"}>{t('goTravelNamespace2:dinner')}</option>
                                    <option value={"supper"}>{t('goTravelNamespace2:supper')}</option>
                                    <option value={"breakfastDinner"}>{t('goTravelNamespace2:breakfastDinner')}</option>
                                    <option value={"breakfastSupper"}>{t('goTravelNamespace2:breakfastSupper')}</option>
                                    <option value={"dinnerSupper"}>{t('goTravelNamespace2:dinnerSupper')}</option>
                                    <option value={"breakfastDinnerSupper"}>{t('goTravelNamespace2:breakfastDinnerSupper')}</option>
                                </select>
                            </Col>
                        </Row>

                        <Row className="mt-2">
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace4:tripDescription')}: </p>
                            </Col>
                            <Col>
                                <textarea className="form-control" aria-label="tripDescription" placeholder={t('goTravelNamespace4:tripDescription')} value={this.state.tripDescription}
                                       name={"tripDescription"} rows="4"
                                       onChange={(e) => this.setState({tripDescription: e.target.value, changesNotSaved: true})}
                                       style={{width: '780px', height: '80px', marginRight: '170px'}}/>
                            </Col>
                        </Row>

                        <Row className="mt-2">
                            <Col style={{textAlign: 'end'}}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace2:attractions')}: </p>
                            </Col>
                            <Col>
                                {this.state.attractions.map((attraction, index) => (index % 3 === 0 &&
                                    <div className={"row mt-4"} key={index} style={{width: '800px', marginRight: '170px'}}>
                                        {this.state.attractions.slice(index, index + 3).map((attraction) => (
                                            <div className={"col-4 col-lg-4"} key={attraction.idAttraction} >
                                                <div className={"form-check"}>
                                                    <input className="form-check-input"
                                                           type="checkbox"
                                                           value={attraction.idAttraction}
                                                           id={attraction.idAttraction}
                                                           name={attraction.idAttraction}
                                                           checked={this.state.checkedAttractions[attraction.idAttraction]}
                                                           onChange={this.handleCheckboxChangeAttractions}/>
                                                    <label className="form-check-label" htmlFor={attraction.idAttraction}>{t('goTravelNamespace2:'+attraction.nameAttraction)}</label>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                ))}
                            </Col>
                        </Row>

                        {this.state.photoUrls.map((photo, index) => (
                            <Row className="mt-2" key={photo.id}>
                                <Col style={{ textAlign: 'end' }}>
                                    <p className="filterNameOwnTrip">{t('goTravelNamespace4:photosUrl')}: </p>
                                </Col>
                                <Col>
                                    <InputGroup style={{ width: '550px' }}>
                                        <Form.Control
                                            placeholder={'url'}
                                            aria-label={t('goTravelNamespace4:photosUrl')}
                                            value={photo.url}
                                            onChange={(e) => this.updatePhotoUrl(photo.id, e.target.value)}
                                        />
                                        {index === this.state.photoUrls.length - 1 && (
                                            <Button variant="outline-secondary" onClick={this.addPhotoUrl}>+</Button>
                                        )}
                                        {this.state.photoUrls.length > 1 && (
                                            <Button variant="outline-secondary" onClick={() => this.removePhotoUrl(photo.id)}>-</Button>
                                        )}
                                    </InputGroup>
                                </Col>
                                <Col>
                                    <div style={{ marginRight: '220px' }}>
                                        <img className="img-hover-zoom" style={{ width: '150px', height: '120px' }} src={photo.url || "https://via.placeholder.com/150"} alt={""} />
                                    </div>
                                </Col>
                            </Row>
                        ))}

                        <Row className="mt-2">
                            <Col style={{ textAlign: 'start' }}>
                                <p className="filterNameOwnTrip">{t('goTravelNamespace3:totalPrice')}: {this.state.totalPrice} {t('goTravelNamespace3:zloty')}</p>
                            </Col>
                        </Row>

                    </form>
                </Modal.Body>
                <Modal.Footer>
                    {this.state.error &&
                        <Message negative  className={"mt-3 messageError"} style={{fontFamily: "Comic Sans MS", width: '400px'}}>{t('goTravelNamespace4:'+this.state.errorMessage)}</Message>
                    }
                    <Button variant="secondary" onClick={this.props.handleClose} style={{fontFamily: 'Comic Sans MS'}}>
                        {t('goTravelNamespace3:close')}
                    </Button>
                    <Button className={"saveTheOffer"} onClick={this.validate} style={{fontFamily: 'Comic Sans MS'}}>
                        {t('goTravelNamespace4:validate')}
                    </Button>
                    {!this.state.changesNotSaved &&
                        <Button className={"saveTheOffer"} onClick={this.saveTheOffer} style={{fontFamily: 'Comic Sans MS'}}>
                            {t('goTravelNamespace4:saveTheOffer')}
                        </Button>
                    }
                </Modal.Footer>
            </Modal>
        )
    }
}

export default withTranslation()(AddTripModal)