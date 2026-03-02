package com.example.onlinecourseslab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponseDto {
    private Long id;
    private String title;
    private String content;
    private Integer orderNumber;
    private Long courseId;
}