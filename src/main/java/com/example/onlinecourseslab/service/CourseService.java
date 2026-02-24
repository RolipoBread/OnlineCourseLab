package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAll();
    Course getById(Long id);
    Course create(Course course);
    Course update(Long id, Course course);
    void delete(Long id);
    List<Course> findByAuthor(String author);
}