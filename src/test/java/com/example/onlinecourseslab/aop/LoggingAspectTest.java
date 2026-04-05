package com.example.onlinecourseslab.aop;

import com.example.onlinecourseslab.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoggingAspectTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldLogExecutionTime() {
        categoryService.getAll();
    }
}