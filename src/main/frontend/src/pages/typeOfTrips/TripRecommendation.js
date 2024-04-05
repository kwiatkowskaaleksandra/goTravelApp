import React, {Component} from "react";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import {withTranslation} from "react-i18next";
import AuthContext from "../../others/AuthContext";
import {goTravelApi} from "../../others/GoTravelApi";
import {handleLogError} from "../../others/JWT";
import {Alert, Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import "./TypeOfTrips.css"
import CardTrip from "./CardTrip";
import { MdSettingsSuggest } from "react-icons/md";
import {Message} from "semantic-ui-react";
import {
    mapOptionToActivityLevel, mapOptionToDuration, mapOptionToFood, mapOptionToPriceLevel, mapOptionToTripType,
    setPreferencesActivityLevel, setPreferencesDuration,
    setPreferencesFood,
    setPreferencesPriceLevel,
    setPreferencesTripType
} from "./PreferencesUtils";

class TripRecommendation extends Component {

    static contextType = AuthContext

    state = {
        trips: [],
        userInfo: [],
        preferences: [],
        activityLevelSelectedOption: '',
        durationSelectedOption: '',
        foodSelectedOption: '',
        priceLevelSelectedOption: '',
        tripTypeSelectedOption: '',
        noPreferences: false,
        currentPage: 1,
        itemsPerPage: 3,
        showRecommendationSettings: false,
        errorMessage: '',
        error: false,
        message: ''
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({userInfo: user})
            goTravelApi.getUserTripPreferences(user).then(res => {
                this.updatePreferences(res.data)
            }).catch(error => {
                handleLogError(error)
                const errorData = error.response.data
                if (errorData.status === 404) {
                    console.log(errorData.message)
                    this.getMostBookedTrips()
                    this.setState({
                        noPreferences: true,
                        message: 'noPreferencesSet'
                    })
                }
            })
        }
    }

    getMostBookedTrips = () => {
        goTravelApi.getMostBookedTrips().then(res => {
            this.setState({
                trips: res.data,
                noPreferences: true,
                message: 'noMatches'
            })
        }).catch(err => {
            handleLogError(err)
        })
    }

    updatePreferences = (data) => {
        this.setState({
            preferences: data,
        });
        this.getTripRecommendation(data).catch(error => {
            console.error("Error getting trip recommendation:", error);
        });
        setPreferencesActivityLevel(data.activityLevel, this.setState.bind(this));
        setPreferencesDuration(data.duration, this.setState.bind(this));
        setPreferencesPriceLevel(data.priceLevel, this.setState.bind(this));
        setPreferencesTripType(data.tripType, this.setState.bind(this));
        setPreferencesFood(data.food, this.setState.bind(this));
    }


    changePage = (pageNumber) => {
        const numberOfPages = Math.ceil(this.state.trips.length / this.state.itemsPerPage);
        if(pageNumber < 1 || pageNumber > numberOfPages) return;

        this.setState({ currentPage: pageNumber });
    };

    getTripRecommendation = async (preferences) => {
        const csrfResponse = await goTravelApi.csrf()
        const csrfToken = csrfResponse.data.token

        goTravelApi.getRecommendation(this.state.userInfo, preferences, csrfToken).then(res => {
            if (res.data.length === 0) {
                this.getMostBookedTrips()
            } else this.setState({trips: res.data})
        })
    }

    savePreferences = async () => {
        this.setState({
            error: false,
            errorMessage: ''
        })
        const {t} = this.props;
        const activityLvl = mapOptionToActivityLevel(this.state.activityLevelSelectedOption)
        const priceLvl = mapOptionToPriceLevel(this.state.priceLevelSelectedOption)
        const duration = mapOptionToDuration(this.state.durationSelectedOption)
        const tripType = mapOptionToTripType(this.state.tripTypeSelectedOption)
        const food = mapOptionToFood(this.state.foodSelectedOption)

        const preferences = {
            activityLevel: activityLvl,
            priceLevel: priceLvl,
            duration: duration,
            tripType: tripType,
            food: food
        }

        const csrfResponse = await goTravelApi.csrf()
        const csrfToken = csrfResponse.data.token

        goTravelApi.saveUserPreferences(this.state.userInfo, preferences, csrfToken).then(res => {
            this.updatePreferences(res.data)
            window.location.reload()
        }).catch(err => {
            handleLogError(err)
            if (err.response.data.status === 409) {
                this.setState({
                    error: true,
                    errorMessage: t('goTravelNamespace4:'+err.response.data.message)
                })
            }
        })
    }

    closeModalWithoutSaving = () => {
        this.setState({
            showRecommendationSettings: false
        });

        if (this.state.preferences.length !== 0) {
            setPreferencesActivityLevel(this.state.preferences.activityLevel, this.setState.bind(this))
            setPreferencesDuration(this.state.preferences.duration, this.setState.bind(this));
            setPreferencesPriceLevel(this.state.preferences.priceLevel, this.setState.bind(this));
            setPreferencesTripType(this.state.preferences.tripType, this.setState.bind(this));
            setPreferencesFood(this.state.preferences.food, this.setState.bind(this));
        }
    }

    render() {
        const {t} = this.props
        const { trips, currentPage, itemsPerPage } = this.state;
        const indexOfLastItem = currentPage * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        const currentTrips = trips.slice(indexOfFirstItem, indexOfLastItem);
        const numberOfPages = Math.ceil(trips.length / itemsPerPage);
        const pages = Array.from({ length: numberOfPages }, (_, i) => i + 1);

        return (
            <main>
                <NavigationBar/>
                <header className={"head"}>
                    <section className={"d-flex justify-content-center mb-4 ms-5 me-5"}>
                        <div className="justify-content-around w-75">
                            <Alert show={this.state.noPreferences} variant="light" className={"recommendationAlert"}>
                                <p>{t('goTravelNamespace4:' + this.state.message)}</p>
                                <hr />
                                <div className="d-flex justify-content-end">
                                    <Button variant="outline-success" className={"recommendationAlert"} onClick={() => this.setState({showRecommendationSettings: true})}>
                                        {t('goTravelNamespace3:addPreferences')}
                                    </Button>
                                </div>
                            </Alert>

                            <div className="p-2 w-90">
                                <div style={{textAlign: 'right'}}>
                                    <MdSettingsSuggest className={"recommendationSettings"} onClick={() => this.setState({showRecommendationSettings: true})}/>
                                </div>
                                <CardTrip trips={currentTrips}/>
                                <section className={"mt-5"}>
                                    <nav aria-label="Page navigation example">
                                        <ul className="pagination justify-content-end">
                                            <li className="page-item">
                                                <button className="page-link" onClick={() => this.changePage(currentPage - 1)} aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </button>
                                            </li>
                                            {pages.map(pageNumber => (
                                                <li className={`page-item ${currentPage === pageNumber ? 'active' : ''}`} key={pageNumber}>
                                                    <button className="page-link" onClick={() => this.changePage(pageNumber)}>
                                                        {pageNumber}
                                                    </button>
                                                </li>
                                            ))}
                                            <li className="page-item">
                                                <button className="page-link" onClick={() => this.changePage(currentPage + 1)} aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </button>
                                            </li>
                                        </ul>
                                    </nav>
                                </section>
                            </div>
                        </div>
                    </section>

                    <Modal
                        size="xl"
                        dialogClassName="modal-90w custom-modal-size"
                        show={this.state.showRecommendationSettings}
                        onHide={this.closeModalWithoutSaving}
                        aria-labelledby="example-custom-modal-styling-title"
                    >
                        <Modal.Header closeButton>
                            <Modal.Title id="example-modal-sizes-title-xl" style={{fontFamily: 'Comic Sans MS'}}>
                                {t('goTravelNamespace4:preferenceSettings')}
                            </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div>

                                <p className="question">1. {t('goTravelNamespace4:question1')}</p>
                                <div className="options" style={{textAlign: 'justify', marginBottom: '10px'}}>
                                    {[...Array(5)].map((_, index) => (
                                        <div className="form-check" style={{marginBottom: '10px'}} key={index}>
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="question1"
                                                id={`inline1Radio${index + 1}`}
                                                value={`option${index + 1}`}
                                                checked={this.state.activityLevelSelectedOption === `option${index + 1}`}
                                                onChange={() => this.setState({activityLevelSelectedOption: `option${index + 1}`})}
                                            />
                                            <label className="form-check-label" htmlFor={`inline1Radio${index + 1}`}>
                                                {t(`goTravelNamespace4:question1answer${index + 1}`)}
                                            </label>
                                        </div>
                                    ))}
                                </div>

                                <p className="question">2. {t('goTravelNamespace4:question2')}</p>
                                <div className="options" style={{textAlign: 'justify', marginBottom: '10px'}}>
                                    {[...Array(5)].map((_, index) => (
                                        <div className="form-check" style={{marginBottom: '10px'}} key={index}>
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="question2"
                                                id={`inline2Radio${index + 1}`}
                                                value={`option${index + 1}`}
                                                checked={this.state.durationSelectedOption === `option${index + 1}`}
                                                onChange={() => this.setState({durationSelectedOption: `option${index + 1}`})}
                                            />
                                            <label className="form-check-label" htmlFor={`inline2Radio${index + 1}`}>
                                                {t(`goTravelNamespace4:question2answer${index + 1}`)}
                                            </label>
                                        </div>
                                    ))}
                                </div>

                                <p className="question">3. {t('goTravelNamespace4:question3')}</p>
                                <div className="options" style={{textAlign: 'justify', marginBottom: '10px'}}>
                                    {[...Array(5)].map((_, index) => (
                                        <div className="form-check" style={{marginBottom: '10px'}} key={index}>
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="question3"
                                                id={`inline3Radio${index + 1}`}
                                                value={`option${index + 1}`}
                                                checked={this.state.priceLevelSelectedOption === `option${index + 1}`}
                                                onChange={() => this.setState({priceLevelSelectedOption: `option${index + 1}`})}
                                            />
                                            <label className="form-check-label" htmlFor={`inline3Radio${index + 1}`}>
                                                {t(`goTravelNamespace4:question3answer${index + 1}`)}
                                            </label>
                                        </div>
                                    ))}
                                </div>

                                <p className="question">4. {t('goTravelNamespace4:question4')}</p>
                                <div className="options" style={{textAlign: 'justify', marginBottom: '10px'}}>
                                    {[...Array(5)].map((_, index) => (
                                        <div className="form-check" style={{marginBottom: '10px'}} key={index}>
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="question4"
                                                id={`inline4Radio${index + 1}`}
                                                value={`option${index + 1}`}
                                                checked={this.state.tripTypeSelectedOption === `option${index + 1}`}
                                                onChange={() => this.setState({tripTypeSelectedOption: `option${index + 1}`})}
                                            />
                                            <label className="form-check-label" htmlFor={`inline4Radio${index + 1}`}>
                                                {t(`goTravelNamespace4:question4answer${index + 1}`)}
                                            </label>
                                        </div>
                                    ))}
                                </div>

                                <p className="question">5. {t('goTravelNamespace4:question5')}</p>
                                <div className="options" style={{textAlign: 'justify', marginBottom: '10px'}}>
                                    {[...Array(8)].map((_, index) => (
                                        <div className="form-check" style={{marginBottom: '10px'}} key={index}>
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="question5"
                                                id={`inline5Radio${index + 1}`}
                                                value={`option${index + 1}`}
                                                checked={this.state.foodSelectedOption === `option${index + 1}`}
                                                onChange={() => this.setState({foodSelectedOption: `option${index + 1}`})}
                                            />
                                            <label className="form-check-label" htmlFor={`inline5Radio${index + 1}`}>
                                                {t(`goTravelNamespace4:question5answer${index + 1}`)}
                                            </label>
                                        </div>
                                    ))}
                                </div>

                            </div>
                        </Modal.Body>
                        <Modal.Footer>
                            {this.state.error &&
                                <Message negative  className={"mt-3 messageError"} style={{fontFamily: "Comic Sans MS", width: '400px'}}>{this.state.errorMessage}</Message>
                            }
                            <Button variant="secondary" style={{fontFamily: 'Comic Sans MS'}} onClick={() => this.savePreferences()}>{t('goTravelNamespace2:saveChanges')}</Button>
                        </Modal.Footer>
                    </Modal>

                </header>
                <Footer/>
            </main>
        )
    }
}

export default withTranslation()(TripRecommendation);