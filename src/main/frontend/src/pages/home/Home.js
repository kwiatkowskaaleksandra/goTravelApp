import React, {Component} from 'react'
import './Home.css'
import {withTranslation} from "react-i18next";
import AuthContext from "../../others/AuthContext";
import HomeEmployee from "../employee/HomeEmployee";
import HomeClient from "../client/HomeClient";

class Home extends Component {

    static contextType = AuthContext

    state = {
        userInfoRoles: []
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        if (user != null) {
            this.setState({
                userInfoRoles: user.roles
            })
            console.log(user.roles)
        }
    }

    render() {
        return (
            <main>
                <header>
                    {this.state.userInfoRoles.includes('ROLE_MODERATOR') ? (
                        <HomeEmployee/>
                    ) : (
                        <HomeClient/>
                    )}
                </header>
            </main>
        )
    }
}

export default withTranslation()(Home);