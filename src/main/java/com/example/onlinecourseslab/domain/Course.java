package com.example.onlinecourseslab.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
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

    public Course(String title, String author, String description,
                  BigDecimal price, Integer lessonCount) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.lessonCount = lessonCount;
    }
}