import { api } from "./axios";

export const getTeachers = () => {
    return api.get("/users/teachers");
};