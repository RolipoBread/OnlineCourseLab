import { createContext, useContext, useEffect, useState } from "react";
import { getUser, setUser, clearUser } from "../utils/auth";

const AuthContext = createContext();

export function AuthProvider({ children }) {

    const [user, setUserState] = useState(null);
    const [loading, setLoading] = useState(true);

    // загрузка при старте приложения
    useEffect(() => {
        const storedUser = getUser();
        setUserState(storedUser);
        setLoading(false);
    }, []);

    // LOGIN / UPDATE USER
    const loginUser = (userData) => {
        setUserState(userData);
        setUser(userData); // localStorage sync
    };

    // LOGOUT
    const logoutUser = () => {
        setUserState(null);
        clearUser();
    };

    // REFRESH USER (ВАЖНО для courses)
    const refreshUser = async (id) => {
        try {
            const res = await fetch(`https://onlinecourselab.onrender.com/users/${id}`);
            const data = await res.json();

            setUserState(data);
            setUser(data);

            return data;
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