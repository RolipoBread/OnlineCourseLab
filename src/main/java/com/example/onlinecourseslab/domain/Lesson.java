package com.example.onlinecourseslab.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String content;

    @Column(nullable = false)
    private Integer orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(
        mappedBy = "lesson",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Progress> progresses = new ArrayList<>();

    public Lesson(String title, String content, Integer orderNumber, Course course) {
        this.title = title;
        this.content = content;
        this.orderNumber = orderNumber;
        this.course = course;
    }
}
