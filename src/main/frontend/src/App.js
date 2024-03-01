import './App.css';
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/home/Home"
import CustomerZoneRegistration from "./pages/home/CustomerZoneRegistration";
import {AuthProvider} from "./others/AuthContext";
import PrivateRoute from "./others/PrivateRoute";
import Login from "./pages/home/CustomerZoneLogin";
import AllOffers from "./pages/typeOfTrips/AllOffers";
import SeeTheOffer from "./pages/typeOfTrips/SeeTheOffer";
import ReservationForm from './pages/user/ReservationForm'
import YourOwnOffer from "./pages/user/YourOwnOffer";
import Contact from "./others/footerLinks/Contact";
import About from "./others/footerLinks/About";
import Airlines from "./others/footerLinks/Airlines";
import Insurance from "./others/footerLinks/Insurance";
import i18next from "i18next";
import HttpBackend from "i18next-http-backend";
import LanguageDetector from "i18next-browser-languagedetector";
import { initReactI18next } from "react-i18next";
import Settings from "./pages/user/Settings";
import SettingsMyProfile from "./pages/user/SettingsMyProfile";
import SettingsInvoices from "./pages/user/SettingsInvoices";
import OAuth2Redirect from "./others/OAuth2Redirect";
import VerifyRegisterLink from "./pages/VerifyRegisterLink";
import ConfirmNewEmail from "./pages/ConfirmNewEmail";
import ChangeEmail from "./pages/ChangeEmail";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";

const apiKeys = {
    goTravelNamespace1: "VY4mV8mgc2hiYbiInxdPaQ",
    goTravelNamespace2: "mqTxtTc1WY7iJiBT6wFjbA",
    goTravelNamespace3: "zi8ssd9snl1UMwdXCB19Zg"
};

i18next
    .use(HttpBackend)
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        fallbackLng: "pl",

        ns: ["goTravelNamespace1", "goTravelNamespace2", "goTravelNamespace3"],
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

                        <Route path={"/reservationForm/*"} element={<PrivateRoute><ReservationForm/></PrivateRoute>}/>
                        <Route path={"/myProfile"} element={<PrivateRoute><Settings/></PrivateRoute>}/>
                        <Route path={"/myProfile/settings"} element={<PrivateRoute><SettingsMyProfile/></PrivateRoute>}/>
                        <Route path={"/myProfile/invoices"} element={<PrivateRoute><SettingsInvoices/></PrivateRoute>}/>
                        <Route path={"/yourOwnOffer"} element={<PrivateRoute><YourOwnOffer/></PrivateRoute>}/>

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
                    </Routes>

                </div>
            </Router>
        </AuthProvider>
    );
}

export default App;
