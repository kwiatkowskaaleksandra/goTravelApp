import React, {Component, useContext} from "react";
import {goTravelApi} from "./GoTravelApi";
import {handleLogError, parseJwt} from "./JWT";

const AuthContext = React.createContext();

class AuthProvider extends Component {
    state = {
        user: null,
    }

    componentDidMount() {
        const user = localStorage.getItem('user')
        this.setState({user});
    }

    getUser = () => {
        return JSON.parse(localStorage.getItem('user'))
    }

    userIsAuthenticated = async () => {
        let user = localStorage.getItem('user')
        if (!user) {
            return false;
        }
        user = JSON.parse(user)

        //jeśli token użytkownika wygasł, wyloguj użytkownika
        if (Date.now() > user.data.exp * 1000) {
            try {
                const res = await goTravelApi.refreshToken(user.refreshToken);
                const { accessToken, refreshToken } = res.data;
                const data = parseJwt(accessToken);
                localStorage.setItem('user', JSON.stringify({ data, accessToken, refreshToken }));
                return true;
            } catch (error) {
                handleLogError(error)
                if (error.response.data.status === 403) {
                    localStorage.removeItem('user')
                    this.setState({user: null})
                    window.location.href = "/"
                    return false
                }
                await this.userLogout();
                return false;
            }
        }
        return true
    }

    userLogin = user => {
        localStorage.setItem('user', JSON.stringify(user))
        this.setState({user})
    }

    userLogout = async () => {
        const user = this.getUser()
        const csrfResponse = await goTravelApi.csrf();
        const csrfToken = csrfResponse.data.token;

        goTravelApi.signout(user, csrfToken).then(() => {
            localStorage.removeItem('user')
            this.setState({user: null})
            window.location.href = "/"
        })

    }

    render() {
        const {children} = this.props
        const {user} = this.state
        const {getUser, userIsAuthenticated, userLogin, userLogout} = this

        return (
            <AuthContext.Provider value={{user, getUser, userIsAuthenticated, userLogin, userLogout}}>
                {children}
            </AuthContext.Provider>
        )
    }
}

export default AuthContext

export function useAuth() {
    return useContext(AuthContext)
}

export {AuthProvider}

