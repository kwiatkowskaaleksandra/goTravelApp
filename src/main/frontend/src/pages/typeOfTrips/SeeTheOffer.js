import React, {Component} from "react";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import {goTravelApi} from "../../others/GoTravelApi";
import {BsCalendarWeek, BsCheck2All, BsPeople, BsTrash3} from "react-icons/bs";
import {MdOutlineFoodBank} from "react-icons/md";
import {FaRegPaperPlane} from "react-icons/fa";
import {RiHotelLine} from "react-icons/ri";
import Carousel from "react-bootstrap/Carousel";
import AuthContext from "../../others/AuthContext";
import Button from "react-bootstrap/Button";
import axios from "axios";
import {withTranslation} from "react-i18next";


class SeeTheOffer extends Component {

    static contextType = AuthContext

    state = {
        idTripSelected: '',
        tripOffer: [],
        typeTrip: '',
        city: '',
        country: '',
        transport: '',
        accommodation: '',
        opinions: [],
        dateFormatted: '',
        photos: [],
        isUserLogin: false,
        userId: 0,
        attractions: [],
        opinionDesc: '',
        userInfo: [],
        userUsername: ''
    }


    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({isUserLogin: true, userId: user.id})
            goTravelApi.getUserInfo(user).then(res => {
                this.setState({
                    userInfo: res.data,
                    userId: res.data.id,
                    userUsername: res.data.username
                })
            })
        }
        this.state.idTripSelected = document.location.href.split("/").pop()
        this.handleGetTrip()
        this.handleGetOpinion()
        this.handleGetPhoto()
        this.handleGetAttractions()

    }


    handleGetOpinion = () => {
        goTravelApi.getOpinionsByIdTrip(this.state.idTripSelected).then(res => {
            this.setState({
                opinions: res.data,
                dateFormatted: new Date(res.data.date).toLocaleDateString("en-CA", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit"
                })
            })
        })
    }

    handlePostOpinion = () => {
        const opinion = {description: this.state.opinionDesc}

        goTravelApi.csrf().then(res => {
            axios.post("http://localhost:8080/api/opinions/addOpinion/"+this.state.userId+"/"+ this.state.idTripSelected, opinion, {
                withCredentials: true,
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': res.data.token
                }}).then(() => {
                this.handleGetOpinion()
                this.setState({opinionDesc: ''})
                window.location.reload()
            })
        })
    }

    handleGetPhoto = () => {
        goTravelApi.getPhotos(this.state.idTripSelected).then(res => {
            this.setState({
                photos: res.data
            })
        })
    }

    handleGetAttractions = () => {
        goTravelApi.getAttractions(this.state.idTripSelected).then(res => {
            this.setState({
                attractions: res.data
            })
        })
    }

    handleGetTrip = () => {
        const {t} = this.props
        goTravelApi.getTripById(this.state.idTripSelected, localStorage.getItem('selectedLang')).then(res => {
            this.setState({tripOffer: res.data})

            if (res.data.typeOfTrip.name === 'lastMinute') {
                this.setState({
                    typeTrip: t('lastMinute')
                })
            } else if (res.data.typeOfTrip.name === 'promotions') {
                this.setState({
                    typeTrip: t('promotions')
                })
            } else if (res.data.typeOfTrip.name === 'shortTrips') {
                this.setState({
                    typeTrip: t('shortTrips')
                })
            } else if (res.data.typeOfTrip.name === 'longTrips') {
                this.setState({
                    typeTrip: t('longTrips')
                })
            } else if (res.data.typeOfTrip.name === 'cruises') {
                this.setState({
                    typeTrip: t('cruises')
                })
            } else if (res.data.typeOfTrip.name === 'allInclusive') {
                this.setState({
                    typeTrip: t('allInclusive')
                })
            } else if (res.data.typeOfTrip.name === 'familyTrips') {
                this.setState({
                    typeTrip: t('familyTrips')
                })
            } else if (res.data.typeOfTrip.name === 'exotics') {
                this.setState({
                    typeTrip: t('exotics')
                })
            }

            this.setState({
                city: res.data.tripCity.nameCity,
                country: res.data.tripCity.country.nameCountry,
                transport: res.data.tripTransport.nameTransport,
                accommodation: res.data.tripAccommodation.nameAccommodation
            })
        })
    }

    handleReservation = () => {
        window.location.href = "/reservationForm/" + this.state.idTripSelected
    }

    handleGetDescription = (e) => {
        this.setState({opinionDesc: e.target.value})
    }

    handleDeleteOpinion = (id) => {

        goTravelApi.csrf().then(res => {
            axios.delete("http://localhost:8080/api/opinions/deleteOpinion/" + id, {
                    withCredentials: true,
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': res.data.token
                    }
                }
            ).then(() => {
                this.handleGetOpinion()
                window.location.reload()
            })
        })

    }

    render() {

        const tabs = document.querySelectorAll(".my-tabs .tabs li");
        const sections = document.querySelectorAll(".my-tabs .tab-content");


        tabs.forEach(tab => {
            tab.addEventListener("click", e => {
                e.preventDefault();
                removeActiveTab();
                addActiveTab(tab);
            });
        })

        const removeActiveTab = () => {
            tabs.forEach(tab => {
                tab.classList.remove("is-active");
            });
            sections.forEach(section => {
                section.classList.remove("is-active");
            });
        }

        const addActiveTab = tab => {
            tab.classList.add("is-active");
            const href = tab.querySelector("a").getAttribute("href");
            const matchingSection = document.querySelector(href);
            matchingSection.classList.add("is-active");
        }

        const {t} = this.props
        //this.props.
        return (
            <main>
                <NavigationBar/>

                <header className={"head"}>
                    <section className={"d-flex justify-content-center mb-4 ms-5 me-5"}>

                        <div className={"card"} style={{width: '80rem'}}>
                            <center>
                                <Carousel>
                                    {this.state.photos.map(photo =>
                                        <Carousel.Item interval={1000} key={photo.idPhoto}>
                                            <img
                                                className="d-block"
                                                src={photo.urlPhoto}
                                                alt=" "
                                                style={{width: '40rem'}}
                                            />
                                        </Carousel.Item>
                                    )}
                                </Carousel>
                            </center>

                            <div className="card-body">
                                <div className="my-tabs">
                                    <nav className="tabs">
                                        <ul>
                                            <li className="is-active" style={{fontFamily: "Comic Sans MS"}}><a href="#tab-one">{t('goTravelNamespace2:generalInformation')}</a></li>
                                            <li><a href="#tab-two" style={{fontFamily: "Comic Sans MS"}}>{t('goTravelNamespace2:opinions')}</a></li>
                                            <li><a href="#tab-three" style={{fontFamily: "Comic Sans MS"}}>{t('goTravelNamespace2:attractions')}</a></li>
                                        </ul>
                                    </nav>

                                    <section className="tab-content is-active" id="tab-one">

                                        <h2 className={"infoTrip"}>{t('goTravelNamespace1:'+this.state.typeTrip)} - {t('goTravelNamespace2:'+this.state.city)} - {t('goTravelNamespace2:'+this.state.country)}</h2>

                                        <p className={"mt-3 ms-3 price"}><BsPeople style={{
                                            width: '25px',
                                            height: '25px',
                                            marginLeft: '5px'
                                        }}/> {this.state.tripOffer.price} {t('goTravelNamespace1:perPerson')}</p>

                                        <p className={"mt-3 ms-3 info"}><BsCalendarWeek style={{
                                            width: '15px',
                                            height: '15px',
                                            marginLeft: '5px'
                                        }}/> {this.state.tripOffer.numberOfDays} {t('goTravelNamespace2:nights')} </p>

                                        <p className={"mt-3 ms-3 info"}><MdOutlineFoodBank style={{
                                            width: '25px',
                                            height: '25px'
                                        }}/> {t('goTravelNamespace2:'+this.state.tripOffer.food)} </p>

                                        <p className={"mt-3 ms-3 info"}><FaRegPaperPlane tyle={{
                                            width: '15px',
                                            height: '15px',
                                            marginLeft: '2px'
                                        }}/> {t('goTravelNamespace1:'+this.state.transport)}</p>

                                        <p className={"mt-3 ms-3 info"}><RiHotelLine tyle={{
                                            width: '15px',
                                            height: '15px',
                                            marginLeft: '2px'
                                        }}/> {t('goTravelNamespace2:'+this.state.accommodation)}</p>

                                        <p className={"mt-3 ms-3 desc"}> {this.state.tripOffer.tripDescription}</p>

                                        <button className="btn btn-primary reservation" type="submit"
                                                onClick={this.handleReservation}>{t('goTravelNamespace2:bookNow')}
                                        </button>

                                    </section>

                                    <section className="tab-content" id="tab-two">
                                        {this.state.opinions.map(opinion =>
                                            <div className="card mt-3" style={{width: '77rem'}} key={opinion.idOpinion}>
                                                <div className="card-body">
                                                    <h5 className="card-title">{opinion.user.username}</h5>
                                                    <h6 className="card-subtitle mb-2 text-muted">{new Date(opinion.date).toLocaleDateString("en-CA", {
                                                        year: "numeric",
                                                        month: "2-digit",
                                                        day: "2-digit"
                                                    })}</h6>
                                                    <p className="card-text">{opinion.description}</p>
                                                    {opinion.user.username === this.state.userUsername && (
                                                        <BsTrash3
                                                            onClick={() => this.handleDeleteOpinion(opinion.idOpinion)}/>
                                                    )}
                                                </div>
                                            </div>
                                        )}

                                        <div>

                                            {this.state.isUserLogin ? (
                                                <div className="input-group mt-3" style={{width: '77rem'}}>
                                                    <textarea className="form-control" aria-label="With textarea"
                                                              placeholder={t('goTravelNamespace2:myOpinion')+'...'}
                                                              onChange={this.handleGetDescription}></textarea>
                                                    <Button type="submit" class="btn btn-primary"
                                                            onClick={this.handlePostOpinion}>{t('goTravelNamespace2:addOpinions')}</Button>
                                                </div>
                                            ) : (
                                                <div></div>
                                            )
                                            }

                                        </div>


                                    </section>

                                    <section className="tab-content" id="tab-three">
                                        {this.state.attractions.map(attraction =>
                                            <div key={attraction.idAttraction} className={"mt-3 ms-3"}>
                                                <p className={"attraction"}><BsCheck2All/> {t('goTravelNamespace2:'+attraction.nameAttraction)}
                                                </p>
                                            </div>
                                        )}
                                    </section>

                                </div>
                            </div>
                        </div>


                    </section>
                </header>

                <Footer/>
            </main>

        );
    }
}


export default withTranslation(['goTravelNamespace2', 'goTravelNamespace1'])(SeeTheOffer);