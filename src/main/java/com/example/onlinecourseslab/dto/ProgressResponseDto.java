package com.example.onlinecourseslab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressResponseDto {
    private Long id;
    private Long studentId;
    private Long lessonId;
    private boolean completed;
    private LocalDateTime completedAt;
}