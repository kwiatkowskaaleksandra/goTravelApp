import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from "./AuthContext";

function PrivateRoute({ children }) {
    const { userIsAuthenticated } = useAuth();
    const [isAuthenticated, setIsAuthenticated] = useState(null);

    useEffect(() => {
        const checkAuthentication = async () => {
            const isAuthenticated = await userIsAuthenticated();
            setIsAuthenticated(isAuthenticated);
        };

        checkAuthentication();
    }, [userIsAuthenticated]);

    if (isAuthenticated === null) {
        return <div className="spinner-grow text-primary" role="status">
            <span className="sr-only" style={{marginLeft: '40px'}}>Loading...</span>
        </div>;
    }

    return isAuthenticated ? children : <Navigate to="/customerZone/login"/>;
}

export default PrivateRoute;
