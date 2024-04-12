import {Accordion, Col, Row} from "react-bootstrap";
import React from "react";
import StripeButton from "../StripeButton";

const ReservationAccordion = ({t, reservations, cancelReservationModal, generateInvoice, user, mode, userType, changeAcceptStatus}) => {
    return (
        <Accordion style={{marginBottom: '10%', width: '1000px'}}>
            {reservations.map(res => (
                <Accordion.Item eventKey = {res.idReservation} >
                    <Accordion.Header>
                        <Row className="w-100">
                            <Col xs={6}>
                                <div>{t('goTravelNamespace1:' + res.trip.typeOfTrip.name)}</div>
                                <div>{t('goTravelNamespace2:' + res.trip.tripCity.country.nameCountry)} - {t('goTravelNamespace2:' + res.trip.tripCity.nameCity)}</div>
                            </Col>
                            <Col xs={6} className="text-end">
                                <div>{t('goTravelNamespace3:dateOfReservation')}</div>
                                <div>{res.dateOfReservation}</div>
                            </Col>
                        </Row>
                    </Accordion.Header>
                    <Accordion.Body>
                        <Row className="w-100">
                            <Col xs={6}>
                                <div>{t('goTravelNamespace3:totalPrice')}: {res.totalPrice} {t('goTravelNamespace3:zloty')}</div>
                                <div className={"mt-2"}>{t('goTravelNamespace3:departureDate')}: {res.departureDate}</div>
                                <div className={"mt-2"}>{t('goTravelNamespace3:numberOfAdults')}: {res.numberOfAdults}</div>
                                {userType === 'client' && <div className={"mt-2"} style={{ color: res.accepted ? 'green' : 'red' }}>{t('goTravelNamespace4:acceptedStatus')}: {
                                    res.accepted ? t('goTravelNamespace4:reservationAccepted') : (res.changedAcceptanceState ? t('goTravelNamespace4:reservationRejected') : t('goTravelNamespace4:pendingApproval'))
                                }
                                </div>
                                }
                            </Col>
                            <Col xs={6}>
                                <div style={{ color: res.payment ? 'green' : 'red' }}>
                                    <span style={{ color: 'black' }}>{t('goTravelNamespace3:paymentStatus')}: </span>
                                    <span style={{fontWeight: 'bold'}}>{res.payment ? t('goTravelNamespace3:paid') : t('goTravelNamespace3:isNotPaid')}</span>
                                </div>
                                <div className={"mt-2"}>{t('goTravelNamespace1:numberOfDays')}: {res.trip.numberOfDays}</div>
                                <div className={"mt-2"}>{t('goTravelNamespace3:numberOfChildren')}: {res.numberOfChildren}</div>
                            </Col>
                        </Row>
                        <Row className={"w-100"} style={{display: 'flex'}}>
                            <Col xs={12}>
                                {userType === 'client' &&
                                <div style={{ display: 'flex', justifyContent: 'center' }}>
                                    {mode === 'active' && <button className="btn btn-primary activeOffers" type="submit" onClick={() => cancelReservationModal(res.idReservation)}>{t('goTravelNamespace3:cancelYourReservation')}</button>}
                                    {res.payment && res.accepted && <button className="btn btn-primary activeOffers" type="submit" onClick={() => generateInvoice(res.idReservation)}>{t('goTravelNamespace3:downloadTheInvoice')}</button>}
                                    {!res.payment && res.accepted && mode === 'active' && <StripeButton price={res.totalPrice} email={user.data.sub} user={user} type={"reservations"} id={res.idReservation} style={{marginTop: '40px'}}/>}
                                </div>
                                }
                                {userType === 'employee' &&
                                <div style={{ display: 'flex', justifyContent: 'center' }}>
                                      <button className="btn btn-primary activeOffers" type="submit" onClick={() => changeAcceptStatus(res.idReservation, 'accept', 'reservations')}>{t('goTravelNamespace4:accept')}</button>
                                      <button className="btn btn-primary activeOffers" type="submit" onClick={() => changeAcceptStatus(res.idReservation, 'reject', 'reservations')}>{t('goTravelNamespace4:reject')}</button>
                                </div>
                            }
                            </Col>
                        </Row>
                    </Accordion.Body>
                </Accordion.Item>
            ))}
        </Accordion>
    )
}

export default ReservationAccordion;