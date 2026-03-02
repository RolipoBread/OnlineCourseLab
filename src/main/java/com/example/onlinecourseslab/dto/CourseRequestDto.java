package com.example.onlinecourseslab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    private String title;
    private String description;
    private String author;
    private BigDecimal price;
    private Integer lessonCount;
    private Long categoryId;
}
