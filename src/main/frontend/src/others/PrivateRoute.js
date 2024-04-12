import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from "./AuthContext";

function PrivateRoute({ children, allowedRoles }) {
    const { userIsAuthenticated, getUser } = useAuth();
    const [isAuthenticated, setIsAuthenticated] = useState(null);
    const [roles, setRoles] = useState([])

    useEffect(() => {
        const checkAuthentication = async () => {
            const isAuthenticated = await userIsAuthenticated();

            if (isAuthenticated) {
                const user = await getUser()
                setRoles(user.roles)
            }

            setIsAuthenticated(isAuthenticated);
        };

        checkAuthentication();
    }, [userIsAuthenticated, getUser]);

    if (isAuthenticated === null) {
        return <div className="spinner-grow text-primary" role="status">
            <span className="sr-only" style={{marginLeft: '40px', background: 'transparent'}}>Loading...</span>
        </div>;
    }

    const hasRequiredRole = roles.some(role => allowedRoles.includes(role));

    return (isAuthenticated && hasRequiredRole) ? children : <Navigate to="/customerZone/login"/>;
}

export default PrivateRoute;
