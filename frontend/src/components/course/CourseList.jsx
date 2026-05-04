import CourseCard from "./CourseCard";

export default function CourseList({ courses }) {
    return (
        <div className="course-list">
            {courses.map((course) => (
                <CourseCard key={course.id} course={course} />
            ))}
        </div>
    );
}