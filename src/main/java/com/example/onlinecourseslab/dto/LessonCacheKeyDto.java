package com.example.onlinecourseslab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class LessonCacheKeyDto {
    private Long courseId;
    private int page;
    private int size;
}
