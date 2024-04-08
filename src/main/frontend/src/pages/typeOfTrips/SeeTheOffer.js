import React, {Component} from "react";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import {goTravelApi} from "../../others/GoTravelApi";
import {BsCalendarWeek, BsCheck2All, BsPeople} from "react-icons/bs";
import {MdOutlineFoodBank} from "react-icons/md";
import {FaRegPaperPlane, FaTrashAlt} from "react-icons/fa";
import {RiHotelLine} from "react-icons/ri";
import Carousel from "react-bootstrap/Carousel";
import AuthContext from "../../others/AuthContext";
import {withTranslation} from "react-i18next";
import {Card, Dropdown, Image, Tab, Tabs} from "react-bootstrap";
import Rating from "./Rating";
import ReactStars from "react-rating-stars-component";
import {Message} from "semantic-ui-react";
import {handleLogError} from "../../others/JWT";

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
        photos: [],
        attractions: [],
        key: 'generalInformation',
        user: null,
        sortType: '',
        opinions: [],
        averageOpinion: 0,
        numberOfOpinion: 0,
        description: '',
        stars: 0,
        starsKey: 0,
        isError: false,
        errorMessage: '',
        page: 1,
        size: 5,
        howManyPages: 1,
    }


    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({
                user: user
            })
        }
        this.state.idTripSelected = document.location.href.split("/").pop()
        this.handleGetTrip()
        this.countOpinions()
        this.getNumberOfOpinionAndCountStars()
        this.handleGetPhoto()
        this.handleGetAttractions()
        this.handleGetOpinion('ASC', this.state.page)
    }

    countOpinions = () => {
        goTravelApi.countOpinionsById(this.state.idTripSelected).then(res => {
            const howMany = Math.ceil(res.data / this.state.size)
            this.setState({howManyPages: howMany})
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

    getNumberOfOpinionAndCountStars = () => {
        goTravelApi.countOpinionsAndStars(this.state.idTripSelected).then((response) => {
            this.setState({
                numberOfOpinion: response.data.countNumberOfOpinion,
                averageOpinion: response.data.averageOpinionCalculated
            })
        }).catch(error => {
            handleLogError(error);
        });
    }

    handleGetOpinion = (sortType, page) => {
        const size = this.state.size
        let currentPage = page !== undefined ? page : 1;

        if (page < 1 || (currentPage - 1) <= 0) currentPage = 1
        else if (page > this.state.howManyPages) currentPage = this.state.howManyPages

        goTravelApi.getOpinionsByIdTrip(this.state.idTripSelected, sortType, currentPage - 1, size).then(response => {
            this.setState({
                sortType: sortType,
                opinions: response.data.content,
                page: currentPage
            })
        }).catch(error => {
            handleLogError(error);
        });
    }

    addOpinion = async () => {
        const {t} = this.props
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        const opinion = {
            description: this.state.description,
            stars: this.state.stars,
            trip: this.state.tripOffer
        }

        goTravelApi.addOpinion(this.state.user, csrfToken, opinion).then(() => {
            this.getNumberOfOpinionAndCountStars()
            this.countOpinions()
            this.handleGetOpinion(this.state.sortType, 1)
            this.setState({
                stars: 0,
                starsKey: this.state.starsKey + 1,
                description: '',
                isError: false,
                errorMessage: '',
                page: 1
            })
        }).catch(error => {
            handleLogError(error)
            this.setState({
                isError: true,
                errorMessage: t('goTravelNamespace3:'+error.response.data.message)
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

     handleDeleteOpinion = async (idOpinion) => {
         const csrfResponse = await goTravelApi.csrf();
         const csrfToken = csrfResponse.data.token;

         goTravelApi.deleteOpinion(this.state.user, csrfToken, idOpinion).then(() => {
             this.getNumberOfOpinionAndCountStars()
             this.countOpinions()
             this.handleGetOpinion(this.state.sortType, this.state.page)
         }).catch(error => {
             handleLogError(error)
         })
     }

    handleChangePage = (currentPage) => {
        this.setState({page: currentPage})
        this.handleGetOpinion(this.state.sortType, currentPage)
    }

    handleTabSelect = (selectedKey) => {
        this.setState({key: selectedKey});
    };

    render() {
        const {key, isError, errorMessage} = this.state
        const {t} = this.props
        const pages = Array.from({ length: this.state.howManyPages }, (_, index) => index + 1);

        return (
            <main>
                <NavigationBar/>

                <header className={"head"}>
                    <section className={"d-flex justify-content-center mb-4 ms-5 me-5"}>

                        <div className={"card"} style={{width: '1000px'}}>
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

                                <div className="d-flex justify-content-center w-75 mt-2">
                                    <div className="d-flex flex-column align-items-start">
                                        <Tabs
                                            id="controlled-tab"
                                            activeKey={key}
                                            onSelect={this.handleTabSelect}
                                            className="mb-3 flex-row"
                                            style={{width: '800px'}}
                                        >
                                            <Tab eventKey="generalInformation" title={t('goTravelNamespace2:generalInformation')}/>
                                            <Tab eventKey="opinions" title={t('goTravelNamespace2:opinions')}/>
                                            <Tab eventKey="attractions" title={t('goTravelNamespace2:attractions')}/>
                                        </Tabs>
                                        <div className={"ml-auto"}>
                                            {key === 'generalInformation' &&
                                                <div className={"d-flex flex-column align-items-start"} style={{width: '800px'}}>
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
                                                </div>
                                            }
                                            {key === 'opinions' &&
                                                <div className={"d-flex flex-column align-items-center"} style={{width: '800px'}}>
                                                    <div className="justify-content-center align-items-center" style={{display: 'block', textAlign: 'center'}}>
                                                        <div>
                                                            <p style={{fontSize: '30px'}}>{this.state.averageOpinion}/5</p>
                                                        </div>
                                                        <div style={{marginTop: '-3%'}}>
                                                            <Rating value={this.state.averageOpinion}/>
                                                        </div>
                                                        <div>
                                                            <p>{t('goTravelNamespace3:numberOfOpinions')}: {this.state.numberOfOpinion}</p>
                                                        </div>
                                                    </div>


                                                    <div>
                                                        <Dropdown className={"mb-2"} style={{marginLeft: '5%'}}>
                                                            <Dropdown.Toggle variant="success" id="dropdown-basic" className={"dropdownSort"}>
                                                                {this.state.sortType === 'DESC' ? t('goTravelNamespace3:sortFromNewest') : t('goTravelNamespace3:sortFromOldest')}
                                                            </Dropdown.Toggle>

                                                            <Dropdown.Menu style={{width: '200px'}}>
                                                                <Dropdown.Item onClick={() => this.handleGetOpinion('DESC', 1)}>{t('goTravelNamespace3:sortFromNewest')}</Dropdown.Item>
                                                                <Dropdown.Item onClick={() => this.handleGetOpinion('ASC', 1)}>{t('goTravelNamespace3:sortFromOldest')}</Dropdown.Item>
                                                            </Dropdown.Menu>
                                                        </Dropdown>
                                                    </div>

                                                    {this.state.user &&
                                                        <div className={"justify-content-center"} style={{width: '800px'}}>
                                                            <ul className="list-unstyled mx-auto">
                                                                <li className="bg-white mb-3">
                                                                    <div className="form-outline">
                                                                        <textarea className="form-control" id="textArea" rows="4" value={this.state.description} onChange={(e) => this.setState({description: e.target.value})}></textarea>
                                                                        <ReactStars key={this.state.starsKey}
                                                                                    count={5}
                                                                                    value={this.state.stars}
                                                                                    onChange={(e) => this.setState({stars: e})}
                                                                                    size={25}
                                                                                    isHalf={true}
                                                                                    emptyIcon={<i className="far fa-star"></i>}
                                                                                    halfIcon={<i className="fa fa-star-half-alt"></i>}
                                                                                    fullIcon={<i className="fa fa-star"></i>}
                                                                                    activeColor="gold"
                                                                        />
                                                                        {isError && <Message className={"messageErrorRegister"}>{errorMessage}</Message>}
                                                                    </div>
                                                                </li>
                                                                <button type="button" className="btn btn-primary float-end addOpinion mb-2" onClick={this.addOpinion}>{t('goTravelNamespace3:addOpinion')}</button>
                                                            </ul>
                                                        </div>
                                                    }

                                                    {this.state.opinions.map(opinion =>
                                                        <div className={"align-items-start"} key={opinion.id} style={{ marginBottom: '20px' }}>

                                                            <Card style={{width: '850px', display: 'block', textAlign: 'left'}}>
                                                                <Card.Header>
                                                                    <Image alt={""} roundedCircle
                                                                           src={"https://png.pngtree.com/png-vector/20190116/ourlarge/pngtree-vector-avatar-icon-png-image_322275.jpg"}
                                                                           width={30} height={30} style={{marginRight: '2%'}}/>
                                                                    {opinion.user.username}
                                                                </Card.Header>
                                                                <Card.Body>
                                                                    <blockquote className={"blockquote mb-0"}>
                                                                        <p style={{fontSize: '15px'}}>
                                                                            {opinion.description}
                                                                        </p>
                                                                        <Rating value={opinion.stars}/>
                                                                    </blockquote>
                                                                </Card.Body>
                                                                <Card.Footer style={{fontSize: '12px'}}>
                                                                    {opinion.dateOfAddingTheOpinion}

                                                                    {this.state.user !== null && opinion.user.username === this.state.user.data.sub && (
                                                                        <button style={{float: 'right', borderColor: '#f8f8f8', backgroundColor: '#f8f8f8'}} onClick={() => this.handleDeleteOpinion(opinion.idOpinion)}>
                                                                            <FaTrashAlt style={{color: '#4ec3ff', width: '20', height: '20'}}/>
                                                                        </button>
                                                                    )}
                                                                </Card.Footer>
                                                            </Card>
                                                        </div>
                                                    )}

                                                    <section className={"mt-5"} style={{placeSelf: 'end'}}>
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
                                                </div>
                                            }
                                            {key === 'attractions' &&
                                                <div className={"d-flex flex-column align-items-start"} style={{width: '800px'}}>
                                                    {this.state.attractions.map(attraction =>
                                                        <div key={attraction.idAttraction} className={"mt-1 ms-3"}>
                                                            <p className={"attraction"}><BsCheck2All/> {t('goTravelNamespace2:'+attraction.nameAttraction)}
                                                            </p>
                                                        </div>
                                                    )}
                                                </div>
                                            }
                                        </div>
                                    </div>
                                </div>
                            </center>
                        </div>
                    </section>
                </header>

                <Footer/>
            </main>

        );
    }
}


export default withTranslation(['goTravelNamespace2', 'goTravelNamespace1'])(SeeTheOffer);