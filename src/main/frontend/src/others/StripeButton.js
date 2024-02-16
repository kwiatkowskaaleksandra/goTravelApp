import axios from "axios";
import StripeCheckout from 'react-stripe-checkout'
import {t} from "i18next";
import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import React, {useState} from "react";
import { TbFaceIdError } from "react-icons/tb";
import { TbFaceId } from "react-icons/tb";
import {goTravelApi} from "./GoTravelApi";

const StripeButton = ({price, email, user, type, id}) => {
    const publicKey = 'pk_test_51OjQutLhNMYMxIBY18SVEedcok3ZYV9FSo4u6fGQmide0oewqhhPtTAvg0EEhpB7n57gxRVEuns0zUig0hh1zvJ700I2d0dMIf'
    const stripePrice = price * 100

    const [showModal, setShowModal] = useState(false);
    const [message, setMessage] = useState('');

    const onToken = (token) => {
        axios.post('http://localhost:8080/payment', {amount: stripePrice, token,}, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${user.accessToken}`
            }}).then(() => {
                updatePaymentStatus().then(() => {
                    setShowModal(true);
                    setMessage('paymentAccepted')
                })
        }).catch(() => {
            setShowModal(true);
            setMessage('paymentDeclined')
        })
    }

    const updatePaymentStatus = async () => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.updatePaymentStatus(user, csrfToken, type, id)
    }

    const handleCloseModal = () => {
        if (message === 'paymentAccepted') {
            window.location.href = "/myProfile/invoices"
        }
        setShowModal(false);
    }

    return (
        <div>
            <StripeCheckout
                style={{height: '40px', fontFamily:'Comic Sans MS', backgroundColor: 'red', border: 'none'}}
                amount={stripePrice}
                label={t('goTravelNamespace3:payNow')}
                name={"GoTravel Travel Agency"}
                image={"https://svgshare.com/i/CUz.svg"}
                panelLabel={t('goTravelNamespace3:payNow')}
                token={onToken}
                stripeKey={publicKey}
                currency={"PLN"}
                email={email}
            />
            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title style={{ fontFamily: "Comic Sans MS" }}>{t('goTravelNamespace3:paymentConfirmation')}</Modal.Title>
                </Modal.Header>
                <Modal.Body style={{ fontFamily: "Comic Sans MS" }}>
                    {message === 'paymentAccepted' ? (
                        <TbFaceId style={{ width: '100px', height: '100px', color: '#52d113' }} />
                    ) : (
                        <TbFaceIdError style={{ width: '100px', height: '100px', color: '#a91212' }} />
                    )}
                    {t('goTravelNamespace3:' + message)}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal} style={{ fontFamily: "Comic Sans MS" }}>
                        {t('goTravelNamespace3:close')}
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}
export default StripeButton