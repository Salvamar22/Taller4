import React, { useState, useMemo, useCallback } from 'react';
import userService from '../services/user.services';

const UserContext = React.createContext();
const TOKEN_KEY = 'token';

export const UserProvider = (props) => {
    const [token, setToken] = useState(undefined);

    const setTokenAll = useCallback((token) => {
        localStorage.setItem(TOKEN_KEY, token);
        setToken(token);
    }, []);

    const login = useCallback((username, password) => {
        const loginAsync = async () => {
            try {
                const tokenRes = await userService.login(username, password);
                console.log(tokenRes);
                if (tokenRes) {
                    setTokenAll(tokenRes);
                    return true;
                }
            } catch (error) {
                console.error(error);
                console.error("Error in login");
            }
            return false;

        };

        return loginAsync();
    }, [setTokenAll]);

    const logout = useCallback(() => {
        setTokenAll(undefined);
    }, [setTokenAll]);

    const value = useMemo(
        () => ({
            token: token,
            login: login,
            logout: logout,
        }),
        [token, login, logout]
    );

    return <UserContext.Provider value={value} {...props} />;
};

export const useUserContext = () => {
    const context = React.useContext(UserContext);

    if (!context) {
        throw new Error("useUserContext() must be inside of UserProvider");
    }

    return context;
};

const getToken = () => localStorage.getItem(TOKEN_KEY);
