import React, {Component} from "react";
import NavigationBar from "../home/NavigationBar";
import Footer from "../home/Footer";
import {withTranslation} from "react-i18next";
import { TbLayoutGridAdd } from "react-icons/tb";
import AuthContext from "../../others/AuthContext";
import {goTravelApi} from "../../others/GoTravelApi";
import Card from "react-bootstrap/Card";
import ListGroup from "react-bootstrap/ListGroup";
import {Col, Row, Form, Modal} from "react-bootstrap";
import './trips.css'
import {FaRegPaperPlane} from "react-icons/fa";
import {BsCalendarWeek} from "react-icons/bs";
import {MdOutlineFoodBank} from "react-icons/md";
import { HiOutlineCurrencyDollar } from "react-icons/hi2";
import { IoHomeOutline } from "react-icons/io5";
import { FaEdit } from "react-icons/fa";
import { IoTrashBinSharp } from "react-icons/io5";
import Button from "react-bootstrap/Button";
import AddTripModal from "./AddTripModal";

class TourOffersManagement extends Component {

    static contextType = AuthContext

    state = {
        trips: [],
        userInfo: [],
        currentPage: 1,
        itemsPerPage: 8,
        typeOfTrip: 'all',
        howManyPages: 1,
        allTypeOfTrips: [],
        showAddModal: false,
        showDeleteModal: false,
        selectedOfferId: 0
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({userInfo: user})
            this.countTrips(this.state.typeOfTrip)
            this.getAllTypeOfTrips()
        }
    }

    getAllTypeOfTrips = () => {
        goTravelApi.getAllTypeOfTrips().then(res => {
            this.setState({allTypeOfTrips: res.data})
        })
    }

    countTrips = (typeOfTrip) => {
        goTravelApi.countTrips(typeOfTrip).then(response => {
            const howMany = Math.ceil(response.data / this.state.itemsPerPage);
            this.setState({howManyPages: howMany, currentPage: 1});
            this.getTrips(1)
        });
    }

    getTrips = (page) => {
        let currentPage = page !== undefined ? page : 1;
        if (page < 1) currentPage = 1
        else if (page > this.state.howManyPages) currentPage = this.state.howManyPages
        if (currentPage - 1 < 0) currentPage = 1
        goTravelApi.getTrips(this.state.typeOfTrip, currentPage - 1, this.state.itemsPerPage).then(res => {
            this.setState({
                trips: res.data.content,
            })
        }).catch(error  => {
            console.log(error)
        })
    }

    changePage = (currentPage) => {
        if (currentPage > 0 && currentPage <= this.state.howManyPages) {
            this.setState({currentPage: currentPage})
            this.getTrips(currentPage)
        }
    };

    deleteTheOffer = async () => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.deleteTheOffer(this.state.selectedOfferId, this.state.userInfo, csrfToken).then(() => {
            this.setState({selectedOfferId: 0})
            window.location.reload()
        }).catch(error => {
            console.log(error)
        })
    }

    render() {
        const {t} = this.props
        const pages = Array.from({ length: this.state.howManyPages }, (_, index) => index + 1);

        return (
            <main>
                <NavigationBar/>
                <section className='d-flex justify-content-center justify-content-lg-between p-2  mt-4'></section>
                <header className={"head"}>
                    <section className={"d-flex justify-content-center"}>
                        <div style={{width: '80%'}} className="d-flex align-items-centern">
                            <TbLayoutGridAdd className={"my-card"} style={{color: '#4ec3ff', width: '35px', height: '35px'}} onClick={() => this.setState({showAddModal: true})}/>
                            <Form.Select aria-label="Default select example"
                                         style={{width: '200px', marginLeft: '20px', fontFamily: 'Comic Sans MS'}}
                                         onChange={(event) => {
                                             const selectedTypeOfTrip = event.target.value;
                                             this.setState({ typeOfTrip: selectedTypeOfTrip }, () => {
                                                 this.countTrips(selectedTypeOfTrip);
                                             });
                                         }}
                            >
                                <option value="all">{t('goTravelNamespace4:allTypeOfTrips')}</option>
                                {this.state.allTypeOfTrips.map((type => (
                                    <option key={type.id} value={type.name}>{t('goTravelNamespace1:' + type.name)}</option>
                                )))}
                            </Form.Select>
                        </div>
                    </section>
                    <section className={"d-flex justify-content-center"}>
                        <div style={{width: '90%', marginTop: '10px'}}>
                            <Row className="justify-content-center">
                                {this.state.trips.length < 1 &&
                                <div style={{height: '150px'}}></div>}
                                {this.state.trips.map((trip) => (
                                    <Col key={trip.idTrip} md={3} className="mb-4">
                                        <Card style={{ marginBottom: '30px' }} className="tripCard">
                                            <Card.Img variant="top" style={{ height: '200px', objectFit: 'cover' }} src={trip.representativePhoto} />
                                            <Card.Body>
                                                <Card.Title style={{height: '30px'}} className={"tripInfo"}>{t('goTravelNamespace1:' + trip.typeOfTrip.name)} - {t(trip.tripCity.country.nameCountry)} - {t(trip.tripCity.nameCity)}</Card.Title>
                                            </Card.Body>
                                            <ListGroup className="list-group-flush tripInfo">
                                                <ListGroup.Item> <FaRegPaperPlane style={{
                                                    width: '15px',
                                                    height: '15px',
                                                    marginRight: '4px'
                                                }}/>{t('goTravelNamespace1:transport')}: {t('goTravelNamespace1:' + trip.tripTransport.nameTransport)}</ListGroup.Item>
                                                <ListGroup.Item style={{height: '60px'}}> <IoHomeOutline  style={{
                                                    width: '15px',
                                                    height: '15px',
                                                    marginRight: '4px'
                                                }}/>{t('goTravelNamespace3:accommodation')}: {t('goTravelNamespace2:' + trip.tripAccommodation.nameAccommodation)}</ListGroup.Item>
                                                <ListGroup.Item><BsCalendarWeek style={{
                                                    width: '15px',
                                                    height: '15px',
                                                    marginLeft: '5px'
                                                }}/> {trip.numberOfDays} {t('nights')}</ListGroup.Item>
                                                <ListGroup.Item><MdOutlineFoodBank style={{
                                                    width: '25px',
                                                    height: '25px'
                                                }}/> {t(trip.food)}</ListGroup.Item>
                                                <ListGroup.Item><HiOutlineCurrencyDollar style={{
                                                        width: '25px',
                                                        height: '25px'
                                                    }}/>{t('price')}: {t(trip.price)} {t('goTravelNamespace1:perPerson')}</ListGroup.Item>
                                            </ListGroup>
                                            <Card.Body>
                                                <div className="d-flex justify-content-center">
                                                    <Button className="mr-2 tripBtn" onClick={() => {this.setState({showAddModal: true, selectedOfferId: trip.idTrip})}}>
                                                        <FaEdit style={{
                                                            width: '25px',
                                                            height: '25px'
                                                        }}/>
                                                    </Button>
                                                    <Button className={"tripBtn"} onClick={() => {this.setState({showDeleteModal: true, selectedOfferId: trip.idTrip})}}>
                                                        <IoTrashBinSharp style={{
                                                            width: '25px',
                                                            height: '25px'
                                                        }}/>
                                                    </Button>
                                                </div>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                ))}
                            </Row>
                        </div>
                    </section>
                    <section className={"mt-5"} style={{marginRight: '20px'}}>
                        <nav aria-label="Page navigation example">
                            <ul className="pagination justify-content-end">
                                <li className="page-item">
                                    <button className="page-link" onClick={() => this.changePage(this.state.currentPage - 1)} aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </button>
                                </li>
                                {pages.map(pageNumber => (
                                    <li className={`page-item ${this.state.currentPage === pageNumber ? 'active' : ''}`} key={pageNumber}>
                                        <button className="page-link" onClick={() => this.changePage(pageNumber)}>
                                            {pageNumber}
                                        </button>
                                    </li>
                                ))}
                                <li className="page-item">
                                    <button className="page-link" onClick={() => this.changePage(this.state.currentPage + 1)} aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </button>
                                </li>
                            </ul>
                        </nav>
                    </section>
                </header>
                <Footer/>
                {this.state.showAddModal && (
                    <div className="modal-container">
                        <AddTripModal allTypeOfTrips = {this.state.allTypeOfTrips} offerToEdit = {this.state.trips.filter(trip => trip.idTrip === this.state.selectedOfferId)} show={this.state.showAddModal} handleClose={() => this.setState({ showAddModal: false, selectedOfferId: 0})} />
                    </div>
                )}

                {this.state.showDeleteModal && (
                    <Modal show={this.state.showDeleteModal} onHide={() => this.setState({showDeleteModal: false})} size={"lg"}>
                        <Modal.Header closeButton>
                            <Modal.Title id="example-modal-sizes-title-xl" style={{fontFamily: 'Comic Sans MS'}}>
                                {t('goTravelNamespace4:deleteTheOffer')}
                            </Modal.Title>
                        </Modal.Header>
                        <Modal.Body style={{fontFamily: 'Comic Sans MS'}}>
                            <div>
                                <p>{t('goTravelNamespace4:confirmDeleteTheOffer')}</p>
                                {this.state.trips
                                    .filter(trip => trip.idTrip === this.state.selectedOfferId)
                                    .map(filteredTrip => (
                                        <div key={filteredTrip.idTrip}>
                                            <p>{t('goTravelNamespace1:' + filteredTrip.typeOfTrip.name)} - {t(filteredTrip.tripCity.country.nameCountry)} - {t(filteredTrip.tripCity.nameCity)}</p>
                                        </div>
                                    ))}
                            </div>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" style={{fontFamily: 'Comic Sans MS'}} onClick={() => this.setState({ showDeleteModal: false })}>{t('goTravelNamespace3:close')}</Button>
                            <Button variant="primary" style={{fontFamily: 'Comic Sans MS'}} onClick={this.deleteTheOffer}>{t('goTravelNamespace3:confirm')}</Button>
                        </Modal.Footer>
                    </Modal>
                )}

            </main>
        )
    }
}

export default withTranslation(['goTravelNamespace2', 'goTravelNamespace1'])(TourOffersManagement)