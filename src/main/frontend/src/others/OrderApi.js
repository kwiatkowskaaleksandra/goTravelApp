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
    getAccommodations,
    getCountries,
    getCitiesByIdCountry,
    getTripsByFilters,
    getTripById,
    getOpinionsByIdTrip,
    getAttractions,
    getSearchedTrips,
    postNewOpinion,
    deleteOpinion,
    getAllTypeOfRoom,
    postNewReservation,
    getReservationByUser,
    postReservationsTypOfRooms,
    putUserUpdate,
    putUserPasswordUpdate,
    deleteUser,
    getAllAttractions,
    postOwnOffer,
    postOwnOfferTypOfRooms,
    postAttractionOwnOffer,
    getOwnOffersByUsername,
    deleteOwnOffer,
    deleteReservation,
}

function deleteReservation(idReservation) {
    return instance.delete("/api/reservations/deleteReservation/" + idReservation)
}

function deleteOwnOffer(idOwnOffer) {
    return instance.delete("/api/ownOffer/deleteOwnOffer/" + idOwnOffer)
}

function getOwnOffersByUsername(username) {
    return instance.get("/api/ownOffer/getByUsername/" + username);
}

function postAttractionOwnOffer(attractions) {
    return instance.post("/api/ownOffer/addOwnOfferAttractions", attractions, {
        headers: {
            'Content-type': 'application/json'
        }
    });
}

function postOwnOfferTypOfRooms(idTypeOfRoom, ownOfferTypeOfRoom) {
    return instance.post("/api/ownOfferTypOfRooms/addOwnOfferTypeOfRooms/" + idTypeOfRoom, ownOfferTypeOfRoom, {
        headers: {
            'Content-type': 'application/json'
        }
    });
}

function postOwnOffer(username, ownOffer) {
    return instance.post("/api/ownOffer/addOwnOffer/" + username, ownOffer, {
        headers: {
            'Content-type': 'application/json'
        }
    });
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

function deleteUser(username) {
    return instance.delete("/api/users/deleteUser/" + username)
}

function putUserPasswordUpdate(username, passwords) {
    return instance.put("/api/users/updatePassword/" + username, passwords, {
        headers: {
            'Content-type': 'application/json'
        }
    });
}

function putUserUpdate(username, userDetails) {
    return instance.put("/api/users/update/" + username, userDetails, {
        headers: {
            'Content-type': 'application/json'
        }
    });
}

function postReservationsTypOfRooms(idTypeOfRoom, reservationTypeOfRoom) {
    return instance.post("/api/reservationsTypOfRooms/addReservationsTypOfRooms/" + idTypeOfRoom, reservationTypeOfRoom, {
        headers: {
            'Content-type': 'application/json'
        }
    });
}

function postNewReservation(username, idTrip, reservation) {
    return instance.post("/api/reservations/addReservation/" + username + '/' + idTrip, reservation, {
        headers: {
            'Content-type': 'application/json'
        }
    });
}

function getAllTypeOfRoom() {
    return instance.get("/api/typeOfRoom/all")
}

function deleteOpinion(idOpinion) {
    return instance.delete("/api/opinions/deleteOpinion/" + idOpinion)
}

function postNewOpinion(idUser, idTrip, opinion) {
    return instance.post("/api/opinions/addOpinion/" + idUser + "/" + idTrip, opinion, {
        headers: {
            'Content-type': 'application/json'
        }
    })
}

function getSearchedTrips(idCountry, typeOfTransport, minDays, maxDays) {
    return instance.get('/api/trips/findByValues/' + idCountry + '/' + typeOfTransport + '/' + minDays + '/' + maxDays)
}

function getAttractions(idTrip) {
    return instance.get("/api/attractions/" + idTrip)
}

function getTripsAndPhotosByTypeOfTrip(typeOfTrip) {
    return instance.get("/api/photos/" + typeOfTrip)
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
    return instance.post('/gotravel/authenticate', {username, password}, {
        headers: {'Content-type': 'application/json'}
    })
}

function signup(user) {
    return instance.post('/gotravel/signup', user, {
        headers: {'Content-type': 'application/json'}
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


