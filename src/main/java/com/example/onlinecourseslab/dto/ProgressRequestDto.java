package com.example.onlinecourseslab.dto;

public class ProgressRequestDto {

    private Long studentId;
    private Long lessonId;

    public ProgressRequestDto() { /* пустой конструктор*/ }

    public Long getStudentId() {
        return studentId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }
}