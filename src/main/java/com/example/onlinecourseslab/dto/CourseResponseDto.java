package com.example.onlinecourseslab.dto;

import java.math.BigDecimal;

public class CourseResponseDto {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private int lessonsCount;

    public CourseResponseDto() {
    }

    public Long getId() {
        return id; }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public int getLessonsCount() {
        return lessonsCount;
    }


    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setLessonsCount(int lessonsCount) {
        this.lessonsCount = lessonsCount;
    }
}
