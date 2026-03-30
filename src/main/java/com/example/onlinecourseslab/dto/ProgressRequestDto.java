package com.example.onlinecourseslab.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressRequestDto {
    @NotNull
    private Long studentId;
    @NotNull
    private Long lessonId;
}