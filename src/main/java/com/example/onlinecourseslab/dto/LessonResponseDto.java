package com.example.onlinecourseslab.dto;

public class LessonResponseDto {

    private Long id;
    private String title;
    private String content;
    private Integer orderNumber;
    private Long courseId;

    public LessonResponseDto() { /* пустой конструктор*/ }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}