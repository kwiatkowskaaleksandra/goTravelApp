import {config} from '../Constants'

import {parseJwt} from "./Helpers";
import axios from "axios";

export const orderApi = {
    authenticate,
    signup,
    getUserInfo,
    getTrips,
    getPhotos,
    getTransports,
    getCountries,
    getTripsByTransport,
    getTripsByFilters,
    getTripById,
    getOpinionsByIdTrip,
    getTripsAndPhotosByTypeOfTrip,
    getAttractions,
    getSearchedTrips,
    postNewOpinion,
    deleteOpinion
}

function deleteOpinion(idOpinion){
    return instance.delete("/api/opinions/deleteOpinion/"+idOpinion)
}

function postNewOpinion(idUser, idTrip, opinion){
    return instance.post("/api/opinions/addOpinion/"+idUser+"/"+idTrip, opinion,{
        headers: {
            'Content-type': 'application/json'
        }
    })
}

function getSearchedTrips(idCountry, typeOfTransport, minDays, maxDays){
    return instance.get('/api/trips/findByValues/'+idCountry+'/'+typeOfTransport+'/'+minDays+'/'+maxDays)
}

function getAttractions(idTrip){
    return instance.get("/api/attractions/"+idTrip)
}

function getTripsAndPhotosByTypeOfTrip(typeOfTrip){
    return instance.get("/api/photos/"+typeOfTrip)
}

function getOpinionsByIdTrip(idTrip){
    return instance.get("/api/opinions/"+idTrip)
}

function getTripById(idTrip){
    return instance.get("/api/trips/"+idTrip)
}

function getTrips(typeOfTrip){
    return instance.get("/api/trips/all/"+typeOfTrip)
}

function getPhotos(idTrip){
    return instance.get("/api/photos/"+idTrip);
}

function getCountries(){
    return instance.get("/api/country/all");
}

function getTransports(){
    return instance.get("/api/transport/all");
}

function getTripsByFilters(typeOfTrip, idCountry, typeOfTransport, minPrice, maxPrice, minDays, maxDays){
    return instance.get('/api/trips/findByFilters/'+typeOfTrip+'/'+idCountry+'/'+typeOfTransport+'/'+minPrice+'/'+maxPrice+'/'+minDays+'/'+maxDays)
}

function getTripsByTransport(typeOfTrip,typeOfTransport){
    return instance.get('/api/trips/all/'+typeOfTrip+'/'+typeOfTransport)
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


