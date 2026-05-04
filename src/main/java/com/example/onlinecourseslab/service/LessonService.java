package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonService {
    Page<Lesson> getAll(Pageable pageable);
    Lesson getById(Long id);
    Lesson create(Lesson lesson);
    Lesson update(Long id, Lesson lesson);
    void delete(Long id);
    Page<Lesson> getByCourse(Course course, Pageable pageable);
    List<LessonResponseDto> addLessonsBulkTransactional(List<LessonRequestDto> dtos);
    List<LessonResponseDto> addLessonsBulkNonTransactional(List<LessonRequestDto> dtos);
    }