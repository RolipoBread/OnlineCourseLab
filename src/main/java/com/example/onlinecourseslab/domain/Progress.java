package com.example.onlinecourseslab.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "lesson_id"}))
@NamedEntityGraph(
    name = "Progress.lesson.course",
    attributeNodes = @NamedAttributeNode(
        value = "lesson",
        subgraph = "lesson-subgraph"
    ),
    subgraphs = @NamedSubgraph(
        name = "lesson-subgraph",
        attributeNodes = @NamedAttributeNode("course")
    )
)
@NamedEntityGraph(
    name = "Progress.student.courses",
    attributeNodes = @NamedAttributeNode(
        value = "student",
        subgraph = "student-subgraph"
    ),
    subgraphs = @NamedSubgraph(
        name = "student-subgraph",
        attributeNodes = @NamedAttributeNode("courses")
    )
)
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private boolean completed;

    private LocalDateTime completedAt;

    public Progress() {
    }

    public Progress(User student, Lesson lesson, boolean completed) {
        this.student = student;
        this.lesson = lesson;
        this.completed = completed;
        if (completed) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public User getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
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

    public void setStudent(User student) {
        this.student = student;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed && this.completedAt == null) {
            this.completedAt = LocalDateTime.now();
        } else if (!completed) {
            this.completedAt = null;
        }
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}