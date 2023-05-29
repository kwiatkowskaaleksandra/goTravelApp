import {config} from '../Constants'

import {parseJwt} from "./Helpers";
import axios from "axios";

export const orderApi = {
    authenticate,
    getUserInfo,
    getTrips,
    getPhotos,
    getTransports,
    getAccommodations,
    getCountries,
    getCitiesByIdCountry,
    getTripsByFilters,
    getTripById,
    getOpinionsByIdTrip,
    getAttractions,
    getSearchedTrips,
    getAllTypeOfRoom,
    getReservationByUser,
    getAllAttractions,
    getOwnOffersByUsername,
    csrf
}

function csrf(){
    return instance.get("/csrf", {
        withCredentials: true
    })
}

function getOwnOffersByUsername(username) {
    return instance.get("/api/ownOffer/getByUsername/" + username);
}

function getAllAttractions() {
    return instance.get("/api/attractions/all");
}

function getAccommodations() {
    return instance.get("/api/accommodations/all");
}

function getCitiesByIdCountry(idCountry) {
    return instance.get("/api/cities/all/" + idCountry);
}

function getReservationByUser(username) {
    return instance.get("/api/reservations/getReservationByUser/" + username);
}

function getAllTypeOfRoom() {
    return instance.get("/api/typeOfRoom/all")
}

function getSearchedTrips(idCountry, typeOfTransport, minDays, maxDays) {
    return instance.get('/api/trips/findByValues/' + idCountry + '/' + typeOfTransport + '/' + minDays + '/' + maxDays)
}

function getAttractions(idTrip) {
    return instance.get("/api/attractions/" + idTrip)
}

function getOpinionsByIdTrip(idTrip) {
    return instance.get("/api/opinions/" + idTrip)
}

function getTripById(idTrip) {
    return instance.get("/api/trips/" + idTrip)
}

function getTrips(typeOfTrip) {
    return instance.get("/api/trips/all/" + typeOfTrip)
}

function getPhotos(idTrip) {
    return instance.get("/api/photos/" + idTrip);
}

function getCountries() {
    return instance.get("/api/country/all");
}

function getTransports() {
    return instance.get("/api/transport/all");
}

function getTripsByFilters(typeOfTrip, idCountry, typeOfTransport, minPrice, maxPrice, minDays, maxDays) {
    return instance.get('/api/trips/findByFilters/' + typeOfTrip + '/' + idCountry + '/' + typeOfTransport + '/' + minPrice + '/' + maxPrice + '/' + minDays + '/' + maxDays)
}

function authenticate(username, password) {
    return instance.post('/api/authenticate', {username, password}, {
        headers: { 'Content-type': 'application/json'}
    })
}

function getUserInfo(user) {
    return instance.get('/api/users/me', {
        headers: {'Authorization': bearerAuth(user)}
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


