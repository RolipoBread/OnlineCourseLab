package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;

import java.util.List;

public interface LessonService {
    List<Lesson> getAll();
    Lesson getById(Long id);
    Lesson create(Lesson lesson);
    Lesson update(Long id, Lesson lesson);
    void delete(Long id);
    List<Lesson> getByCourse(Course course, int page, int size);
}