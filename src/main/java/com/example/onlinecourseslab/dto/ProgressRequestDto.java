package com.example.onlinecourseslab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressRequestDto {
    private Long studentId;
    private Long lessonId;
}