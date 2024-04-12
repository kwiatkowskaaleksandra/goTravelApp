import 'bootstrap/dist/css/bootstrap.min.css';
import React, {useEffect, useState} from 'react'
import {useAuth} from "../../others/AuthContext";
import './NavigationBar.css'
import NavigationBarEmployee from "../employee/NavigationBarEmployee";
import NavigationBarClient from "../client/NavigationBarClient";

const languages = [
    {value: 'pl', text: 'Polish', code: 'PL'},
    {value: 'en', text: 'English', code: 'GB'}
]

function NavigationBar() {

    const [lang, setLang] = useState('')
    const [roles, setRoles] = useState([])
    const [userName, setUserName] = useState('');

    useEffect(() => {
        const storedLang = localStorage.getItem('selectedLang')
        if (storedLang) {
            setLang(storedLang);
        }
    }, []);

    const handleChangeLang = (e) => {
        const selectedLang = languages.find(lang => lang.code === e)
        setLang(selectedLang.value);
        localStorage.setItem('selectedLang', selectedLang.code);
        let loc = "http://localhost:3000/";
        window.location.replace(loc + "?lng=" + selectedLang.value);
    }

    const {getUser, userIsAuthenticated, userLogout} = useAuth()
    const logout = async () => {
        await userLogout()
    }

    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const checkAuthentication = async () => {
            console.log("Checking authentication...");
            const isAuthenticated = await userIsAuthenticated();
            console.log("Authentication:", isAuthenticated);
            setIsAuthenticated(isAuthenticated);
        };

        checkAuthentication();
    },);

    useEffect(() => {
        const getUserNameAndRoles = () => {
            const user = getUser();
            if (user) {
                setRoles(user.roles);
                setUserName(user.data.sub);
            }
        };
        getUserNameAndRoles();
    }, [getUser]);

    return (

        <>
            {roles.includes('ROLE_MODERATOR') ? (
                <div>
                    <NavigationBarEmployee isAuthenticated={isAuthenticated}
                                           lang={lang}
                                           onLogout={logout}
                                           onChangeLang={handleChangeLang}
                                           username={userName}/>
                </div>
            ) : (
                <div>
                    <NavigationBarClient isAuthenticated={isAuthenticated}
                                         lang={lang}
                                         onLogout={logout}
                                         onChangeLang={handleChangeLang}
                                         username={userName}/>
                </div>
            )}
        </>
    )
}

export default NavigationBar
