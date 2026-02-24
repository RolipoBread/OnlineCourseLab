package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
  List<Course> findByAuthor(String author); }