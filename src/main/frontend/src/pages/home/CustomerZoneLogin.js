import React, {Component} from 'react'
import {Link, Navigate} from 'react-router-dom'
import {Form, Message} from 'semantic-ui-react'
import AuthContext from '../../others/AuthContext'
import {orderApi} from '../../others/OrderApi'
import {handleLogError, parseJwt} from '../../others/Helpers'
import './CustomerZoneLogin.css'
import {Nav} from "react-bootstrap"

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
        const Auth = this.context
        const isLoggedIn = Auth.userIsAuthenticated()
        this.setState({isLoggedIn})
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    handleSubmit = (e) => {
        e.preventDefault()

        const {username, password} = this.state
        if (!(username && password)) {
            this.setState({isError: true})
            return
        }

        orderApi.findUser(username, password).then(response => {
            if(response.data === false){
                orderApi.authenticate(username, password)
                    .then(response => {

                        const {accessToken} = response.data
                        const data = parseJwt(accessToken)
                        const user = {data, accessToken}

                        const Auth = this.context
                        Auth.userLogin(user)

                        this.setState({
                            username: '',
                            password: '',
                            totp: 0,
                            isLoggedIn: true,
                            isError: false
                        })
                    })
                    .catch(error => {
                        handleLogError(error)
                        this.setState({isError: true})
                    })
            }
            else{
                this.setState({twoFA: true})
            }
        })
    }

    handleSubmitVerify = (e) => {
        e.preventDefault()

        const {username, password, totp} = this.state

        if(totp === 0){
            this.setState({isError: true})
            return
        }

        orderApi.verify(username, totp).then(response => {
            if(response.data === true){
                orderApi.authenticate(username, password).then(r => {
                    const {accessToken} = r.data
                    const data = parseJwt(accessToken)
                    const user = {data, accessToken}

                    const Auth = this.context
                    Auth.userLogin(user)

                    this.setState({
                        username: '',
                        password: '',
                        totp: 0,
                        isLoggedIn: true,
                        isError: false
                    })

                })
                    .catch(error => {
                        handleLogError(error)
                        console.log(error)
                        this.setState({isError: true})
                    })

            }else{
                this.setState({isError: true})
            }
        })
    }

    verifyStyleOn = () => {
        return this.state.twoFA ? {"display": "block"} : {"display": "none"}
    }

    verifyStyleOff = () => {
        return this.state.twoFA ? {"display": "none"} : {"display": "block"}
    }

    render() {
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
                                        <h5 className="card-title text-center mb-5 fw-light fs-5">Zaloguj się</h5>
                                        <Form onSubmit={this.handleSubmit}>

                                            <Form.Field>
                                                <label>Nazwa użytkownika:</label>
                                                <Form.Input fluid autoFocus type="text" id="floatingInput"
                                                            placeholder="nazwa użytkownika" name="username"
                                                            onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>Hasło:</label>
                                                <Form.Input fluid type="password" id="floatingInput"
                                                            placeholder="hasło" name="password"
                                                            onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <div className="d-grid">
                                                <button className="btn btn-primary btn-login text-uppercase fw-bold"
                                                        type="submit">Zaloguj się
                                                </button>
                                            </div>

                                            <hr className="my-4"></hr>

                                            <div className="d-grid mb-2">
                                                <p>Nie masz jeszcze konta ?</p>
                                                <Nav.Link as={Link} to={"/customerZone/registration"}
                                                          className="btn btn-primary btn-login text-uppercase fw-bold"> Zarejestruj
                                                    się</Nav.Link>
                                            </div>
                                            {isError && <Message negative>Nazwa użytkownika lub hasło są
                                                nieprawidłowe!</Message>}
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
                                            <h5 className="card-title text-center mb-5 fw-light fs-5">Proszę podać 6-cyfrowy kod z aplikacji autentykatora </h5>
                                            <Form onSubmit={this.handleSubmitVerify}>

                                                <Form.Field>
                                                    <label>2FA kod:</label>
                                                    <Form.Input fluid autoFocus type="text" id="floatingInput"
                                                                placeholder="kod" name="totp"
                                                                onChange={this.handleInputChange}/>
                                                </Form.Field>


                                                <div className="d-grid">
                                                    <button className="btn btn-primary btn-login text-uppercase fw-bold"
                                                            type="submit">Zaloguj się
                                                    </button>
                                                </div>


                                                <hr className="my-4"></hr>

                                                {isError && <Message negative>Błąd!</Message>}
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

export default Login
