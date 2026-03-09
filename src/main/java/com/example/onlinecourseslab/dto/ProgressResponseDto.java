package com.example.onlinecourseslab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressResponseDto {
    private Long id;
    private Long studentId;
    private Long lessonId;
    private boolean completed;
}