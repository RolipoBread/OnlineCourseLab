import { useAuth } from "../../context/AuthContext";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../../styles/profile.css";
import { api } from "../../api/axios";
import { uploadAvatar } from "../../api/userApi";
import { getCategories } from "../../api/categoryApi";

export default function ProfilePage() {

    const { user, logoutUser, loginUser } = useAuth();
    const navigate = useNavigate();

    const [hover, setHover] = useState(false);

    const [showEditProfile, setShowEditProfile] = useState(false);
    const [showCreateCourse, setShowCreateCourse] = useState(false);
    const [showEditCourse, setShowEditCourse] = useState(false);

    const [editingCourse, setEditingCourse] = useState(null);

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [categories, setCategories] = useState([]);

    const [newCourse, setNewCourse] = useState({
        title: "",
        description: "",
        categoryId: "",
        lessonCount: 1,
        price: 0
    });

    // ================= LOAD USER =================
    const reloadUser = async () => {
        try {
            const res = await api.get(`/users/${user.id}`);
            loginUser(res.data);
        } catch (e) {
            console.error(e);
        }
    };

    useEffect(() => {
        if (user) {
            setName(user.name || "");
            setEmail(user.email || "");
        }
    }, [user]);

    useEffect(() => {
        loadCategories();
    }, []);

    const loadCategories = async () => {
        try {
            const res = await getCategories();
            setCategories(res.data);
        } catch (e) {
            console.error(e);
        }
    };

    if (!user) return <div className="container">Не авторизован</div>;

    // ================= SAVE PROFILE =================
    const handleSave = async () => {
        try {
            const payload = {
                name,
                email,
                password: password?.trim() ? password : user.password,
                role: user.role
            };

            const res = await api.put(`/users/${user.id}`, payload);
            loginUser(res.data);

            setShowEditProfile(false);
            setPassword("");

        } catch (e) {
            console.error(e);
        }
    };

    // ================= AVATAR =================
    const handleAvatarChange = async (file) => {
        try {
            const res = await uploadAvatar(user.id, file);
            loginUser(res.data);
        } catch (e) {
            console.error(e);
        }
    };

    // ================= CREATE COURSE =================
    const handleCreateCourse = async () => {
        try {
            const created = await api.post("/courses", {
                title: newCourse.title,
                description: newCourse.description,
                author: user.name,
                price: Number(newCourse.price),
                lessonCount: Number(newCourse.lessonCount),
                categoryId: newCourse.categoryId ? Number(newCourse.categoryId) : null
            });

            await api.put(`/users/${user.id}/courses/${created.data.id}`);

            const res = await api.get(`/users/${user.id}`);
            loginUser(res.data);

            setShowCreateCourse(false);
            setNewCourse({
                title: "",
                description: "",
                categoryId: "",
                lessonCount: 1,
                price: 0
            });

        } catch (e) {
            console.error(e);
        }
    };

    // ================= DELETE COURSE =================
    const handleDeleteCourse = async (courseId) => {
        if (!window.confirm("Удалить курс?")) return;

        try {
            await api.delete(`/courses/${courseId}`);
            await api.delete(`/users/${user.id}/courses/${courseId}`);

            const res = await api.get(`/users/${user.id}`);
            loginUser(res.data);

        } catch (e) {
            console.error(e);
        }
    };

    // ================= UPDATE COURSE =================
    const handleUpdateCourse = async () => {
        try {
            const payload = {
                title: editingCourse.title,
                description: editingCourse.description,
                author: editingCourse.author,
                price: Number(editingCourse.price),
                lessonCount: Number(editingCourse.lessonCount),
                categoryId: editingCourse.categoryId
                    ? Number(editingCourse.categoryId)
                    : null
            };

            await api.put(`/courses/${editingCourse.id}`, payload);

            const res = await api.get(`/users/${user.id}`);
            loginUser(res.data);

            setShowEditCourse(false);
            setEditingCourse(null);

        } catch (e) {
            console.error(e);
        }
    };

    return (
        <div className="container profile-page">

            <div className="profile-header">

                <div
                    className="profile-avatar-wrapper"
                    onClick={() => document.getElementById("avatarInput").click()}
                >
                    <div className="profile-avatar">
                        {user.avatarUrl ? (
                            <img src={`${import.meta.env.VITE_API_URL}${user.avatarUrl}`} />
                        ) : (
                            <span>{user.name?.charAt(0).toUpperCase()}</span>
                        )}
                        <div className="avatar-overlay">📷</div>
                    </div>

                    <input
                        id="avatarInput"
                        type="file"
                        hidden
                        onChange={(e) => {
                            const file = e.target.files[0];
                            if (file) handleAvatarChange(file);
                        }}
                    />
                </div>

                <div className="profile-info">
                    <h2>{user.name}</h2>
                    <div>{user.email}</div>
                    <div>роль: {user.role}</div>
                </div>

                <div className="profile-right">

                    <button className="edit-btn" onClick={() => setShowEditProfile(true)}>
                        Редактировать
                    </button>

                    <button
                        className="logout-btn"
                        onMouseEnter={() => setHover(true)}
                        onMouseLeave={() => setHover(false)}
                        onClick={() => {
                            logoutUser();
                            navigate("/");
                        }}
                    >
                        {hover ? "Ну и вали" : "Выйти"}
                    </button>

                </div>
            </div>

            {/* остальной JSX без изменений */}
        </div>
    );
}