package com.example.onlinecourseslab.dto;

public class LessonRequestDto {

    private String title;
    private String content;
    private Integer orderNumber;
    private Long courseId;

    public LessonRequestDto() { /* пустой конструктор*/ }

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