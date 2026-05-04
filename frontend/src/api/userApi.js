import { api } from "./axios";

// загрузка аватара
export const uploadAvatar = (userId, file) => {
    const formData = new FormData();
    formData.append("file", file);

    return api.post(`/users/${userId}/avatar`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
};