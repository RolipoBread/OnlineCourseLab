const USER_KEY = "user";

export const getUser = () => {
    try {
        const user = localStorage.getItem(USER_KEY);
        return user ? JSON.parse(user) : null;
    } catch (e) {
        console.error("Failed to parse user", e);
        return null;
    }
};

export const setUser = (user) => {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
};

export const clearUser = () => {
    localStorage.removeItem(USER_KEY);
};