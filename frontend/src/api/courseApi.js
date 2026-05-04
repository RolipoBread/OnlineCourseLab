import { api } from "./axios";

/* =========================
   COURSES
========================= */

export const getCourses = (params) => {
    return api.get("/courses", { params });
};

export const getCoursesByCategory = (category) =>
    api.get("/courses", { params: { category } });

export const getCourseById = (id) => {
    return api.get(`/courses/${id}`);
};

/* =========================
   LESSONS
========================= */

export const getLessonsByCourse = (courseId, page = 0, size = 5) => {
    return api.get(`/lessons/course/${courseId}`, {
        params: { page, size }
    });
};