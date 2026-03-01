package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    // Простой поиск по студенту
    @EntityGraph(value = "Progress.student.courses", type = EntityGraph.EntityGraphType.LOAD)
    List<Progress> findByStudent(User student);

    // Поиск по студенту и урокам
    List<Progress> findByStudentAndLessonIn(User student, List<Lesson> lessons);

    // Один прогресс
    Optional<Progress> findByStudentAndLesson(User student, Lesson lesson);

    // По курсу
    List<Progress> findByStudentAndLesson_Course(User student, Course course);

    // Все прогрессы с подгрузкой lesson->course
    @EntityGraph(value = "Progress.lesson.course", type = EntityGraph.EntityGraphType.LOAD)
    List<Progress> findAll();
}