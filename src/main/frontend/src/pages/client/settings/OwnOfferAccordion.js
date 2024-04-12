import {Accordion, Col, Row} from "react-bootstrap";
import React from "react";
import StripeButton from "../StripeButton";

const OwnOfferAccordion = ({t, ownOffers, generateInvoice, cancelReservationModal, user, mode, userType, changeAcceptStatus}) => {
    return (
        <Accordion style={{marginBottom: '15%', width: '1000px'}}>
            {ownOffers.map(ownOffer => (
                <Accordion.Item eventKey = {ownOffer.idOwnOffer} >
                    <Accordion.Header>
                        <Row className="w-100">
                            <Col xs={6}>
                                <div>{t('goTravelNamespace2:' + ownOffer.offerCity.country.nameCountry)} - {t('goTravelNamespace2:' + ownOffer.offerCity.nameCity)}</div>
                            </Col>
                            <Col xs={6} className="text-end">
                                <div>{t('goTravelNamespace3:dateOfReservation')}</div>
                                <div>{ownOffer.dateOfReservation}</div>
                            </Col>
                        </Row>
                    </Accordion.Header>
                    <Accordion.Body>
                        <Row className="w-100">
                            <Col xs={6}>
                                <div>{t('goTravelNamespace3:totalPrice')}: {ownOffer.totalPrice} {t('goTravelNamespace3:zloty')}</div>
                                <div className={"mt-2"}>{t('goTravelNamespace3:departureDate')}: {ownOffer.departureDate}</div>
                                <div className={"mt-2"}>{t('goTravelNamespace3:numberOfAdults')}: {ownOffer.numberOfAdults}</div>
                                {userType === 'client' &&
                                <div className={"mt-2"} style={{ color: ownOffer.accepted ? 'green' : 'red'}}>{t('goTravelNamespace4:acceptedStatus')}: {
                                    ownOffer.accepted ? t('goTravelNamespace4:reservationAccepted') : (ownOffer.changedAcceptanceState ? t('goTravelNamespace4:reservationRejected') : t('goTravelNamespace4:pendingApproval'))
                                }
                                </div>
                                }
                            </Col>
                            <Col xs={6}>
                                <div style={{ color: ownOffer.payment ? 'green' : 'red' }}>
                                    <span style={{ color: 'black' }}>{t('goTravelNamespace3:paymentStatus')}: </span>
                                    <span style={{fontWeight: 'bold'}}>{ownOffer.payment ? t('goTravelNamespace3:paid') : t('goTravelNamespace3:isNotPaid')}</span>
                                </div>
                                <div className={"mt-2"}>{t('goTravelNamespace1:numberOfDays')}: {ownOffer.numberOfDays}</div>
                                <div className={"mt-2"}>{t('goTravelNamespace3:numberOfChildren')}: {ownOffer.numberOfChildren}</div>
                            </Col>
                        </Row>
                        <Row className={"w-100"} style={{display: 'flex'}}>
                            <Col xs={12}>
                                {userType === 'client' &&
                                <div style={{ display: 'flex', justifyContent: 'center' }}>
                                    {mode === 'active' && <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => cancelReservationModal(ownOffer.idOwnOffer)}>{t('goTravelNamespace3:cancelYourReservation')}</button> }
                                    {ownOffer.payment && ownOffer.accepted && <button className="btn btn-primary activeOffers" type="submit" onClick={(e) => generateInvoice(ownOffer.idOwnOffer)}>{t('goTravelNamespace3:downloadTheInvoice')}</button>}
                                    {!ownOffer.payment && ownOffer.accepted && <StripeButton price={ownOffer.totalPrice} email={user.data.sub} user={user} type={"ownOffer"} id={ownOffer.idOwnOffer} style={{marginTop: '40px'}}/>}
                                </div>
                                }
                                {userType === 'employee' &&
                                <div style={{ display: 'flex', justifyContent: 'center' }}>
                                    <button className="btn btn-primary activeOffers" type="submit" onClick={() => changeAcceptStatus(ownOffer.idOwnOffer, 'accept', 'ownOffer')}>{t('goTravelNamespace4:accept')}</button>
                                    <button className="btn btn-primary activeOffers" type="submit" onClick={() => changeAcceptStatus(ownOffer.idOwnOffer, 'reject', 'ownOffer')}>{t('goTravelNamespace4:reject')}</button>
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

export default OwnOfferAccordion;