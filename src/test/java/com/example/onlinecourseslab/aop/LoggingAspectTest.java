package com.example.onlinecourseslab.aop;

import com.example.onlinecourseslab.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LoggingAspectTest {

    @MockBean
    private CategoryService categoryService; // мокируем сервис

    @Test
    void shouldLogExecutionTime() {
        categoryService.getAll();
        verify(categoryService, times(1)).getAll();
    }
}