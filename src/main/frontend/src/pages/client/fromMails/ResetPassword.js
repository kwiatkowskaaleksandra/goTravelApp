import {withTranslation} from "react-i18next";
import logo from "../../../assets/image/logo.svg";
import {Button, Form, Modal} from "react-bootstrap";
import React, {Component} from "react";
import {RiLockPasswordFill} from "react-icons/ri";
import {Message} from "semantic-ui-react";
import {goTravelApi} from "../../../others/GoTravelApi";
import {handleLogError} from "../../../others/JWT";

class ResetPassword extends Component {

    state = {
        email: '',
        password: '',
        repeatedPassword: '',
        showMessage: false,
        isError: false,
        errorMessage: '',
    }

    componentDidMount() {
        const urlSearchParams = new URLSearchParams(window.location.search);
        const codeParam = urlSearchParams.get('email');
        this.setState({email: codeParam})
    }

    handleInputChange = (event, {name, value}) => {
        this.setState({[name]: value})
    }

    handleResetPassword = async (event) => {
        event.preventDefault()
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;
        const {t} = this.props;

        if (this.state.email !== '' && this.state.repeatedPassword !== '') {
            const {password, repeatedPassword, email} = this.state
            const resetPassword = {
                newPassword: password,
                repeatedPassword: repeatedPassword
            }

            goTravelApi.resetPassword(resetPassword, email, csrfToken).then(() => {
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
    }

    render() {
        const {t} = this.props
        const {showMessage, isError, errorMessage} = this.state

        return (
            <div>
                <div className="bg-light">
                    <div className="container-fluid" style={{width: '1200px'}}>
                        <div className="row linkVerify">
                            <div className="col-lg-10 offset-lg-1" style={{width: '900px', height: '970px'}}>
                                <div className="bg-white shadow rounded" style={{marginTop: '15%'}}>
                                    <div className="row">

                                        <div className="col-md-5 ps-0 d-none d-md-block">
                                            <div className="form-right h-100">
                                                <img alt="" src={logo} width="300" height="300" className="img"/>
                                            </div>
                                        </div>


                                        <div className="col-md-7 pe-0">

                                            <p className={"text-center registerHeader"}>{t('goTravelNamespace3:setANewPassword')}</p>
                                            <div className="form-left h-100 py-5 px-5">
                                                <Form onSubmit={this.handleResetPassword}>
                                                    <div className="col-12">
                                                        <div className="col-12">
                                                            <label>{t('password')}<span className="text-danger">*</span></label>
                                                            <div className="input-group">
                                                                <div className="input-group-text"><RiLockPasswordFill style={{color: '#4ec3ff'}}/></div>
                                                                <input type="password" className="form-control"
                                                                       placeholder={t('password')} name="password" onChange={(event) => this.handleInputChange(event, { name: event.target.name, value: event.target.value })}/>
                                                            </div>
                                                        </div>

                                                        <div className="col-12 mt-4">
                                                            <label>{t('repeatedPassword')}<span className="text-danger">*</span></label>
                                                            <div className="input-group">
                                                                <div className="input-group-text"><RiLockPasswordFill style={{color: '#4ec3ff'}}/></div>
                                                                <input type="password" className="form-control"
                                                                       placeholder={t('password')} name="repeatedPassword" onChange={(event) => this.handleInputChange(event, { name: event.target.name, value: event.target.value })}/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="d-grid mb-3 mt-3" style={{justifyItems: 'center'}}>
                                                        <button type="submit"
                                                                className="btn btn-primary goTo"
                                                                style={{height: '40px', width: '140px'}}>{t('goTravelNamespace3:continue')}</button>
                                                    </div>
                                                </Form>
                                                {isError && <Message negative className={"mt-3 messageError w-75"}>{errorMessage}</Message>}
                                                <Modal show={showMessage}>
                                                    <Modal.Header style={{display: 'flex', alignContent:'center', alignSelf: 'center'}}>
                                                        <Modal.Title style={{fontFamily: 'Comic Sans MS', fontWeight: 'bold'}}>{t('goTravelNamespace3:passwordHasBeenChanged')}</Modal.Title>
                                                    </Modal.Header>
                                                    <Modal.Footer style={{display: 'flex', alignContent:'center'}}>
                                                        <Button variant="btn btn-primary px-4 float-end mt-4 loginButton" style={{width:'150px'}} href={'/customerZone/login'}>
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

export default withTranslation()(ResetPassword)