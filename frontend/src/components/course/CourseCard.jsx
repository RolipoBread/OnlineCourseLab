import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { api } from "../../api/axios";

export default function CourseCard({ course }) {
    const navigate = useNavigate();
    const { user, loginUser } = useAuth();

    const color = course.categoryColor || "#333";

    const handleClick = async () => {

        if (!user) {
            navigate("/login");
            return;
        }

        try {
            // ENROLL
            await api.put(
                `/users/${user.id}/courses/${course.id}`
            );

            // refresh user
            const res = await api.get(`/users/${user.id}`);
            loginUser(res.data);

            navigate(`/courses/${course.id}`);

        } catch (e) {
            console.error("Enroll error:", e);
        }
    };

    return (
        <div className="course-card" onClick={handleClick}>
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