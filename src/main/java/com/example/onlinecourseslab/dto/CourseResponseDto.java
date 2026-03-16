    package com.example.onlinecourseslab.dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.math.BigDecimal;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CourseResponseDto {
        private Long id;
        private String title;
        private String author;
        private BigDecimal price;
        private Integer lessonCount;
        private Long categoryId;
        private String categoryName;
    }
