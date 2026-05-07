import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { getCourseById, getLessonsByCourse } from "../../api/courseApi";
import "../../styles/coursePage.css";

export default function CoursePage() {
    const { id } = useParams();
    const { user } = useAuth();

    const [course, setCourse] = useState(null);
    const [lessons, setLessons] = useState([]);

    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [showCreate, setShowCreate] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    const [editingLesson, setEditingLesson] = useState(null);

    const [viewLesson, setViewLesson] = useState(null);

    const [newLesson, setNewLesson] = useState({
        title: "",
        content: "",
        orderNumber: 1
    });

    const size = 6;

    useEffect(() => {
        loadCourse();
    }, [id]);

    useEffect(() => {
        loadLessons();
    }, [id, page]);

    const loadCourse = async () => {
        const res = await getCourseById(id);
        setCourse(res.data);
    };

    const loadLessons = async () => {
        const res = await getLessonsByCourse(id, page, size);
        setLessons(res.data.content || []);
        setTotalPages(res.data.totalPages || 0);
    };

    // ================= CREATE =================
    const handleCreateLesson = async () => {
        try {
            await fetch("http://localhost:8080/lessons", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    title: newLesson.title,
                    content: newLesson.content,
                    orderNumber: Number(newLesson.orderNumber),
                    courseId: Number(id)
                })
            });

            setShowCreate(false);
            setNewLesson({ title: "", content: "", orderNumber: 1 });
            loadLessons();

        } catch (e) {
            console.error(e);
        }
    };

    // ================= UPDATE =================
    const handleUpdateLesson = async () => {
        try {
            await fetch(`http://localhost:8080/lessons/${editingLesson.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    title: editingLesson.title,
                    content: editingLesson.content,
                    orderNumber: Number(editingLesson.orderNumber),
                    courseId: Number(id)
                })
            });

            setShowEdit(false);
            setEditingLesson(null);
            loadLessons();

        } catch (e) {
            console.error(e);
        }
    };

    // ================= DELETE =================
    const handleDeleteLesson = async (lessonId) => {
        if (!window.confirm("Удалить урок?")) return;

        try {
            await fetch(`http://localhost:8080/lessons/${lessonId}`, {
                method: "DELETE"
            });

            loadLessons();

        } catch (e) {
            console.error(e);
        }
    };

    if (!course) return <div className="container">Загрузка...</div>;

    return (
        <div className="container course-page">

            {/* HERO */}
            <div
                className="course-hero"
                style={{
                    background: `linear-gradient(135deg, ${course.categoryColor || "#3b82f6"}, #0f172a)`
                }}
            >
                <h1>{course.title}</h1>
                <p>{course.description}</p>
                <span>{course.author}</span>
            </div>

            {/* HEADER */}
            <div className="courses-header">
                <h2>Уроки</h2>

                {user?.role === "TEACHER" && (
                    <button
                        className="add-course-btn"
                        onClick={() => setShowCreate(true)}
                    >
                        ＋
                    </button>
                )}
            </div>

            {/* LESSONS */}
            <div className="lesson-grid">

                {lessons.length > 0 ? (
                    lessons.map(lesson => (
                        <div key={lesson.id} className="lesson-hover-wrapper">

                            {/* ACTIONS */}
                            {user?.role === "TEACHER" && (
                                <div className="lesson-actions">
                                    <button onClick={() => {
                                        setEditingLesson(lesson);
                                        setShowEdit(true);
                                    }}>✏</button>

                                    <button onClick={() => handleDeleteLesson(lesson.id)}>🗑</button>
                                </div>
                            )}

                            {/* CARD */}
                            <div
                                className="lesson-card"
                                onClick={() => setViewLesson(lesson)}
                                style={{ cursor: "pointer" }}
                            >
                                <div className="lesson-title">
                                    {lesson.title}
                                </div>
                            </div>

                        </div>
                    ))
                ) : (
                    <div className="empty">Уроков пока нет</div>
                )}

            </div>

            {/* PAGINATION */}
            <div className="pagination">
                <button onClick={() => setPage(p => Math.max(p - 1, 0))}>
                    Назад
                </button>

                <span>Страница {page + 1} / {totalPages}</span>

                <button onClick={() => setPage(p => p + 1)} disabled={page >= totalPages - 1}>
                    Вперёд
                </button>
            </div>

            {/* VIEW LESSON MODAL */}
            {viewLesson && (
                <div className="modal-overlay" onClick={() => setViewLesson(null)}>
                    <div className="modal" onClick={(e) => e.stopPropagation()}>

                        <h3>{viewLesson.title}</h3>

                        <div style={{ color: "#aaa", fontSize: "13px" }}>
                            Урок #{viewLesson.orderNumber}
                        </div>

                        <div style={{ marginTop: "10px", color: "#ddd" }}>
                            {viewLesson.content}
                        </div>

                        <div className="modal-actions">
                            <button onClick={() => setViewLesson(null)}>
                                Закрыть
                            </button>
                        </div>

                    </div>
                </div>
            )}

            {/* CREATE MODAL */}
            {showCreate && (
                <div className="modal-overlay" onClick={() => setShowCreate(false)}>
                    <div className="modal" onClick={(e) => e.stopPropagation()}>

                        <h3>Создать урок</h3>

                        <input
                            placeholder="Название"
                            value={newLesson.title}
                            onChange={(e) =>
                                setNewLesson({ ...newLesson, title: e.target.value })
                            }
                        />

                        <textarea
                            placeholder="Контент"
                            value={newLesson.content}
                            onChange={(e) =>
                                setNewLesson({ ...newLesson, content: e.target.value })
                            }
                        />

                        <input
                            type="number"
                            value={newLesson.orderNumber}
                            onChange={(e) =>
                                setNewLesson({ ...newLesson, orderNumber: e.target.value })
                            }
                        />

                        <div className="modal-actions">
                            <button onClick={handleCreateLesson}>Создать</button>
                            <button onClick={() => setShowCreate(false)}>Отмена</button>
                        </div>

                    </div>
                </div>
            )}

            {/* EDIT MODAL */}
            {showEdit && (
                <div className="modal-overlay" onClick={() => setShowEdit(false)}>
                    <div className="modal" onClick={(e) => e.stopPropagation()}>

                        <h3>Редактировать урок</h3>

                        <input
                            value={editingLesson?.title || ""}
                            onChange={(e) =>
                                setEditingLesson({ ...editingLesson, title: e.target.value })
                            }
                        />

                        <textarea
                            value={editingLesson?.content || ""}
                            onChange={(e) =>
                                setEditingLesson({ ...editingLesson, content: e.target.value })
                            }
                        />

                        <input
                            type="number"
                            value={editingLesson?.orderNumber || 1}
                            onChange={(e) =>
                                setEditingLesson({ ...editingLesson, orderNumber: e.target.value })
                            }
                        />

                        <div className="modal-actions">
                            <button onClick={handleUpdateLesson}>
                                Сохранить
                            </button>
                            <button onClick={() => setShowEdit(false)}>
                                Отмена
                            </button>
                        </div>

                    </div>
                </div>
            )}

        </div>
    );
}