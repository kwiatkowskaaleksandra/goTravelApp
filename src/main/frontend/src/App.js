import './App.css';
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/home/Home"
import CustomerZoneRegistration from "./pages/home/CustomerZoneRegistration";
import {AuthProvider} from "./others/AuthContext";
import PrivateRoute from "./others/PrivateRoute";
import Login from "./pages/home/CustomerZoneLogin";
import LastMinute from "./pages/typeOfTrips/LastMinute";
import Promotion from './pages/typeOfTrips/Promotion'
import Exotic from './pages/typeOfTrips/Exotic'
import Cruise from './pages/typeOfTrips/Cruise'
import AllInclusive from './pages/typeOfTrips/AllInclusive'
import LongTrip from './pages/typeOfTrips/LongTrip'
import ShortTrip from './pages/typeOfTrips/ShortTrip'
import FamilyTrip from './pages/typeOfTrips/FamilyTrip'
import SeeTheOffer from "./pages/typeOfTrips/SeeTheOffer";
import SearchedTrips from "./pages/typeOfTrips/SearchedTrips";
import ReservationForm from './pages/user/ReservationForm'
import MyProfile from "./pages/user/MyProfile";
import YourOwnOffer from "./pages/user/YourOwnOffer";

function App() {
    return (
        <AuthProvider>
            <Router>

                <div>

                    <Routes>
                        <Route path='/' element={<Home/>}/>
                        <Route path='/customerZone/login' element={<Login/>}/>
                        <Route path={"/customerZone/registration"} element={<CustomerZoneRegistration/>}/>

                        <Route path={"/lastMinute"} element={<LastMinute/>}/>
                        <Route path={"/promotions"} element={<Promotion/>}/>
                        <Route path={"/exotics"} element={<Exotic/>}/>
                        <Route path={"/cruises"} element={<Cruise/>}/>
                        <Route path={"/allInclusive"} element={<AllInclusive/>}/>
                        <Route path={"/longTrips"} element={<LongTrip/>}/>
                        <Route path={"/shortTrips"} element={<ShortTrip/>}/>
                        <Route path={"/familyTrips"} element={<FamilyTrip/>}/>
                        <Route path={"/searchedTrips/*"} element={<SearchedTrips/>}/>

                        <Route path={"/lastMinute/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/promotions/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/exotics/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/familyTrips/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/longTrips/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/cruises/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/shortTrips/*"} element={<SeeTheOffer/>}/>
                        <Route path={"/allInclusive/*"} element={<SeeTheOffer/>}/>

                        <Route path={"/customerHome"} element={<PrivateRoute><Home/></PrivateRoute>}/>
                        <Route path={"/reservationForm/*"} element={<PrivateRoute><ReservationForm/></PrivateRoute>}/>
                        <Route path={"/myProfile"} element={<PrivateRoute><MyProfile/></PrivateRoute>}/>
                        <Route path={"/yourOwnOffer"} element={<PrivateRoute><YourOwnOffer/></PrivateRoute>}/>

                    </Routes>

                </div>
            </Router>
        </AuthProvider>
    );
}

export default App;
