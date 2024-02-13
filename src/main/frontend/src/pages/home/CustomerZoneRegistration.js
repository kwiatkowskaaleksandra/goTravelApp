import React, {Component} from 'react'
import {Link, Navigate} from 'react-router-dom'
import {Checkbox, Form, Message} from 'semantic-ui-react'
import AuthContext from "../../others/AuthContext";
import {handleLogError} from '../../others/JWT'
import './CustomerZoneRegistration.css'
import {Nav} from "react-bootstrap"
import {goTravelApi} from "../../others/GoTravelApi";
import {withTranslation} from "react-i18next";

class CustomerZoneRegistration extends Component {
    static contextType = AuthContext

    state = {
        username: '',
        password: '',
        repeatedPassword: '',
        firstname: '',
        lastname: '',
        email: '',

        isLoggedIn: false,
        isError: false,
        errorMessage: '',
        acceptanceOfPolicy: false
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

    onChangeCheckBox = (e, data) => {
        const acceptanceOfPolicy = data.checked
        this.setState({acceptanceOfPolicy})
    }

    handleSubmit = (e) => {
        e.preventDefault()
        const { t } = this.props;
        const role = ["USER"]
        const {username, password, repeatedPassword, firstname, lastname, email, acceptanceOfPolicy} = this.state

        if (!(username && password && repeatedPassword && firstname && lastname && email)) {
            this.setState({
                isError: true,
                errorMessage: t('pleaseCompleteAllFields')
            })
            return
        }

        if (!acceptanceOfPolicy) {
            this.setState({
                isError: true,
                errorMessage: t('pleaseAcceptTheRegulations')
            })
            return
        }

        const user = {username, password, repeatedPassword, firstname, lastname, email, role}

        goTravelApi.signup(user).then(() => {
                this.setState({
                    username: '',
                    password: '',
                    repeatedPassword: '',
                    isLoggedIn: false,
                    isError: false,
                    errorMessage: ''
                })
                window.location.href = "/customerZone/login"
            })
                .catch(error => {
                    handleLogError(error)
                    if (error.response && error.response.data) {
                        const errorData = error.response.data
                        let errorMessage = t('theFieldsWereFilledInIncorrectly')
                        if (errorData.status === 409) {
                            errorMessage = t('goTravelNamespace3:' + errorData.message)
                        } else if (errorData.status === 400) {
                            errorMessage = errorData.errors[0].defaultMessage
                        }
                        this.setState({
                            isError: true,
                            errorMessage
                        })
                    }
                })

    }

    render() {
        const {t} = this.props
        const {isLoggedIn, isError, errorMessage} = this.state
        if (isLoggedIn) {
            return <Navigate to='/'/>
        } else {
            return (
                <body className="img js-fullheight">
                <main id={"content"}>

                    <div className="container">
                        <div className="row">
                            <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                                <div className="card border-0 shadow rounded-3 my-5">
                                    <div className="card-body p-4 p-sm-5">
                                        <h5 className="card-title text-center mb-5 fw-light fs-5">{t('register')}</h5>
                                        <Form onSubmit={this.handleSubmit}>

                                            <Form.Field>
                                                <label>{t('firstname')}</label>
                                                <Form.Input fluid autoFocus type="text" id="floatingInput"
                                                            placeholder={t('firstname')} name="firstname"
                                                            onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>{t('lastname')}</label>
                                                <Form.Input fluid type="text" id="floatingInput" placeholder={t('lastname')}
                                                            name="lastname" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>{t('username')}</label>
                                                <Form.Input fluid type="text" id="floatingInput"
                                                            placeholder={t('username')} name="username"
                                                            onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>{t('password')}</label>
                                                <Form.Input fluid type="password" id="floatingInput" placeholder={t('password')}
                                                            name="password" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>{t('repeatedPassword')}</label>
                                                <Form.Input fluid type="password" id="floatingInput" placeholder={t('repeatedPassword')}
                                                            name="repeatedPassword" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>{t('email')}</label>
                                                <Form.Input fluid type="text" id="floatingInput" placeholder={t('email')}
                                                            name="email" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <Checkbox toggle label={t('acceptTheProvisionsOfThePrivacyPolicy')}
                                                          onClick={(e, data) => this.onChangeCheckBox(e, data)}/>
                                            </Form.Field>

                                            <div className="d-grid">
                                                <button className="btn btn-primary btn-login text-uppercase fw-bold"
                                                        type="submit">{t('register')}
                                                </button>
                                            </div>

                                            <hr className="my-4"></hr>

                                            <div className="d-grid mb-2">
                                                <Nav.Link as={Link} to={"/customerZone/login"}
                                                          className="btn btn-primary btn-login text-uppercase fw-bold">{t('alreadyHaveAnAccount')}</Nav.Link>
                                            </div>

                                        </Form>
                                        {isError && <Message className={"messageErrorRegister"}>{errorMessage}</Message>}
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

export default withTranslation()(CustomerZoneRegistration);

