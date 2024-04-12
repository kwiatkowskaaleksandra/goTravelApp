import './App.css';
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/home/Home"
import CustomerZoneRegistration from "./pages/home/CustomerZoneRegistration";
import {AuthProvider} from "./others/AuthContext";
import Login from "./pages/home/CustomerZoneLogin";
import AllOffers from "./pages/client/typeOfTrips/AllOffers";
import SeeTheOffer from "./pages/client/typeOfTrips/SeeTheOffer";
import ReservationForm from './pages/client/ReservationForm'
import YourOwnOffer from "./pages/client/YourOwnOffer";
import Contact from "./others/footerLinks/Contact";
import About from "./others/footerLinks/About";
import Airlines from "./others/footerLinks/Airlines";
import Insurance from "./others/footerLinks/Insurance";
import i18next from "i18next";
import HttpBackend from "i18next-http-backend";
import LanguageDetector from "i18next-browser-languagedetector";
import { initReactI18next } from "react-i18next";
import Settings from "./pages/client/settings/Settings";
import SettingsMyProfile from "./pages/client/settings/SettingsMyProfile";
import SettingsInvoices from "./pages/client/settings/SettingsInvoices";
import OAuth2Redirect from "./others/OAuth2Redirect";
import VerifyRegisterLink from "./pages/client/fromMails/VerifyRegisterLink";
import ConfirmNewEmail from "./pages/client/fromMails/ConfirmNewEmail";
import ChangeEmail from "./pages/client/fromMails/ChangeEmail";
import ForgotPassword from "./pages/client/fromMails/ForgotPassword";
import ResetPassword from "./pages/client/fromMails/ResetPassword";
import TripRecommendation from "./pages/client/typeOfTrips/TripRecommendation";
import SettingsFavorites from "./pages/client/settings/SettingsFavorites";
import PrivateRoute from "./others/PrivateRoute";
import AccountSettings from "./pages/employee/AccountSettings";
import TourOffersManagement from "./pages/employee/TourOffersManagement";
import ReservationManagement from "./pages/employee/ReservationManagement";

const apiKeys = {
    goTravelNamespace1: "VY4mV8mgc2hiYbiInxdPaQ",
    goTravelNamespace2: "mqTxtTc1WY7iJiBT6wFjbA",
    goTravelNamespace3: "zi8ssd9snl1UMwdXCB19Zg",
    goTravelNamespace4: "QE3fJ4uGBKNTb5lIuYdLyQ"
};

i18next
    .use(HttpBackend)
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        fallbackLng: "pl",

        ns: ["goTravelNamespace1", "goTravelNamespace2", "goTravelNamespace3", "goTravelNamespace4"],
        defaultNS: "goTravelNamespace1",

        supportedLngs: ["pl","en"],
        backend: {
            loadPath: (lng, ns) => {
                const apiKey = apiKeys[ns]
                return `https://api.i18nexus.com/project_resources/translations/${lng}/${ns}.json?api_key=${apiKey}`;
            },
        }
    })

function App() {
    return (
        <AuthProvider>
            <Router>
                <div>
                    <Routes>
                        <Route path='/' element={<Home/>}/>
                        <Route path='/customerZone/login' element={<Login/>}/>
                        <Route path={"/customerZone/registration"} element={<CustomerZoneRegistration/>}/>

                        <Route path={"/allOffers/lastMinute"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/promotions"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/exotics"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/cruises"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/allInclusive"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/longTrips"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/shortTrips"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/familyTrips"} element={<AllOffers/>}/>
                        <Route path={"/allOffers/search/*"} element={<AllOffers/>}/>

                        <Route path={"/allOffers/lastMinute/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allOffers/promotions/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allOffers/exotics/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allOffers/familyTrips/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allOffers/longTrips/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allOffers/cruises/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allOffers/shortTrips/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allOffers/allInclusive/*"} element={<SeeTheOffer/>}/>

                        <Route path={"/reservationForm/*"} element={<PrivateRoute allowedRoles={['ROLE_USER']}><ReservationForm/></PrivateRoute>}/>
                        <Route path={"/myProfile"} element={<PrivateRoute allowedRoles={['ROLE_USER']}><Settings/></PrivateRoute>}/>
                        <Route path={"/myProfile/settings"} element={<PrivateRoute allowedRoles={['ROLE_USER']}><SettingsMyProfile/></PrivateRoute>}/>
                        <Route path={"/myProfile/invoices"} element={<PrivateRoute allowedRoles={['ROLE_USER']}><SettingsInvoices/></PrivateRoute>}/>
                        <Route path={"/myProfile/favorites"} element={<PrivateRoute allowedRoles={['ROLE_USER']}><SettingsFavorites/></PrivateRoute>}/>
                        <Route path={"/yourOwnOffer"} element={<PrivateRoute allowedRoles={['ROLE_USER']}><YourOwnOffer/></PrivateRoute>}/>
                        <Route path={"/allOffers/proposedForYou"} element={<PrivateRoute allowedRoles={['ROLE_USER']}><TripRecommendation/></PrivateRoute>}/>

                        <Route path={"/contact"} element={<Contact/>}/>
                        <Route path={"/about"} element={<About/>}/>
                        <Route path={"/airlines"} element={<Airlines/>}/>
                        <Route path={"/insurance"} element={<Insurance/>}/>
                        <Route path={'/oauth2/redirect'} element={<OAuth2Redirect/>}/>

                        <Route path={'/verify'} element={<VerifyRegisterLink/>}/>
                        <Route path={'/confirm'} element={<ChangeEmail/>}/>
                        <Route path={'/confirmNewEmail'} element={<ConfirmNewEmail/>}/>
                        <Route path={'/forgotPassword'} element={<ForgotPassword/>}/>
                        <Route path={'/reset'} element={<ResetPassword/>}/>

                        <Route path={"/employee/accountSettings"} element={<PrivateRoute allowedRoles={['ROLE_MODERATOR']}><AccountSettings/></PrivateRoute>}/>
                        <Route path={"/employee/manageTourOffers"} element={<PrivateRoute allowedRoles={['ROLE_MODERATOR']}><TourOffersManagement/></PrivateRoute>}/>
                        <Route path={"/employee/reservationManagement"} element={<PrivateRoute allowedRoles={['ROLE_MODERATOR']}><ReservationManagement/></PrivateRoute>}/>

                    </Routes>

                </div>
            </Router>
        </AuthProvider>
    );
}

export default App;
