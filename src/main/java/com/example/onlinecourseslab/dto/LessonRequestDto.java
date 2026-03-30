package com.example.onlinecourseslab.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequestDto {
    @NotBlank
    private String title;
    private String content;
    @NotNull
    @Positive
    private Integer orderNumber;
    @NotNull
    private Long courseId;
}