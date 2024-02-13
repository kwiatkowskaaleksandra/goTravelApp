import React, {Component} from "react";
import ListGroup from "react-bootstrap/ListGroup";
import {BsCalendarWeek} from "react-icons/bs";
import {MdOutlineFoodBank} from "react-icons/md";
import {FaRegPaperPlane} from "react-icons/fa";
import Card from 'react-bootstrap/Card'
import {withTranslation} from "react-i18next";

class CardTrip extends Component {

    static defaultProps = {
        trips: []
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
                                        <div className={"d-flex row gx-5"}>

                                            <div className="col">
                                                <div className="p-3 ">
                                                    <Card.Img className={"justify-content-center"}
                                                                  style={{
                                                                  marginTop: '10px',
                                                                  marginLeft: '2%'
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
                                                        href={tr.typeOfTrip.name + '/' + tr.idTrip}>{t('seeTheOffer')}</Card.Link>
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