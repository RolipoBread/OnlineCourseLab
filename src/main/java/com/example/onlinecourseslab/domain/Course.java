package com.example.onlinecourseslab.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@SuppressWarnings("checkstyle:WhitespaceAround")
@Entity
@Table(name = "courses")
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

    @SuppressWarnings("checkstyle:WhitespaceAround")
    public Course() { }

    @SuppressWarnings("checkstyle:Indentation")
    public Course(String title, String author, String description,
                  BigDecimal price, Integer lessonCount) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.lessonCount = lessonCount;
    }

    public Long getId() {
        return id; }
    public String getTitle() {
        return title; }
    public String getAuthor() {
        return author; }
    public String getDescription() {
        return description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getLessonsCount() {
        return lessonCount; }


    public void setId(Long id) {
        this.id = id;
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
    public void setLessonsCount(Integer lessonCount) {
        this.lessonCount  = lessonCount;
    }
}
