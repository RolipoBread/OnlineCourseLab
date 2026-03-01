package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
  List<Course> findByAuthor(String author);
  @Query("SELECT c FROM Course c JOIN FETCH c.lessons")
  List<Course> findAllWithLessons();
}