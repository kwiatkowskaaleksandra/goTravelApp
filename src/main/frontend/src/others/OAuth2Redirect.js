import React, { Component } from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import { parseJwt } from './JWT'
import AuthContext from "./AuthContext";

// A higher-order component that injects the current location into the wrapped component's props
function withLocation(Component) {
    return props => <Component {...props} location={useLocation()} />
}

class OAuth2Redirect extends Component {
    static contextType = AuthContext

    state = {
        redirectTo: '/'
    }

    componentDidMount() {
        // Extract the 'token' parameter from the URL
        const accessToken = this.extractUrlParameter('token')
        const refreshToken = this.extractUrlParameter('refresh')
        if (accessToken && refreshToken) {
            this.handleLogin(accessToken, refreshToken)
            const redirect = "/"
            this.setState({ redirect })
        }
    }

    // Extract a parameter from the URL query string
    extractUrlParameter = (key) => {
        return new URLSearchParams(this.props.location.search).get(key)
    }

    // Handle user login after receiving the access token
    handleLogin = async (accessToken, refreshToken) => {
        const data = parseJwt(accessToken)

        const user = {data, accessToken, refreshToken}

        const Auth = this.context
        await Auth.userLogin(user)
    }

    render() {
        return <Navigate to={this.state.redirectTo} />
    }
}

export default withLocation(OAuth2Redirect)