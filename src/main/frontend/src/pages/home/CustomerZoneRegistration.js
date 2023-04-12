import React, {Component} from 'react'
import {Link, Navigate} from 'react-router-dom'
import {Checkbox, Form, Message} from 'semantic-ui-react'
import AuthContext from "../../others/AuthContext";
import {orderApi} from '../../others/OrderApi'
import {handleLogError} from '../../others/Helpers'
import './CustomerZoneRegistration.css'
import {Nav} from "react-bootstrap"

class CustomerZoneRegistration extends Component {
    static contextType = AuthContext

    state = {
        username: '',
        password: '',
        password2: '',
        firstname: '',
        lastname: '',
        email: '',
        isLoggedIn: false,
        isError: false,
        errorMessage: '',
        acceptanceOfPolicy: false
    }

    componentDidMount() {
        const Auth = this.context
        const isLoggedIn = Auth.userIsAuthenticated()
        this.setState({ isLoggedIn })
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({ [name]: value })
    }

    onChangeCheckBox = (e, data) =>{
        const acceptanceOfPolicy = data.checked
        this.setState({acceptanceOfPolicy})
    }

    handleSubmit = (e) => {
        e.preventDefault()

        const { username, password, password2, firstname, lastname, email, acceptanceOfPolicy } = this.state
        if (!(username && password && password2 && firstname && lastname && email)) {
            this.setState({
                isError: true,
                errorMessage: 'Proszę uzupełnić wszystkie pola!'
            })
            return
        }

        if(password!==password2){
            this.setState({
                isError: true,
                errorMessage: 'Podane hasła różnią się od siebie.'
            })
            return
        }

        if(!acceptanceOfPolicy){
            this.setState({
                isError: true,
                errorMessage: 'Proszę zaakceptować regulamin.'
            })
            return
        }

        const user = { username, password, firstname, lastname, email }
        orderApi.signup(user)
            .then(response => {
         //       const { accessToken } = response.data
          //      const data = parseJwt(accessToken)
          //      const user = { data, accessToken }

               // const Auth = this.context
             //   Auth.userLogin(user)

                this.setState({
                    username: '',
                    password: '',
                    isLoggedIn: false,
                    isError: false,
                    errorMessage: ''
                })
                window.location.href="/customerZone/login"
            })
            .catch(error => {
                handleLogError(error)
                if (error.response && error.response.data) {
                    const errorData = error.response.data
                    let errorMessage = 'Pola zostały błędnie uzupełnione.'
                    if (errorData.status === 409) {
                        errorMessage = errorData.message
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
        const { isLoggedIn, isError, errorMessage } = this.state
        if (isLoggedIn) {
            return <Navigate to='/' />
        } else {
            return (
                <body className="img js-fullheight">
                <main id={"content"}>

                    <div className="container">
                        <div className="row">
                            <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                                <div className="card border-0 shadow rounded-3 my-5">
                                    <div className="card-body p-4 p-sm-5">
                                        <h5 className="card-title text-center mb-5 fw-light fs-5">Zarejestruj się</h5>
                                        <Form onSubmit={this.handleSubmit}>

                                        <Form.Field>
                                                <label>Imię</label>
                                                <Form.Input fluid autoFocus type="text" id="floatingInput" placeholder="imię" name="firstname" onChange={this.handleInputChange}/>
                                        </Form.Field>

                                            <Form.Field>
                                                <label>Nazwisko</label>
                                                <Form.Input fluid type="text" id="floatingInput" placeholder="nazwisko" name="lastname" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>Nazwa użytkownika</label>
                                                <Form.Input fluid type="text" id="floatingInput" placeholder="nazwa użytkownika" name="username" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>Hasło</label>
                                                <Form.Input fluid type="password" id="floatingInput" placeholder="hasło" name="password" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>Powtórz hasło</label>
                                                <Form.Input fluid type="password" id="floatingInput" placeholder="hasło" name="password2" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <label>Email</label>
                                                <Form.Input fluid type="text" id="floatingInput" placeholder="email" name="email" onChange={this.handleInputChange}/>
                                            </Form.Field>

                                            <Form.Field>
                                                <Checkbox toggle label={'Akceptuję postanowienia polityki prywatności.'} onClick={(e, data) => this.onChangeCheckBox(e,data)}/>
                                            </Form.Field>

                                            <div className="d-grid">
                                                <button className="btn btn-primary btn-login text-uppercase fw-bold" type="submit">Zarejestruj się</button>
                                            </div>

                                            <hr className="my-4"></hr>

                                            <div className="d-grid mb-2">
                                                <Nav.Link as={Link} to={"/customerZone/login"}
                                                          className="btn btn-primary btn-login text-uppercase fw-bold"> Masz
                                                    już konto ?</Nav.Link>
                                            </div>

                                        </Form>
                                        {isError && <Message negative>{errorMessage}</Message>}
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

export default CustomerZoneRegistration

