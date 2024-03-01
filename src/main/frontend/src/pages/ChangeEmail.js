import React, {Component} from "react";
import {withTranslation} from "react-i18next";
import logo from "../assets/image/logo.svg";
import {Button, Form, Modal, Nav} from "react-bootstrap";
import {FaUserAlt} from "react-icons/fa";
import {Message} from "semantic-ui-react";
import {Link} from "react-router-dom";
import {handleLogError} from "../others/JWT";
import {goTravelApi} from "../others/GoTravelApi";

class ChangeEmail extends Component {

    state = {
        email: '',
        newEmail: '',
        showMessage: false,
        isError: false,
        errorMessage: '',
        expires: false
    }

    componentDidMount() {
        const urlSearchParams = new URLSearchParams(window.location.search);
        const codeParam = urlSearchParams.get('email');
        const expirationTimestamp = urlSearchParams.get('expires')

        if (expirationTimestamp) {
            const  currentTimestamp = Math.floor(Date.now() / 1000)
            if (currentTimestamp < parseInt(expirationTimestamp, 10)) {
                this.setState({
                    expires: true,
                    email: atob(codeParam)
                })
            }
        }
    }

    handleSendEmail = (event) => {
        event.preventDefault()
        const {t} = this.props

        goTravelApi.sendConfirmationNewEmail(this.state.email, this.state.newEmail).then(() => {
            this.setState({showMessage: true})
        }).catch(error => {
            handleLogError(error)
            if (error.response && error.response.data) {
                const errorData = error.response.data
                let errorMessage = t('goTravelNamespace3:fieldsHaveBeenFilledInIncorrectly')
                if (errorData.status === 409) {
                    errorMessage = t('goTravelNamespace3:' + errorData.message)
                }
                this.setState({
                    isError: true,
                    errorMessage
                })
            }
        })
    }

    render() {
        const {showMessage, errorMessage, isError} = this.state
        const {t} = this.props
        return (
            <div>
                <div className="bg-light">
                    <div className="container-fluid" style={{width: '1200px'}}>
                        <div className="row linkVerify">
                            <div className="col-lg-10 offset-lg-1" style={{width: '900px', height: '970px'}}>
                                <div className="bg-white shadow rounded" style={{marginTop: '15%'}}>
                                    <div className="row">

                                        <div className="col-md-5 ps-0 d-none d-md-block">
                                            <div className="form-right h-100" style={{marginTop: '-20px'}}>
                                                <img alt="" src={logo} width="300" height="300" className="img"/>
                                            </div>
                                        </div>

                                        {this.state.expires ? (
                                            <div className="col-md-7 pe-0">

                                                <p className={"text-center registerHeader"}>{t('goTravelNamespace3:emailChange')}</p>
                                                <div className="form-left h-100 py-5 px-5">
                                                    <Form onSubmit={this.handleSendEmail}>
                                                        <div className="col-12">
                                                            <div className="col-12">
                                                                <label>{t('goTravelNamespace3:currentEmailAddress')}<span
                                                                    className="text-danger"></span></label>
                                                                <div className="input-group">
                                                                    <div className="input-group-text"><FaUserAlt
                                                                        style={{color: '#4ec3ff'}}/></div>
                                                                    <input type="text" className="form-control"
                                                                           placeholder={t('goTravelNamespace3:currentEmailAddress')}
                                                                           name="currentEmailAddress" disabled={true}
                                                                           value={this.state.email}/>
                                                                </div>
                                                            </div>
                                                            <div className="col-12 mt-3">
                                                                <label>{t('goTravelNamespace3:newEmailAddress')}<span
                                                                    className="text-danger"></span></label>
                                                                <div className="input-group">
                                                                    <div className="input-group-text"><FaUserAlt
                                                                        style={{color: '#4ec3ff'}}/></div>
                                                                    <input type="text" className="form-control"
                                                                           placeholder={t('goTravelNamespace3:newEmailAddress')}
                                                                           name="newEmailAddress" onChange={(e) => {
                                                                        this.setState({newEmail: e.target.value})
                                                                    }}/>
                                                                </div>
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
                                                        <Modal.Body style={{fontFamily: 'Comic Sans MS'}}>
                                                            {t('goTravelNamespace3:linkHasBeenSend')}
                                                        </Modal.Body>
                                                        <Modal.Footer style={{
                                                            display: 'flex',
                                                            alignContent: 'center',
                                                            marginRight: '25%'
                                                        }}>
                                                            <Button
                                                                variant="btn btn-primary px-4 float-end mt-4 loginButton"
                                                                style={{width: '150px'}} href={'/customerZone/login'}>
                                                                Ok
                                                            </Button>
                                                        </Modal.Footer>
                                                    </Modal>
                                                    {isError && <Message negative
                                                                         className={"mt-3 messageError w-75"}>{errorMessage}</Message>}
                                                </div>
                                            </div>
                                        ) : (
                                            <div className="col-md-7 pe-0">

                                                <p className={"text-center registerHeader"}>{t('goTravelNamespace3:somethingWentWrong')}</p>
                                                <p className={"registerMessage"}>{t('goTravelNamespace3:incorrectRegistrationVerificationMessage')}</p>
                                                <div className="form-left h-100 py-5 px-5">
                                                    <div className="d-grid mb-3">
                                                        <Nav.Link as={Link} to={"/"}
                                                                  className="btn btn-primary goTo"
                                                                  style={{height: '40px'}}>{t('goTravelNamespace3:goToTheHomePage')}</Nav.Link>
                                                    </div>
                                                </div>
                                            </div>
                                        )}
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

export default withTranslation()(ChangeEmail);