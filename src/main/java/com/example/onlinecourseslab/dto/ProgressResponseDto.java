package com.example.onlinecourseslab.dto;

import java.time.LocalDateTime;

public class ProgressResponseDto {

    private Long id;
    private Long studentId;
    private Long lessonId;
    private boolean completed;
    private LocalDateTime completedAt;

    public ProgressResponseDto() { /* пустой конструктор*/ }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}