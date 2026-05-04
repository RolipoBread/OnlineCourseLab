import { useEffect, useState, useRef } from "react";
import { getCourses } from "../../api/courseApi";
import { getCategories } from "../../api/categoryApi";
import { getTeachers } from "../../api/teacherApi.js";
import CourseList from "../../components/course/CourseList";
import TeacherSlider from "../../components/teacher/TeacherSlider";
import "swiper/css";
import "swiper/css/navigation";
import "../../styles/global.css";

export default function HomePage() {
    const [courses, setCourses] = useState([]);
    const [categories, setCategories] = useState(["Все"]);
    const [selectedCategory, setSelectedCategory] = useState("Все");
    const [loading, setLoading] = useState(false);
    const [teachers, setTeachers] = useState([]);

    useEffect(() => {
        getTeachers().then(res => {
            console.log("ПРЕПОДАВАТЕЛИ:", res.data);
            setTeachers(res.data);
        });
    }, []);

    useEffect(() => {
        getCategories().then(res => {
            const names = res.data.map(c => c.name);
            setCategories(["Все", ...names]);
        });
    }, []);

    useEffect(() => {
        const fetchCourses = async () => {
            setLoading(true);
            try {
                const params =
                    selectedCategory === "Все"
                        ? {}
                        : { category: selectedCategory };
                const res = await getCourses(params);
                setCourses(res.data);
            } finally {
                setLoading(false);
            }
        };
        fetchCourses();
    }, [selectedCategory]);

    const coursesRef = useRef(null);

    return (
        <>
            <section className="segment segment-dark hero-section">
                <div className="container">
                    <h1>Освойте навыки, которые имеют значение</h1>
                    <p>Современные курсы от реальных разработчиков</p>
                </div>
            </section>
            <TeacherSlider  teachers={teachers} />
            <section ref={coursesRef} className="segment segment-dark courses-section">
                <div className="container">
                    <h2 className="title">Курсы</h2>
                    <div className="category-list">
                        {categories.map(cat => (
                            <button
                                key={cat}
                                onClick={() => setSelectedCategory(cat)}
                                className={`category-btn ${selectedCategory === cat ? "active" : ""}`}
                            >
                                {cat}
                            </button>
                        ))}
                    </div>
                    {loading ? (
                        <div className="courses-loading">
                            Загрузка...
                        </div>
                    ) : (
                        <CourseList courses={courses} />
                    )}
                </div>
            </section>
        </>
    );
}