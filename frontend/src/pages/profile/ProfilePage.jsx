import { useAuth } from "../../context/AuthContext";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../../styles/profile.css";
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

    const reloadUser = async () => {
        const res = await fetch(`http://localhost:8080/users/${user.id}`);
        const data = await res.json();
        loginUser({ ...data });
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

    const handleSave = async () => {
        try {
            const payload = {
                name,
                email,
                password: password && password.trim() !== "" ? password : user.password,
                role: user.role
            };

            const res = await fetch(`http://localhost:8080/users/${user.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            const updatedUser = await res.json();
            loginUser(updatedUser);

            setShowEditProfile(false);
            setPassword("");
        } catch (e) {
            console.error("Ошибка обновления профиля:", e);
        }
    };

    const handleAvatarChange = async (file) => {
        try {
            const res = await uploadAvatar(user.id, file);
            loginUser(res.data);
        } catch (e) {
            console.error(e);
        }
    };

    const handleCreateCourse = async () => {
        try {
            const createRes = await fetch("http://localhost:8080/courses", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    title: newCourse.title,
                    description: newCourse.description,
                    author: user.name,
                    price: Number(newCourse.price),
                    lessonCount: Number(newCourse.lessonCount),
                    categoryId: newCourse.categoryId ? Number(newCourse.categoryId) : null
                })
            });

            const createdCourse = await createRes.json();

            await fetch(
                `http://localhost:8080/users/${user.id}/courses/${createdCourse.id}`,
                { method: "PUT" }
            );

            const res = await fetch(`http://localhost:8080/users/${user.id}`);
            loginUser(await res.json());

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

    const handleDeleteCourse = async (courseId) => {
        if (!window.confirm("Удалить курс?")) return;

        try {
            await fetch(`http://localhost:8080/courses/${courseId}`, {
                method: "DELETE"
            });

            await fetch(
                `http://localhost:8080/users/${user.id}/courses/${courseId}`,
                { method: "DELETE" }
            );

            const res = await fetch(`http://localhost:8080/users/${user.id}`);
            loginUser(await res.json());

        } catch (e) {
            console.error("Ошибка удаления курса:", e);
        }
    };

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

            await fetch(`http://localhost:8080/courses/${editingCourse.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            const res = await fetch(`http://localhost:8080/users/${user.id}`);
            loginUser(await res.json());

            setShowEditCourse(false);
            setEditingCourse(null);

        } catch (e) {
            console.error("Ошибка обновления курса:", e);
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
                            <img src={`http://localhost:8080${user.avatarUrl}`} />
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

            <div className="profile-courses">

                <div className="courses-header">
                    <h3>Мои курсы</h3>

                    {user.role === "TEACHER" && (
                        <button
                            className="add-course-btn"
                            onClick={() => setShowCreateCourse(true)}
                        >
                            ＋
                        </button>
                    )}
                </div>

                {user.courses?.length > 0 ? (
                    <div className="course-list">

                        {user.courses.map(course => (

                            <div key={course.id} className="course-hover-wrapper">

                                <div className="course-actions">
                                    {user.role === "TEACHER" && (
                                        <button onClick={() => {
                                            setEditingCourse(course);
                                            setShowEditCourse(true);
                                        }}>
                                            ✏
                                        </button>
                                    )}

                                    <button onClick={() => handleDeleteCourse(course.id)}>
                                        🗑
                                    </button>
                                </div>

                                <Link to={`/courses/${course.id}`} className="course-card-link">

                                    <div className="profile-course-card">

                                        <div
                                            className="course-image"
                                            style={{
                                                background: `linear-gradient(135deg, ${course.categoryColor || "#3b82f6"}, #0f172a)`
                                            }}
                                        >
                                            <div className="course-image-overlay">
                                                <div className="course-title-on-image">
                                                    {course.title}
                                                </div>
                                            </div>
                                        </div>

                                        <div className="course-content">
                                            <div>{course.author}</div>
                                            <div className="course-desc">{course.description}</div>
                                        </div>
                                    </div>

                                </Link>

                            </div>

                        ))}

                    </div>
                ) : (
                    <div className="empty">Нет курсов</div>
                )}
            </div>

            {showEditProfile && (
                <div className="modal-overlay" onClick={() => setShowEditProfile(false)}>
                    <div className="modal" onClick={(e) => e.stopPropagation()}>

                        <h3>Редактирование профиля</h3>

                        <input value={name} onChange={(e) => setName(e.target.value)} />
                        <input value={email} onChange={(e) => setEmail(e.target.value)} />
                        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />

                        <div className="modal-actions">
                            <button onClick={handleSave}>Сохранить</button>
                            <button onClick={() => setShowEditProfile(false)}>Отмена</button>
                        </div>

                    </div>
                </div>
            )}

            {showCreateCourse && (
                <div
                    className="modal-overlay"
                    onClick={() => setShowCreateCourse(false)}
                >
                    <div className="modal" onClick={(e) => e.stopPropagation()}>

                        <h3>Создать курс</h3>

                        <label>Название</label>
                        <input
                            value={newCourse.title}
                            onChange={(e) =>
                                setNewCourse({ ...newCourse, title: e.target.value })
                            }
                            placeholder="Введите название курса"
                        />

                        <label>Описание</label>
                        <textarea
                            value={newCourse.description}
                            onChange={(e) =>
                                setNewCourse({ ...newCourse, description: e.target.value })
                            }
                            placeholder="Описание курса"
                        />

                        <label>Категория</label>
                        <select
                            value={newCourse.categoryId}
                            onChange={(e) =>
                                setNewCourse({ ...newCourse, categoryId: e.target.value })
                            }
                        >
                            <option value="">Выберите категорию</option>
                            {categories.map((c) => (
                                <option key={c.id} value={c.id}>
                                    {c.name}
                                </option>
                            ))}
                        </select>

                        <label>Автор</label>
                        <input
                            value={user.name}
                            disabled
                        />

                        <label>Цена</label>
                        <input
                            type="number"
                            value={newCourse.price}
                            onChange={(e) =>
                                setNewCourse({ ...newCourse, price: e.target.value })
                            }
                            placeholder="0"
                        />

                        <label>Количество уроков</label>
                        <input
                            type="number"
                            value={newCourse.lessonCount}
                            onChange={(e) =>
                                setNewCourse({ ...newCourse, lessonCount: e.target.value })
                            }
                            min="1"
                        />

                        <div className="modal-actions">

                            <button onClick={handleCreateCourse}>
                                Создать
                            </button>

                            <button onClick={() => setShowCreateCourse(false)}>
                                Отмена
                            </button>

                        </div>

                    </div>
                </div>
            )}

            {showEditCourse && (
                <div className="modal-overlay" onClick={() => setShowEditCourse(false)}>
                    <div className="modal" onClick={(e) => e.stopPropagation()}>

                        <h3>Редактировать курс</h3>

                        <label>Название</label>
                        <input
                            value={editingCourse?.title ?? ""}
                            onChange={(e) =>
                                setEditingCourse({ ...editingCourse, title: e.target.value })
                            }
                        />

                        <label>Описание</label>
                        <textarea
                            value={editingCourse?.description ?? ""}
                            onChange={(e) =>
                                setEditingCourse({ ...editingCourse, description: e.target.value })
                            }
                        />

                        <label>Цена</label>
                        <input
                            type="number"
                            value={editingCourse?.price ?? ""}
                            onChange={(e) =>
                                setEditingCourse({ ...editingCourse, price: e.target.value })
                            }
                        />

                        <label>Уроки</label>
                        <input
                            type="number"
                            value={editingCourse?.lessonCount ?? ""}
                            onChange={(e) =>
                                setEditingCourse({ ...editingCourse, lessonCount: e.target.value })
                            }
                        />

                        <label>Категория</label>
                        <select
                            value={editingCourse?.categoryId ?? ""}
                            onChange={(e) =>
                                setEditingCourse({
                                    ...editingCourse,
                                    categoryId: e.target.value
                                })
                            }
                        >
                            <option value="">Выберите</option>
                            {categories.map(c => (
                                <option key={c.id} value={c.id}>
                                    {c.name}
                                </option>
                            ))}
                        </select>

                        <div className="modal-actions">
                            <button onClick={handleUpdateCourse}>
                                Сохранить
                            </button>
                            <button onClick={() => setShowEditCourse(false)}>
                                Отмена
                            </button>
                        </div>

                    </div>
                </div>
            )}

        </div>
    );
}