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
        isError: false
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
                    isLoggedIn: true,
                    isError: false
                })
                window.location.href = "/customerHome"
            })
            .catch(error => {
                handleLogError(error)
                this.setState({isError: true})
            })
    }

//TODO: zapiętaj hasło i zapomnialem hasło

    render() {
        const {isLoggedIn, isError} = this.state
        if (isLoggedIn) {
            return <Navigate to={'/'}/>
        } else {
            return (
                <body className="img js-fullheight">

                <main id={"content"}>

                    <div className="container">
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

                                            <div className="form-check mb-3">
                                                <input className="form-check-input" type="checkbox" value=""
                                                       id="rememberPasswordCheck"/>
                                                <label className="form-check-label" htmlFor="rememberPasswordCheck">Zapamiętaj
                                                    hasło</label>
                                            </div>

                                            <div className="d-grid">
                                                <button className="btn btn-primary btn-login text-uppercase fw-bold"
                                                        type="submit">Zaloguj się
                                                </button>
                                            </div>
                                            <div className="d-grid mb-2">
                                                <a href={"#!"}>Zapomniałem hasła</a>
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
                </main>
                </body>
            )
        }
    }
}

export default Login
