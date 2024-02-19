import React, {Component} from 'react'
import {Link, Navigate} from 'react-router-dom'
import {Form, Message} from 'semantic-ui-react'
import AuthContext from '../../others/AuthContext'
import {goTravelApi} from '../../others/GoTravelApi'
import {getSocialLoginUrl, handleLogError, parseJwt} from '../../others/JWT'
import './CustomerZoneLogin.css'
import {Nav} from "react-bootstrap"
import {withTranslation} from "react-i18next";
import {BsFacebook} from "react-icons/bs";
import {FcGoogle} from "react-icons/fc";

class Login extends Component {

    static contextType = AuthContext

    state = {
        username: '',
        password: '',
        isLoggedIn: false,
        isError: false,
        twoFA: false,
        totp: 0,
    }

    componentDidMount() {
        (async () => {
            const isLoggedIn = await this.context.userIsAuthenticated();
            this.setState({isLoggedIn});
        })();
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    handleSubmitLogin = (e) => {
        e.preventDefault()

        const {username, password} = this.state
        if (!(username && password)) {
            this.setState({isError: true})
            return
        }

        goTravelApi.isUsing2FA(username).then(res => {
            if (res.data === true) {
                this.setState({
                    twoFA: true,
                    isError: false
                })
            } else {
                this.login({username, password})
            }
        }).catch(error => {
            handleLogError(error)
            this.setState({isError: true})
        })
    }

    handleSubmitVerifyCode = async () => {
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;
        const {username, password, totp} = this.state

        if (totp === 0) {
            this.setState({isError: true})
            return
        }

        goTravelApi.verify2FACode(csrfToken, {username, totp}).then(response => {
            if (response.data === true) {
               this.login({username, password})
            } else {
                this.setState({isError: true})
            }
        })
    }

    login = (signInInfo) => {
        goTravelApi.login(signInInfo).then(response => {
            const {accessToken} = response.data
            const {refreshToken} = response.data
            const data = parseJwt(accessToken)
            const user = {data, accessToken, refreshToken}

            const Auth = this.context
            Auth.userLogin(user)

            this.setState({
                username: '',
                password: '',
                totp: 0,
                isLoggedIn: true,
                isError: false
            })
        }).catch(error => {
            handleLogError(error)
            this.setState({isError: true})
        })
    }

    verifyStyleOn = () => {
        return this.state.twoFA ? {"display": "block"} : {"display": "none"}
    }

    verifyStyleOff = () => {
        return this.state.twoFA ? {"display": "none"} : {"display": "block"}
    }

    render() {
        const {t} = this.props
        const {isLoggedIn, isError} = this.state
        if (isLoggedIn) {
            return <Navigate to={'/'}/>
        } else {
            return (
                <body className="img js-fullheight">
                <main id={"content"}>

                    <div className="container" style={this.verifyStyleOff()}>
                        <div className="row">
                            <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                                <div className="card border-0 shadow rounded-3 my-5">
                                    <div className="card-body p-4 p-sm-5">
                                        <h5 className="card-title text-center mb-5 fw-light fs-5">{t('login')}</h5>
                                        <Form onSubmit={this.handleSubmitLogin}>

                                            <Form.Field>
                                                <label>{t('username')}:</label>
                                                <Form.Input fluid autoFocus type="text" id="floatingInput"
                                                            placeholder={t('username')} name="username"
                                                            onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>{t('password')}:</label>
                                                <Form.Input fluid type="password" id="floatingInput"
                                                            placeholder={t('password')} name="password"
                                                            onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <div className="d-grid">
                                                <button className="btn btn-primary btn-login text-uppercase fw-bold"
                                                        type="submit">{t('login')}
                                                </button>
                                            </div>

                                            <div className="line-container">
                                                <hr className="line"/>
                                                <span className="text">{t('or').toUpperCase()}</span>
                                                <hr className="line"/>
                                            </div>
                                            <div className="col-sm-12" style={{width: '70%'}}>
                                                <div className={"row"}
                                                     style={{marginLeft: '45%', marginTop: '-8%'}}>
                                                    <div className={"col"}>
                                                        <a href={getSocialLoginUrl('facebook')} className="btn btn-primary btn-floating mx-1 bg-white border-white" style={{width: '50px', height: '40px', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                                                            <BsFacebook style={{color: 'blue', width: '30px', height: '30px'}}/>
                                                        </a>

                                                    </div>
                                                    <div className={"col"}>
                                                        <a href={getSocialLoginUrl('google')} className="btn btn-primary btn-floating mx-1 bg-white border-white" style={{width: '50px', height: '40px', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                                                            <FcGoogle style={{width: '30px', height: '30px'}}/>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className="d-grid mb-2 mt-3">
                                                <p>{t('youDontHaveAnAccountYet')}</p>
                                                <Nav.Link as={Link} to={"/customerZone/registration"}
                                                          className="btn btn-primary btn-login text-uppercase fw-bold">{t('register')}</Nav.Link>
                                            </div>
                                            {isError && <Message className={"messageErrorLogin"}>{t('usernameOrPasswordAreIncorrect')}</Message>}
                                        </Form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style={this.verifyStyleOn()}>
                        <div className="container">
                            <div className="row">
                                <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                                    <div className="card border-0 shadow rounded-3 my-5">
                                        <div className="card-body p-4 p-sm-5">
                                            <h5 className="card-title text-center mb-5 fw-light fs-5">{t('6digitCode')}</h5>
                                            <Form onSubmit={this.handleSubmitVerifyCode}>

                                                <Form.Field>
                                                    <label>{t('2FACode')}</label>
                                                    <Form.Input fluid autoFocus type="text" id="floatingInput"
                                                                placeholder={t('code')} name="totp"
                                                                onChange={this.handleInputChange}/>
                                                </Form.Field>


                                                <div className="d-grid">
                                                    <button className="btn btn-primary btn-login text-uppercase fw-bold"
                                                            type="submit">{t('login')}
                                                    </button>
                                                </div>


                                                <hr className="my-4"></hr>

                                                {isError && <Message className={"messageErrorLogin"}>{t('error')}</Message>}
                                            </Form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                </body>
            )
        }
    }
}

export default withTranslation()(Login);