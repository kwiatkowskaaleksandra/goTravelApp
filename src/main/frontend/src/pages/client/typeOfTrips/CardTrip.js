import React, {Component} from "react";
import ListGroup from "react-bootstrap/ListGroup";
import {BsCalendarWeek} from "react-icons/bs";
import {MdOutlineFoodBank} from "react-icons/md";
import {FaRegPaperPlane} from "react-icons/fa";
import Card from 'react-bootstrap/Card'
import {withTranslation} from "react-i18next";
import AuthContext from "../../../others/AuthContext";
import {goTravelApi} from "../../../others/GoTravelApi";
import {handleLogError} from "../../../others/JWT";

class CardTrip extends Component {

    static contextType = AuthContext

    static defaultProps = {
        trips: [],
        mode: ''
    };

    state = {
        favorites: {},
        bubbles: {},
        userInfo: [],
        isLoggedIn: false
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({userInfo: user, isLoggedIn: true})
            this.getFavoritesTrips(user)

        }
    }

    getFavoritesTrips = (user) => {
        goTravelApi.getFavoritesTrips(user).then(res => {
            const favorites = {};
            res.data.forEach(trip => {
                favorites[trip.idTrip] = true
            })
            this.setState({favorites})
        }).catch(err => {
            handleLogError(err)
        })
    }

    addTripToFavorites = async (user, idTrip) => {
        const csrfResponse = await goTravelApi.csrf()
        const csrfToken = csrfResponse.data.token

        goTravelApi.addToFavorites(idTrip, user, csrfToken).then(() => {
            this.getFavoritesTrips(this.state.userInfo);
        }).catch(err => {
            handleLogError(err)
        })
    }

    removeTripFromFavorites = async (user, idTrip) => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.removeTripFromFavorites(idTrip, user, csrfToken).then(() => {
            this.getFavoritesTrips(this.state.userInfo);
            if (this.props.mode === 'favoriteTrips') {
                this.props.onUpdateTrips(idTrip);
            }
        }).catch(err => {
            handleLogError(err);
        });
    }


    toggleFavorite = async (idTrip) => {
        const isFavorite = this.state.favorites[idTrip];

        if (isFavorite) {
            await this.removeTripFromFavorites(this.state.userInfo, idTrip);
        } else {
            await this.addTripToFavorites(this.state.userInfo, idTrip);
        }

        const newBubbles = [];
        for (let i = 0; i < 10; i++) {
            const animationDuration = Math.random() + 0.5 + 's';
            const left = Math.random() * 100 - 50 + '%';
            const top = Math.random() * 100 - 50 + '%';
            newBubbles.push(<div className="bubble" style={{animationDuration, left, top}} key={i}></div>);
        }

        this.setState(prevState => ({
            bubbles: {
                ...prevState.bubbles,
                [idTrip]: newBubbles
            }
        }));

        setTimeout(() => {
            this.setState(prevState => ({
                bubbles: {
                    ...prevState.bubbles,
                    [idTrip]: []
                }
            }));
        }, 2000);
    };

    render() {
        const {t, trips} = this.props;

        return (
            <div className="p-2 w-100">
                <section>
                    <div>
                        {trips.map(tr =>
                            <Card key={tr.idTrip} on>

                                <div className={"d-flex justify-content-around"}>
                                    <div className={"container"}>
                                        {this.state.isLoggedIn &&
                                            <div className="favorite-button-container">
                                                <button className={`favorite-button ${this.state.favorites[tr.idTrip] ? 'is-favorite' : ''}`} onClick={() => this.toggleFavorite(tr.idTrip)}>
                                                    ♥
                                                </button>
                                                <div>
                                                    {this.state.bubbles[tr.idTrip] || []}
                                                </div>
                                            </div>
                                        }
                                        <div className={"d-flex row gx-5"}>

                                            <div className="col">
                                                <div className={"p-3 "} >
                                                    <Card.Img className={"justify-content-center"}
                                                                  style={{
                                                                  marginTop: '10px',
                                                                  marginLeft: '2%',
                                                                      height: '350px',
                                                                      objectFit:'cover'
                                                              }} variant="top"
                                                                  src={tr.representativePhoto}/>

                                                </div>
                                            </div>

                                            <div className="col">
                                                <div className="p-3">

                                                    <Card.Body>
                                                        <Card.Title className={"titleTrip"}>
                                                            {t(tr.tripCity.nameCity)}, {t(tr.tripCity.country.nameCountry)}
                                                        </Card.Title>
                                                    </Card.Body>

                                                    <ListGroup className="list-group-flush listTrip">
                                                        <ListGroup.Item> <BsCalendarWeek style={{
                                                            width: '15px',
                                                            height: '15px',
                                                            marginLeft: '5px'
                                                        }}/> {tr.numberOfDays} {t('nights')}</ListGroup.Item>

                                                        <ListGroup.Item> <MdOutlineFoodBank style={{
                                                            width: '25px',
                                                            height: '25px'
                                                        }}/> {t(tr.food)}</ListGroup.Item>

                                                        <ListGroup.Item> <FaRegPaperPlane style={{
                                                            width: '15px',
                                                            height: '15px',
                                                            marginLeft: '2px'
                                                        }}/> {t('goTravelNamespace1:transport')}: {t('goTravelNamespace1:'+tr.tripTransport.nameTransport)}
                                                        </ListGroup.Item>
                                                    </ListGroup></div>

                                                <Card.Body>
                                                    <Card.Text className={"priceTrip"}>
                                                        {t('price')}: {tr.price} {t('goTravelNamespace1:perPerson')}
                                                    </Card.Text>
                                                    <Card.Link
                                                        className={"seeTheOffer btn btn-primary btn-sm"}
                                                        href={tr.typeOfTrip.name + '/' + tr.idTrip}>{t('goTravelNamespace4:seeTheOffer')}</Card.Link>
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
        )
    }
}

export default withTranslation(['goTravelNamespace2', 'goTravelNamespace1'])(CardTrip);