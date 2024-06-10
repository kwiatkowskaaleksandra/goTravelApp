import React, {Component} from "react";
import AuthContext from "../../../others/AuthContext";
import NavigationBar from "../../home/NavigationBar";
import {withTranslation} from "react-i18next";
import Footer from "../../home/Footer";
import CardTrip from "../typeOfTrips/CardTrip";
import {goTravelApi} from "../../../others/GoTravelApi";
import {handleLogError} from "../../../others/JWT";
import {Alert} from "react-bootstrap";

class SettingsFavorites extends Component {

    static contextType = AuthContext

    state = {
        userInfo: null,
        trips: [],
        currentPage: 1,
        itemsPerPage: 3,
        noFavorites: false
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
            if (res.data.length === 0) {
                this.setState({noFavorites: true})
            }
            this.setState({trips: res.data})
        }).catch(err => {
            handleLogError(err)
        })
    }

    changePage = (pageNumber) => {
        const numberOfPages = Math.ceil(this.state.trips.length / this.state.itemsPerPage);
        if(pageNumber < 1 || pageNumber > numberOfPages) return;

        this.setState({ currentPage: pageNumber });
    };

    render() {
        const {t} = this.props
        const { trips, currentPage, itemsPerPage } = this.state;
        const indexOfLastItem = currentPage * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        const currentTrips = trips.slice(indexOfFirstItem, indexOfLastItem);
        const numberOfPages = Math.ceil(trips.length / itemsPerPage);
        const pages = Array.from({ length: numberOfPages }, (_, i) => i + 1);

        return (
            <div>
                <NavigationBar/>
                <header className={"head"}>
                    <section className={"d-flex justify-content-center mb-4 ms-5 me-5"}>
                        <div className="justify-content-around w-75">

                            <Alert show={this.state.noFavorites} variant="light" className={"recommendationAlert"} style={{marginBottom: '15%'}}>
                                <p>{t('goTravelNamespace3:noTripsAddedToFavorites')}</p>
                            </Alert>

                            <div className="p-2 w-90">
                                <CardTrip trips={currentTrips} mode={"favoriteTrips"} onUpdateTrips={() => this.getFavoritesTrips(this.state.userInfo)}/>
                                {!this.state.noFavorites &&
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
                                }
                            </div>
                        </div>
                    </section>
                </header>
                <Footer/>
            </div>
        )
    }
}

export default withTranslation()(SettingsFavorites)