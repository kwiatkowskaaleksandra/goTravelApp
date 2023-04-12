import {config} from '../Constants'

import {parseJwt} from "./Helpers";
import axios from "axios";

export const orderApi = {
    authenticate,
    signup,
    getUserInfo
}

function authenticate(username, password) {
    return instance.post('/gotravel/authenticate', { username, password }, {
        headers: { 'Content-type': 'application/json' }
    })
}

function signup(user) {
    return instance.post('/gotravel/signup', user, {
        headers: { 'Content-type': 'application/json' }
    })
}

function getUserInfo(user) {
    return instance.get('/api/users/me', {
        headers: { 'Authorization': bearerAuth(user) }
    })
}

const instance = axios.create({
    baseURL: config.url.API_BASE_URL
})

instance.interceptors.request.use(function (config) {
    // If token is expired, redirect user to login
    if (config.headers.Authorization) {
        const token = config.headers.Authorization.split(' ')[1]
        const data = parseJwt(token)
        if (Date.now() > data.exp * 1000) {
            window.location.href = "/customerZone/login"
        }
    }
    return config
}, function (error) {
    return Promise.reject(error)
})


function bearerAuth(user) {
    return `Bearer ${user.accessToken}`
}


