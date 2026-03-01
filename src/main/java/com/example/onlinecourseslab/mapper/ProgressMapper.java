package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.dto.ProgressResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProgressMapper {

    public ProgressResponseDto toDto(Progress progress) {
        final ProgressResponseDto dto = new ProgressResponseDto();
        dto.setId(progress.getId());
        dto.setStudentId(progress.getStudent().getId());
        dto.setLessonId(progress.getLesson().getId());
        dto.setCompleted(progress.isCompleted());
        dto.setCompletedAt(progress.getCompletedAt());
        return dto;
    }
}