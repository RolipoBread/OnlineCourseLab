package com.example.onlinecourseslab.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer lessonCount;

    // Связь с категорией
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // внешний ключ к таблице categories
    private Category category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();

    public Course(Long id, String title, String author, String description,
                  BigDecimal price, Integer lessonCount, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.lessonCount = lessonCount;
        this.category = category;
    }
    public Course(String title, String author, String description,
                  BigDecimal price, Integer lessonCount, Category category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.lessonCount = lessonCount;
        this.category = category;
    }
}