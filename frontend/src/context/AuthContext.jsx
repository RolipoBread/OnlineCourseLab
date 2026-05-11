import { createContext, useContext, useEffect, useState } from "react";
import { getUser, setUser, clearUser } from "../utils/auth";
import { api } from "../api/axios";

const AuthContext = createContext();

export function AuthProvider({ children }) {

    const [user, setUserState] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const storedUser = getUser();
        setUserState(storedUser);
        setLoading(false);
    }, []);

    const loginUser = (userData) => {
        setUserState(userData);
        setUser(userData);
    };

    const logoutUser = () => {
        setUserState(null);
        clearUser();
    };

    const refreshUser = async (id) => {
        try {
            const res = await api.get(`/users/${id}`);

            setUserState(res.data);
            setUser(res.data);

            return res.data;
        } catch (e) {
            console.error("refreshUser failed", e);
        }
    };

    return (
        <AuthContext.Provider value={{
            user,
            loading,
            loginUser,
            logoutUser,
            refreshUser
        }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);