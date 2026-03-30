package com.example.onlinecourseslab.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String author;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Positive
    private Integer lessonCount;
    private Long categoryId;
}