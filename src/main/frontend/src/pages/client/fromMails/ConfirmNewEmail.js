import React, {Component} from "react";
import {withTranslation} from "react-i18next";
import logo from "../../../assets/image/logo.svg";
import {Nav} from "react-bootstrap";
import {Link} from "react-router-dom";
import {goTravelApi} from "../../../others/GoTravelApi";
import {handleLogError} from "../../../others/JWT";

class ConfirmNewEmail extends Component {

    state = {
        confirm: false
    }

    componentDidMount() {
        const urlSearchParams = new URLSearchParams(window.location.search);
        const emailParam = urlSearchParams.get('email');
        const usernameParam = urlSearchParams.get('username');
        const expirationTimestamp = urlSearchParams.get('expires')

        if (expirationTimestamp) {
            const  currentTimestamp = Math.floor(Date.now() / 1000)
            if (currentTimestamp < parseInt(expirationTimestamp, 10)) {
                goTravelApi.confirmEmail(emailParam, usernameParam).then(() => {
                    this.setState({confirm: true})
                    localStorage.removeItem('user')
                }).catch(error => {
                    this.setState({confirm: false})
                    handleLogError(error)
                })
            }
        }
    }

    render() {
        const {t} = this.props
        const {confirm} = this.state

        return (
            <div>
                <div className="bg-light">
                    <div className="container-fluid" style={{width: '1200px'}}>
                        <div className="row linkVerify" >
                            <div className="col-lg-10 offset-lg-1" style={{width: '900px', height: '970px'}}>
                                <div className="bg-white shadow rounded" style={{marginTop: '15%'}}>
                                    <div className="row">

                                        <div className="col-md-5 ps-0 d-none d-md-block">
                                            <div className="form-right h-100" style={{marginTop: '-20px'}}>
                                                <img alt="" src={logo} width="300" height="300" className="img"/>
                                            </div>
                                        </div>

                                        {confirm ? (
                                            <div className="col-md-7 pe-0">

                                                <p className={"text-center registerHeader"}>{t('goTravelNamespace3:emailHasBeenVerified')}</p>
                                                <div className="form-left h-100 py-5 px-5">
                                                    <div className="d-grid mb-3">
                                                        <Nav.Link as={Link} to={'/customerZone/login'}
                                                                  className="btn btn-primary goTo"
                                                                  style={{height: '40px'}}>{t('login')}</Nav.Link>
                                                    </div>
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

export default withTranslation()(ConfirmNewEmail);
