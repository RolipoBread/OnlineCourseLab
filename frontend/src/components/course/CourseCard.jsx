import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

export default function CourseCard({ course }) {
    const navigate = useNavigate();
    const { user, loginUser } = useAuth();

    const color = course.categoryColor || "#333";

    const handleClick = async () => {

        // 1. если не залогинен → login
        if (!user) {
            navigate("/login");
            return;
        }

        try {
            // 2. записать на курс
            await fetch(
                `http://localhost:8080/users/${user.id}/courses/${course.id}`,
                { method: "PUT" }
            );

            // 3. обновить пользователя
            const res = await fetch(`http://localhost:8080/users/${user.id}`);
            const fullUser = await res.json();

            loginUser(fullUser);

            // 4. перейти в курс
            navigate(`/courses/${course.id}`);

        } catch (e) {
            console.error("Enroll error:", e);
        }
    };

    return (
        <div className="course-card" onClick={handleClick}>

            {/* IMAGE + TITLE */}
            <div
                className="course-image"
                style={{ backgroundColor: color }}
            >
                <div className="course-image-overlay">
                    <div className="course-title-on-image">
                        {course.title}
                    </div>
                </div>
            </div>

            {/* CONTENT */}
            <div className="course-content">

                <div className="course-teacher">
                    <span>Преподаватель:</span> {course.author}
                </div>

                <div className="course-desc">
                    {course.description}
                </div>

                <div className="course-bottom">
                    <div className="course-price">
                        ${course.price}
                    </div>
                </div>

            </div>

        </div>
    );
}