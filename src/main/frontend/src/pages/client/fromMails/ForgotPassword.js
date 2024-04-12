import {withTranslation} from "react-i18next";
import logo from "../../../assets/image/logo.svg";
import {Button, Form, Modal} from "react-bootstrap";
import React, {Component} from "react";
import {FaUserAlt} from "react-icons/fa";
import {handleLogError} from "../../../others/JWT";
import {goTravelApi} from "../../../others/GoTravelApi";

class ForgotPassword extends Component {

    state = {
        email: '',
        showMessage: false,
    }

    handleInputChange = (event, {name, value}) => {
        this.setState({[name]: value})
    }

    handleSendEmail = (event) => {
        event.preventDefault()

        if (this.state.email !== '') {
            goTravelApi.forgotPassword(this.state.email).then(() => {
                this.setState({showMessage: true})
            }).catch(error => {
                handleLogError(error)
            })
        }
    }

    render() {
        const {t} = this.props
        const {showMessage} = this.state

        return (
            <div>
                <div className="bg-light">
                    <div className="container-fluid" style={{width: '1200px'}}>
                        <div className="row linkVerify" >
                            <div className="col-lg-10 offset-lg-1" style={{width: '900px', height: '980px'}}>
                                <div className="bg-white shadow rounded" style={{marginTop: '15%'}}>
                                    <div className="row">

                                        <div className="col-md-5 ps-0 d-none d-md-block">
                                            <div className="form-right h-100" >
                                                <img alt="" src={logo} width="300" height="300" className="img"/>
                                            </div>
                                        </div>

                                            <div className="col-md-7 pe-0">
                                                <p className={"text-center registerHeader"}>{t('goTravelNamespace3:doNotYouRememberThePassword')}</p>
                                                <div className="form-left h-100 py-5 px-5">
                                                    <Form onSubmit={this.handleSendEmail}>
                                                        <div className="col-12">
                                                            <div className="input-group">
                                                                <div className="input-group-text"><FaUserAlt
                                                                    style={{color: '#4ec3ff'}}/></div>
                                                                <input type="email" className="form-control"
                                                                       placeholder={t('goTravelNamespace3:enterYourEmailAddress')}
                                                                       name={'email'}
                                                                       onChange={(event) => this.handleInputChange(event, {
                                                                           name: event.target.name,
                                                                           value: event.target.value
                                                                       })}/>
                                                            </div>
                                                        </div>
                                                        <div className="d-grid mb-3 mt-3" style={{justifyItems: 'center'}}>
                                                            <button type="submit"
                                                                    className="btn btn-primary goTo"
                                                                    style={{
                                                                        height: '40px',
                                                                        width: '140px'
                                                                    }}>{t('goTravelNamespace3:continue')}</button>
                                                        </div>
                                                    </Form>
                                                    <Modal show={showMessage}>
                                                        <Modal.Header style={{
                                                            display: 'flex',
                                                            alignContent: 'center',
                                                            alignSelf: 'center'
                                                        }}>
                                                            <Modal.Title style={{
                                                                fontFamily: 'Comic Sans MS',
                                                                fontWeight: 'bold'
                                                            }}>{t('goTravelNamespace3:linkHasBeenSend')}</Modal.Title>
                                                        </Modal.Header>
                                                        <Modal.Body style={{fontFamily: 'Comic Sans MS'}}>
                                                            {t('goTravelNamespace3:linkHasBeenSendMessage')}
                                                        </Modal.Body>
                                                        <Modal.Footer style={{
                                                            display: 'flex',
                                                            alignContent: 'center'
                                                        }}>
                                                            <Button
                                                                variant="btn btn-primary px-4 float-end mt-4 loginButton"
                                                                style={{width: '150px'}} href={'/customerZone/login'}>
                                                                Ok
                                                            </Button>
                                                        </Modal.Footer>
                                                    </Modal>
                                                </div>
                                            </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default withTranslation()(ForgotPassword)