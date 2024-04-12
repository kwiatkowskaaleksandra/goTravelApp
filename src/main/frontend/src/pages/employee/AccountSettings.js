import {Component} from "react";
import {withTranslation} from "react-i18next";
import NavigationBar from "../home/NavigationBar";
import Footer from "../home/Footer";
import AuthContext from "../../others/AuthContext";
import {Message} from "semantic-ui-react";
import {Tab, Tabs} from "react-bootstrap";
import {goTravelApi} from "../../others/GoTravelApi";
import {handleLogError} from "../../others/JWT";

class AccountSettings extends Component {

    static contextType = AuthContext

    state = {
        key: 'userData',
        isError: false,
        errorMessage: '',
        userInfo: null,
        userData: [],
        oldPassword: '',
        newPassword: '',
        repeatedNewPassword: '',
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            goTravelApi.getUserInfo(user).then(res => {
                this.setState({
                    userData: res.data,
                    userInfo: user
                })
            }).catch(error  => {
                console.log(error)
            })
        }
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

        goTravelApi.changePassword(passwordRequest, csrfToken, this.state.userInfo).then(() => {
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

    handleTabSelect = (selectedKey) => {
        this.setState({key: selectedKey});
    };

    render() {
        const {t} = this.props
        const {key} = this.state;

        return (
            <main>
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
                                </Tabs>
                            </div>
                            <div className="ml-auto">
                                {key === 'userData' &&
                                    <div className={"d-flex justify-content-center"} style={{width: '750px'}}>
                                        <div style={{textAlign: 'center'}}>
                                            <form className="form-floating row gy-2 gx-3" style={{marginLeft: '30px'}}>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                            <span className="input-group-text w-25">{t('firstname')}</span>
                                                            <input disabled type="text" className="form-control" id="firstname" placeholder={this.state.userData.firstname} />
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('lastname')}</span>
                                                            <input disabled type="text" className="form-control" id="lastname" placeholder={this.state.userData.lastname}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                        <span className="input-group-text w-25"
                                                              id="basic-addon1">{"Email"}</span>
                                                            <input disabled type="email" className="form-control" id="email" placeholder={this.state.userData.email}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                <span className="input-group-text w-25"
                                                      id="basic-addon1">{t('username')}</span>
                                                            <input disabled type="text" className="form-control" id="username" placeholder={this.state.userData.username}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                <span className="input-group-text w-25"
                                                      id="basic-addon1">{t('goTravelNamespace2:phoneNumber')}</span>
                                                            <input disabled type="text" className="form-control" id="phoneNumber" placeholder={this.state.userData.phoneNumber}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('goTravelNamespace2:city')}</span>
                                                            <input disabled type="text" className="form-control" id="city" placeholder={this.state.userData.city}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('goTravelNamespace2:street')}</span>
                                                            <input disabled type="text" className="form-control" id="street" placeholder={this.state.userData.street}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                    <span className="input-group-text w-25"
                                                          id="basic-addon1">{t('goTravelNamespace2:streetNumber')}</span>
                                                            <input disabled type="text" className="form-control" id="streetNumber" placeholder={this.state.userData.streetNumber}/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className={"row"}>
                                                    <div className={"col"}>
                                                        <div className="input-group mb-3">
                                                        <span className="input-group-text w-25"
                                                              id="basic-addon1">{t('goTravelNamespace2:zipCode')}</span>
                                                            <input disabled type="text" className="form-control" id="zipCode" placeholder={this.state.userData.zipCode}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>

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
                                            {this.state.isError &&
                                                <Message negative className={"mt-3 messageError"}>{this.state.errorMessage}</Message>}
                                        </div>

                                    </div>
                                </div>
                                }
                            </div>
                        </div>
                    </section>
                </header>
                <Footer/>
            </main>
        )
    }
}

export default withTranslation()(AccountSettings);