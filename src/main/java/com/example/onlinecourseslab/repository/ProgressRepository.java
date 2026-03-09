package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @EntityGraph(value = "Progress.student.courses", type = EntityGraph.EntityGraphType.LOAD)
    List<Progress> findByStudent(User student);
    List<Progress> findByStudentAndLessonIn(User student, List<Lesson> lessons);
    Optional<Progress> findByStudentAndLesson(User student, Lesson lesson);
    List<Progress> findByStudentAndLessonCourse(User student, Course course);
    @EntityGraph(value = "Progress.lesson.course", type = EntityGraph.EntityGraphType.LOAD)
    List<Progress> findAll();
    List<Progress> findByLessonId(Long lessonId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Progress p WHERE p.lesson.id IN :lessonIds")
    int deleteAllByLessonIds(@Param("lessonIds") List<Long> lessonIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM Progress p WHERE p.student.id = :studentId")
    int deleteAllByStudentId(@Param("studentId") Long studentId);
}