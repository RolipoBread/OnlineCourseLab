import { useEffect, useState } from "react";
import { getCourses } from "../../src/api/courseApi";
import "../../src/styles/global.css";

export default function CourseManager() {

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const load = async () => {
        setLoading(true);
        setError(null);

        try {
            const res = await getCourses();
            setCourses(res.data || []);
        } catch (e) {
            console.error(e);
            setError("Ошибка загрузки курсов");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        load();
    }, []);

    const deleteCourse = async (id) => {
        if (!window.confirm("Удалить курс?")) return;

        try {
            await fetch(`http://localhost:8080/courses/${id}`, {
                method: "DELETE"
            });

            load();
        } catch (e) {
            console.error(e);
            setError("Ошибка удаления курса");
        }
    };

    return (
        <div className="admin-section">

            <h3>Управление курсами</h3>

            {loading && <div>Загрузка...</div>}

            {error && <div style={{ color: "red" }}>{error}</div>}

            {!loading && courses.length === 0 && (
                <div>Курсов нет</div>
            )}

            <div className="admin-list">

                {courses.map(course => (
                    <div key={course.id} className="admin-item">

                        <div className="admin-item-info">
                            <div className="admin-title">
                                {course.title}
                            </div>

                            <div className="admin-sub">
                                {course.author}
                            </div>
                        </div>

                        <button
                            className="delete-btn"
                            onClick={() => deleteCourse(course.id)}
                        >
                            🗑
                        </button>

                    </div>
                ))}

            </div>

        </div>
    );
}