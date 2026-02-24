<<<<<<< HEAD
package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
=======
package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
>>>>>>> 6c85d3303b4ccd8764bf92559f3314f4bec1f888
    List<Course> findByAuthor(String author); }