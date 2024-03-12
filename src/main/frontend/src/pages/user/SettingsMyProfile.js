import {Component} from "react";
import {withTranslation} from "react-i18next";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import {Modal, Tab, Tabs} from "react-bootstrap";
import AuthContext from "../../others/AuthContext";
import {Message} from "semantic-ui-react";
import {goTravelApi} from "../../others/GoTravelApi";
import {handleLogError} from "../../others/JWT";
import Button from "react-bootstrap/Button";

class SettingsMyProfile extends Component {

    static contextType = AuthContext

    state = {
        key: 'userData',
        isError: false,
        errorMessage: '',
        showMessage: false,
        showModalQRCode: false,
        user: null,

        username: '',
        firstname: '',
        lastname: '',
        email: '',
        phoneNumber: 0,
        city: '',
        street: '',
        streetNumber: '',
        zipCode: 0,
        oldPassword: '',
        newPassword: '',
        repeatedNewPassword: '',
        using2FA: false,
        showQrCodeButton: false,
        qrCode: '',
        provider: ''
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            goTravelApi.getUserInfo(user).then(res => {

                this.setState({
                    usernameID: res.data.username,
                    username: res.data.username,
                    firstname: res.data.firstname,
                    lastname: res.data.lastname,
                    email: res.data.email,
                    phoneNumber: res.data.phoneNumber,
                    city: res.data.city,
                    street: res.data.street,
                    streetNumber: res.data.streetNumber,
                    zipCode: res.data.zipCode,
                    using2FA: res.data.using2FA,
                    provider: res.data.provider,
                    user: user
                })
            }).catch(error  => {
                console.log(error)
            })
        }
    }

    saveChangesUserData = async () => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;
        const {t} = this.props;

        const userDetails = {
            username: this.state.username,
            firstname: this.state.firstname,
            lastname: this.state.lastname,
            email: this.state.email,
            phoneNumber: this.state.phoneNumber,
            city: this.state.city,
            street: this.state.street,
            streetNumber: this.state.streetNumber,
            zipCode: this.state.zipCode,
            using2FA: this.state.using2FA
        }

        goTravelApi.updateUserData(userDetails, csrfToken, this.state.user).then(() => {
            window.location.reload()
        }).catch(error => {
            handleLogError(error)
            if (error.response && error.response.data) {
                const errorData = error.response.data
                let errorMessage = t('goTravelNamespace2:fieldsHaveBeenFilledInIncorrectly')
                if (errorData.status === 409) {
                    errorMessage = t('goTravelNamespace2:' + errorData.message)
                } else if (errorData.status === 400) {
                    errorMessage = errorData.errors[0].defaultMessage
                } else if (errorData.status === 401) {
                    errorMessage = t('goTravelNamespace2:' + errorData.message)
                } else if (errorData.status === 404) {
                    errorMessage = t('goTravelNamespace2:' + errorData.message)
                }

                this.setState({
                    isError: true,
                    errorMessage
                })
            }
        })
    }

    saveChangePassword = async () => {
        const {t} = this.props;
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        const passwordRequest = {
            password: this.state.oldPassword,
            newPassword: this.state.newPassword,
            repeatedPassword: this.state.repeatedNewPassword
        }

        goTravelApi.changePassword(passwordRequest, csrfToken, this.state.user).then(() => {
            const Auth = this.context
            Auth.userLogout()
        }).catch(error => {
            handleLogError(error)
            if (error.response && error.response.data) {
                const errorData = error.response.data
                let errorMessage = t('goTravelNamespace2:fieldsHaveBeenFilledInIncorrectly')
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

    deleteAccount = async () => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.deleteAccount(csrfToken, this.state.user).then(() => {
            localStorage.removeItem('user')
            this.setState({user: null})
            window.location.href = "/"
        }).catch(error => {
            handleLogError(error)
        })
    }

    changeEmail = () => {
        goTravelApi.sendConfirmationEmail(this.state.username).then(() => {
            this.setState({showMessage: true})
        }).catch(error => {
            handleLogError(error)
        })
    }

    changeOf2FAInclusion = async (enable) => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.changeOf2FAInclusion(csrfToken, this.state.user, enable).then(response => {
            if (enable && response.data !== '') {
                this.setState({
                    qrCode: response.data,
                    showQrCodeButton: true
                })
            } else {
                window.location.reload()
            }
        }).catch(error => {
            handleLogError(error)
        })
    }

    showQrCodeButton = () => {
        return this.state.showQrCodeButton ? {"display": "block"} : {"display": "none"}
    }

    handleShowModalVerify = () => {
        this.setState({showModalQRCode: true});
    }

    handleCloseModalQRCode = () => {
        this.setState({showModalQRCode: false});
    };

    handleTabSelect = (selectedKey) => {
        this.setState({key: selectedKey});
    };

    render() {
        const {t} = this.props
        const {key, isError, errorMessage, showMessage, showModalQRCode} = this.state;

        return (
            <div>
                <NavigationBar/>
                <section className='d-flex justify-content-center justify-content-lg-between p-2  mt-4'></section>
                <header className={"head"}>
                    <section className={"d-flex justify-content-center"}>
                        <div className="d-flex justify-content-center w-75">
                            <div className="d-flex flex-column align-items-start">
                                <Tabs
                                    id="controlled-tab"
                                    activeKey={key}
                                    onSelect={this.handleTabSelect}
                                    className="mb-3 flex-column"
                                >
                                    <Tab eventKey="userData" title={t('goTravelNamespace2:userData')} />
                                    <Tab eventKey="passwordChange" title={t('goTravelNamespace2:passwordChange')}/>
                                    <Tab eventKey="emailChange" title={t('goTravelNamespace2:emailChange')} disabled={this.state.provider !== 'LOCAL'}/>
                                    <Tab eventKey="2FA" title={t('goTravelNamespace3:2FA')} disabled={this.state.provider !== 'LOCAL'}/>
                                    <Tab eventKey="deletionOfTheAccount" title={t('goTravelNamespace3:deletionOfTheAccount')} />
                            </Tabs>
                            </div>
                            <div className="ml-auto">
                                {key === 'userData' &&
                                    <div className={"d-flex justify-content-center"} style={{width: '750px'}}>
                                        <div style={{textAlign: 'center'}}>
                                            <form className="form-floating row gy-2 gx-3"
                                                  style={{marginLeft: '30px'}}>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                            <span className="input-group-text w-25">{t('firstname')}</span>
                                                            <input type="text" className="form-control" id="firstname"
                                                                   onChange={(e) => {
                                                                       this.setState({firstname: e.target.value})
                                                                   }} placeholder={this.state.firstname}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('lastname')}</span>
                                                            <input type="text" className="form-control" id="lastname"
                                                                   onChange={(e) => {
                                                                       this.setState({lastname: e.target.value})
                                                                   }} placeholder={this.state.lastname}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                        <span className="input-group-text w-25"
                                                              id="basic-addon1">{"Email"}</span>
                                                            <input type="email" className="form-control" id="email"
                                                                   onChange={(e) => {
                                                                       this.setState({email: e.target.value})
                                                                   }} placeholder={this.state.email} disabled={true}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                <span className="input-group-text w-25"
                                                      id="basic-addon1">{t('username')}</span>
                                                            <input type="text" className="form-control" id="username"
                                                                   onChange={(e) => {
                                                                       this.setState({username: e.target.value})
                                                                   }} placeholder={this.state.username} disabled={true}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                <span className="input-group-text w-25"
                                                      id="basic-addon1">{t('goTravelNamespace2:phoneNumber')}</span>
                                                            <input type="text" className="form-control" id="phoneNumber"
                                                                   onChange={(e) => {
                                                                       this.setState({phoneNumber: e.target.value})
                                                                   }}
                                                                   placeholder={this.state.phoneNumber !== null ? this.state.phoneNumber.toString() : ''}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('goTravelNamespace2:city')}</span>
                                                            <input type="text" className="form-control" id="city"
                                                                   onChange={(e) => {
                                                                       this.setState({city: e.target.value})
                                                                   }} placeholder={this.state.city}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('goTravelNamespace2:street')}</span>
                                                            <input type="text" className="form-control" id="street"
                                                                   onChange={(e) => {
                                                                       this.setState({street: e.target.value})
                                                                   }} placeholder={this.state.street}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('goTravelNamespace2:streetNumber')}</span>
                                                            <input type="text" className="form-control" id="streetNumber"
                                                                   onChange={(e) => {
                                                                       this.setState({streetNumber: e.target.value})
                                                                   }} placeholder={this.state.streetNumber}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                        <span className="input-group-text w-25"
                                                              id="basic-addon1">{t('goTravelNamespace2:zipCode')}</span>
                                                            <input type="text" className="form-control" id="zipCode"
                                                                   onChange={(e) => {
                                                                       this.setState({zipCode: e.target.value})
                                                                   }}
                                                                   placeholder={this.state.zipCode !== null ? this.state.zipCode.toString() : ''}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>

                                            <button className="btn btn-primary w-25 saveChanges" type="submit"
                                                    onClick={this.saveChangesUserData}>{t('goTravelNamespace2:saveChanges')}</button>
                                            {isError &&
                                                <Message negative className={"mt-3 messageError"}>{errorMessage}</Message>}

                                        </div>
                                    </div>
                                }
                                {key === 'passwordChange' &&
                                    <div className={"d-flex justify-content-center"} style={{width: '750px', height: '400px'}}>
                                        <div style={{textAlign: 'center'}}>
                                            <form className="form-floating row gy-2 gx-3"
                                                  style={{marginLeft: '30px'}}>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                        <span
                                                            className="input-group-text w-25">{t('goTravelNamespace2:oldPassword')}</span>
                                                            <input type="password" className="form-control" id="oldPassword"
                                                                   onChange={(e) => {
                                                                       this.setState({oldPassword: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('goTravelNamespace2:newPassword')}</span>
                                                            <input type="password" className="form-control" id="newPassword"
                                                                   onChange={(e) => {
                                                                       this.setState({newPassword: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                        <span className="input-group-text w-25"
                                                              id="basic-addon1">{t('goTravelNamespace2:repeatedNewPassword')}</span>
                                                            <input type="password" className="form-control"
                                                                   id="repeatedNewPassword"
                                                                   onChange={(e) => {
                                                                       this.setState({repeatedNewPassword: e.target.value})
                                                                   }}/>
                                                        </div>
                                                    </div>
                                                </div>

                                            </form>

                                            <div className={"row justify-content-center"}>
                                                <button className="btn btn-primary w-25 saveChanges" type="submit"
                                                        onClick={this.saveChangePassword}>{t('goTravelNamespace2:saveChanges')}</button>

                                            </div>
                                            <div className={"row justify-content-md-start"}>
                                                <label>*{t('goTravelNamespace3:afterChangingYourPasswordYouWillBeAutomaticallyLoggedOut')}</label>
                                                {isError &&
                                                    <Message negative className={"mt-3 messageError"}>{errorMessage}</Message>}
                                            </div>

                                        </div>
                                    </div>
                                }
                                {key === 'emailChange' &&
                                    <div className={"d-flex justify-content-center"} style={{width: '750px', height: '400px'}}>
                                        <div>
                                            <h5 style={{
                                                fontWeight: 'bold',
                                                marginBottom: '2%'
                                            }}>{t('goTravelNamespace2:confirmTheChangeOfEmailAddress')}</h5>
                                            <h6>{t('goTravelNamespace2:beforeYouChangeEmailConfirmThatThisIsYourEmail').replace('%s', this.state.email)}</h6>
                                            <div className={"row justify-content-center"}>
                                                <button className="btn btn-primary w-50 saveChanges" type="submit"
                                                        onClick={this.changeEmail}>{t('goTravelNamespace2:sendConfirmationEmail')}</button>
                                            </div>
                                        </div>
                                    </div>
                                }
                                {key === '2FA' &&
                                    <div className={"d-flex justify-content-center"} style={{width: '750px'}}>
                                        <div style={{marginLeft: '30px'}}>
                                            <h5 style={{fontWeight: 'bold'}}>{t('goTravelNamespace3:2FA')}</h5>
                                            <p>{t('goTravelNamespace3:2FAInformationPart1')}</p>
                                            <ul>
                                                <li style={{
                                                    lineHeight: '1.5',
                                                    marginTop: '2%',
                                                    textAlign: 'justify'
                                                }}>{t('goTravelNamespace3:2FAInformationPart2')}</li>
                                                <li style={{
                                                    lineHeight: '1.5',
                                                    marginTop: '2%',
                                                    textAlign: 'justify'
                                                }}>{t('goTravelNamespace3:2FAInformationPart3')}</li>
                                                <li style={{
                                                    lineHeight: '1.5',
                                                    marginTop: '2%',
                                                    textAlign: 'justify'
                                                }}>{t('goTravelNamespace3:2FAInformationPart4')}</li>
                                                <li style={{
                                                    lineHeight: '1.5',
                                                    marginTop: '2%',
                                                    textAlign: 'justify'
                                                }}>{t('goTravelNamespace3:2FAInformationPart5')}</li>
                                            </ul>

                                            {this.state.using2FA ? (
                                                <div className="input-group mb-3" style={{alignContent:'center', textAlign: 'center', justifyContent: 'center'}}>
                                                    <div className={"btn btn-primary twoFactorAuthenticationEnable"} onClick={() => this.changeOf2FAInclusion(false)}>{t('goTravelNamespace3:disable2FA')}</div>
                                                </div>
                                            ) : (
                                                <div className="input-group mb-3" style={{alignContent:'center', textAlign: 'center', justifyContent: 'center'}}>
                                                    <div className={"btn btn-primary twoFactorAuthenticationEnable"} style={{borderBottomRightRadius: '5px', borderTopRightRadius: '5px'}} onClick={() => this.changeOf2FAInclusion(true)}>{t('goTravelNamespace3:enable2FA')}</div>
                                                    <div className="input-group mb-3 d-flex justify-content-center" style={{alignContent:'center', textAlign: 'center', marginTop: '4%'}}>
                                                        <p className="btn btn-primary twoFactorAuthenticationEnable"  style={this.showQrCodeButton()}
                                                           onClick={this.handleShowModalVerify}>{t('goTravelNamespace3:showQRCode')}
                                                        </p>
                                                    </div>
                                                </div>
                                            )}
                                        </div>
                                    </div>
                                }
                                {key === 'deletionOfTheAccount' && <div className={"d-flex"} style={{width: '750px'}}>
                                    <div style={{marginLeft: '30px'}}>
                                        <h5 style={{fontWeight: 'bold'}}>{t('goTravelNamespace3:accountDeletionWarning')}</h5>
                                        <h6 style={{lineHeight: '1.5'}}>{t('goTravelNamespace3:accountDeletionWarningPart1')}</h6>
                                        <ul>
                                            <li style={{
                                                lineHeight: '1.5',
                                                marginTop: '2%',
                                                textAlign: 'justify'
                                            }}>{t('goTravelNamespace3:accountDeletionWarningPart2')}</li>
                                            <li style={{
                                                lineHeight: '1.5',
                                                marginTop: '2%',
                                                textAlign: 'justify'
                                            }}>{t('goTravelNamespace3:accountDeletionWarningPart3')}</li>
                                            <li style={{
                                                lineHeight: '1.5',
                                                marginTop: '2%',
                                                textAlign: 'justify'
                                            }}>{t('goTravelNamespace3:accountDeletionWarningPart4')}</li>
                                            <li style={{
                                                lineHeight: '1.5',
                                                marginTop: '2%',
                                                textAlign: 'justify'
                                            }}>{t('goTravelNamespace3:accountDeletionWarningPart5')}</li>
                                        </ul>
                                        <h6 style={{lineHeight: '1.5', textAlign: 'justify'}}>{t('goTravelNamespace3:accountDeletionWarningPart6')}</h6>
                                        <div className={"row justify-content-center"}>
                                            <button className="btn btn-primary w-25 saveChanges" type="submit"
                                                    onClick={this.deleteAccount}>{t('goTravelNamespace3:deleteAccount')}</button>
                                        </div>
                                    </div>
                                    </div>
                                }
                            </div>
                        </div>
                    </section>
                </header>

                <Modal show={showMessage}>
                    <Modal.Body style={{fontFamily: 'Comic Sans MS'}}>
                        {t('goTravelNamespace3:activationLinkHasBeenSentToTheEmail')}
                    </Modal.Body>
                    <Modal.Footer style={{display: 'flex', alignContent: 'center'}}>
                        <Button variant="btn btn-primary px-4 float-end mt-4 saveChanges" style={{width: '100px'}}
                                href={'/myProfile/settings'}>
                            Ok
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Modal show={showModalQRCode} onHide={this.handleCloseModalQRCode}>
                    <Modal.Header closeButton onClick={() => window.location.reload()}>
                        <Modal.Title className={"text-center mx-auto"} style={{fontFamily: 'Comic Sans MS'}}>{t('goTravelNamespace3:enable2FA')}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Modal.Body className={'text-center'} style={{fontFamily: 'Comic Sans MS'}}>
                            {t('goTravelNamespace3:enable2FAMessage')}
                            <div className={'mx-auto'}>
                                <img alt={"qr code"} src={this.state.qrCode} />
                            </div>
                        </Modal.Body>
                    </Modal.Body>
                    <Modal.Footer>
                    </Modal.Footer>
                </Modal>

                <Footer/>
            </div>
        )
    }
}

export default withTranslation()(SettingsMyProfile)