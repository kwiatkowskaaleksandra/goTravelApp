import './App.css';
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import { BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/home/Home"
import CustomerZoneRegistration from "./pages/home/CustomerZoneRegistration";
import CustomerHome from "./pages/user/CustomerHome";
import {AuthProvider} from "./others/AuthContext";
import PrivateRoute from "./others/PrivateRoute";
import Login from "./pages/home/CustomerZoneLogin";

function App() {
  return (
      <AuthProvider>
           <Router>

               <div>

                   <Routes>
                       <Route path='/' element={<Home />} />
                       <Route path='/customerZone/login' element={<Login />} />
                       <Route path={"/customerZone/registration"} element={<CustomerZoneRegistration />}/>
                       <Route path={"/customerHome"} element={<PrivateRoute><CustomerHome /></PrivateRoute>}/>
                   </Routes>

               </div>
           </Router>
</AuthProvider>
  );
}

export default App;
