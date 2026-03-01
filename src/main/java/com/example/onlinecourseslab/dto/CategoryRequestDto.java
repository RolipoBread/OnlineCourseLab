package com.example.onlinecourseslab.dto;

public class CategoryRequestDto {

    private String name;
    private String description;

    public CategoryRequestDto() { /* пустой конструктор*/ }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}