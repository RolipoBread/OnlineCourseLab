package com.example.onlinecourseslab.dto;

import java.math.BigDecimal;

public class CourseRequestDto {
    private String title;
    private String description;
    private String author;
    private BigDecimal price;
    private Integer lessonCount;

    public CourseRequestDto() { /* пустой конструктор*/ }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getAuthor() {
        return author;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getLessonsCount() {
        return lessonCount;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setLessonsCount(Integer lessonsCount) {
        this.lessonCount = lessonsCount;
    }
}
