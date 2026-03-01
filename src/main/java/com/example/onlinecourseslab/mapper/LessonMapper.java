package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toEntity(LessonRequestDto dto, Course course) {
        final Lesson lesson = new Lesson();
        lesson.setTitle(dto.getTitle());
        lesson.setContent(dto.getContent());
        lesson.setOrderNumber(dto.getOrderNumber());
        lesson.setCourse(course);
        return lesson;
    }

    public LessonResponseDto toDto(Lesson lesson) {
        final LessonResponseDto dto = new LessonResponseDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setContent(lesson.getContent());
        dto.setOrderNumber(lesson.getOrderNumber());
        dto.setCourseId(lesson.getCourse().getId());
        return dto;
    }
}