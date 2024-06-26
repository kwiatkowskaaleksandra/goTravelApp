import {config} from '../Constants'

import {parseJwt} from "./JWT";
import axios from "axios";

export const goTravelApi = {
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
    getAllAttractions,
    getOwnOffersByUsername,
    countTrips,
    signup,
    login,
    refreshToken,
    signout,
    csrf,
    updateUserData,
    changePassword,
    deleteAccount,
    changeOf2FAInclusion,
    verify2FACode,
    isUsing2FA,
    createReservation,
    getTotalPriceOwnOffer,
    createOwnOffer,
    updatePaymentStatus,
    addOpinion,
    deleteOpinion,
    countOpinionsById,
    countOpinionsAndStars,
    verifyRegisterLink,
    sendConfirmationNewEmail,
    confirmEmail,
    sendConfirmationEmail,
    forgotPassword,
    resetPassword,
    getActiveOrders,
    deleteReservation,
    generateInvoice,
    getAllInsurances,
    getUserTripPreferences,
    getRecommendation,
    saveUserPreferences,
    getMostBookedTrips,
    getFavoritesTrips,
    addToFavorites,
    removeTripFromFavorites,
    getAllTypeOfTrips,
    saveNewTrip,
    validateTrip,
    deleteTheOffer,
    getAllActiveReservationNotAccepted,
    changeAcceptStatus,
    getAllActiveOwnOffersNotAccepted
}

function changeAcceptStatus(user, token, idOffer, acceptStatus, mode) {
    return instance.put("/api/" + mode + "/changeAcceptStatus", null, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'Authorization': bearerAuth(user),
            'X-CSRF-TOKEN': token,
        },
        params: {
            idOffer: idOffer,
            acceptStatus: acceptStatus
        }
    })
}

function getAllActiveOwnOffersNotAccepted(user, page, size) {
    return instance.get("/api/ownOffer/getAllActiveOwnOffersNotAccepted", {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'Authorization': bearerAuth(user)
        },
        params: {
            page: page,
            size: size
        }
    })
}

function getAllActiveReservationNotAccepted(user, page, size) {
    return instance.get("/api/reservations/getAllActiveReservationNotAccepted", {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'Authorization': bearerAuth(user)
        },
        params: {
            page: page,
            size: size
        }
    })
}

function deleteTheOffer(idTrip, user, token) {
    return instance.delete("/api/trips/deleteTheOffer/" + idTrip, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function validateTrip(trip, user, token) {
    return instance.post("/api/trips/validate", trip, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function saveNewTrip(trip, user, token) {
    return instance.post("/api/trips/saveNewTrip", trip, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function getAllTypeOfTrips() {
    return instance.get("/api/typeOfTrip/getAllTypeOfTrips")
}

function removeTripFromFavorites(idTrip, user, token) {
    return instance.delete("/api/favoriteTrips/removeTripFromFavorites", {
        params: {
            idTrip: idTrip
        },
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function addToFavorites(idTrip, user, token) {
    return instance.post("/api/favoriteTrips/addToFavorites", null,{
        params: {
            idTrip: idTrip
        },
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function getFavoritesTrips(user) {
    return instance.get("/api/favoriteTrips/getFavoritesTrips", {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': bearerAuth(user)
        }
    })
}

function getMostBookedTrips() {
    return instance.get("/api/trips/mostBookedTrips")
}

function saveUserPreferences(user, preferences, token) {
    return instance.post("/api/userTripPreferences/savePreferences", preferences, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function getRecommendation(user, preferences, token) {
    return instance.post("/api/trips/tripRecommendation", preferences, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function getUserTripPreferences(user) {
    return instance.get("/api/userTripPreferences/getPreferences", {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': bearerAuth(user)
        }
    })
}

function getAllInsurances() {
    return instance.get("/api/insurance/all")
}

function generateInvoice(user, idReservation, reservationOrigin) {
    return instance.get("/api/" + reservationOrigin + '/getInvoice/' + idReservation, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': bearerAuth(user)
        },
        responseType: 'blob'
    })
}

function deleteReservation(user, token, idReservation, reservationOrigin) {
    return instance.delete('/api/' + reservationOrigin + '/deleteReservation/' + idReservation, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function getActiveOrders(user, reservationOrigin, period) {
    return instance.get('/api/' + reservationOrigin + '/getReservationActiveOrders/'+period, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': bearerAuth(user)
        }
    })
}

function resetPassword(passwordParams, email, token) {
    return instance.put("/api/users/resetPassword", passwordParams, {
        params: {
            email: email
        },
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
        }
    })
}

function forgotPassword(email) {
    return instance.get("/gotravel/forgotPassword", {
        params: {
            email: email
        }
    })
}

function sendConfirmationEmail(username) {
    return instance.get('/mail/sendConfirmationEmail/' + username)
}

function confirmEmail(newEmail, username) {
    return instance.put('/mail/confirmEmail', null, {
        params: {
            newEmail: newEmail,
            username: username
        },
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

function sendConfirmationNewEmail(oldEmail, newEmail) {
    return instance.get('/mail/sendConfirmationNewEmail/' + oldEmail, {
        params: {
            newEmail: newEmail
        }
    })
}

function verifyRegisterLink(code) {
    return instance.get("/gotravel/verifyRegisterLink", {
        params: {
            code: code
        }
    })
}

function countOpinionsAndStars(idTrip) {
    return instance.get("/api/opinions/countOpinionsAndStars" ,{
        params: {
            idTrip: idTrip
        }
    })
}

function deleteOpinion(user, token, idOpinion) {
    return instance.delete("/api/opinions/deleteOpinion/" + idOpinion, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function addOpinion(user, token, opinion) {
    return instance.post("/api/opinions/addOpinion", opinion, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function updatePaymentStatus(user, token, type, idOffer) {
    return instance.put("api/"+type+"/updatePaymentStatus", null, {
        params:{
          "idOffer": idOffer
        },
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function createOwnOffer(user, token, ownOffer) {
    return instance.post("/api/ownOffer/createOwnOffer", ownOffer, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function getTotalPriceOwnOffer(user, token, ownOffer) {
    return instance.post("/api/ownOffer/getTotalPrice", ownOffer, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function createReservation(user, token, reservation) {
    return instance.post("/api/reservations/addReservation", reservation, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function isUsing2FA(username) {
    return instance.get('/gotravel/isUsing2FA', {
        params: {
            username: username
        }
    })
}

function verify2FACode(token, userTotpRequest) {
    return instance.post("/gotravel/verify2FACode", userTotpRequest, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        }
    })
}

function changeOf2FAInclusion(token, user, twoFactorAuthenticationEnable) {
    return instance.put("/api/users/changeOf2FAInclusion", null, {
        params: {
            twoFactorAuthenticationEnable: twoFactorAuthenticationEnable
        },
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function deleteAccount(token, user) {
    return instance.put("/api/users/deleteUser", null,{
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function changePassword(passwordRequest, token, user) {
    return instance.put("/api/users/updatePassword", passwordRequest, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function updateUserData(userDetails, token, user) {
    return instance.put("/api/users/updateUserData", userDetails, {
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function csrf(){
    return instance.get("/csrf", {
        withCredentials: true
    })
}

function signout(user, token) {
    return instance.post("/gotravel/signout", null,{
        withCredentials: true,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
            'Authorization': bearerAuth(user)
        }
    })
}

function refreshToken(token) {
    return instance.post("gotravel/refreshToken", token, {
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json'
        }
    })
}

function login(loginRequest) {
    return instance.post("/gotravel/authenticate", loginRequest, {
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
        }
    })
}

function signup(user) {
    return instance.post("/gotravel/signup", user, {
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
        }
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

function getAllTypeOfRoom() {
    return instance.get("/api/typeOfRoom/all")
}

function getSearchedTrips(idCountry, typeOfTransport, minDays, maxDays) {
    return instance.get('/api/trips/findByValues/' + idCountry + '/' + typeOfTransport + '/' + minDays + '/' + maxDays)
}

function getAttractions(idTrip) {
    return instance.get("/api/attractions/" + idTrip)
}

function getOpinionsByIdTrip(idTrip, sortType,  page, size) {
    return instance.get("/api/opinions/" + idTrip, {
        params: {
            sortType: sortType,
            page: page,
            size: size
        }
    })
}

function getTripById(idTrip, selectedLang) {
    return instance.get("/api/trips/" + idTrip + "/" + selectedLang)
}

function getTrips(typeOfTrip, page, size) {
    return instance.get("/api/trips/all/" + typeOfTrip, {
        params: {
            page: page,
            size: size
        }
    })
}

function countTrips(typeOfTrip) {
    return instance.get("/api/trips/countTrips/" + typeOfTrip)
}

function countOpinionsById(idTrips) {
    return instance.get("/api/opinions/countOpinions/" + idTrips)
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

function getTripsByFilters(tripFilteringRequest) {
    return instance.get('/api/trips/findByFilters', {
        params: tripFilteringRequest
    })
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
        if (data && Date.now() > data.exp * 1000) {
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


