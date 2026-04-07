package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import com.example.onlinecourseslab.mapper.CourseMapper;
import com.example.onlinecourseslab.service.CategoryService;
import com.example.onlinecourseslab.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService service;

    @MockBean
    private CourseMapper mapper;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCourses_shouldReturnOk() throws Exception {
        when(service.getAll(any())).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/courses"))
            .andExpect(status().isOk());
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        CourseRequestDto request = new CourseRequestDto(
            "Java Basics",
            "Intro course",
            "John Doe",
            new BigDecimal("99.99"),
            10,
            1L
        );

        Course course = new Course();
        course.setId(1L);
        course.setTitle(request.getTitle());

        CourseResponseDto response = new CourseResponseDto();
        response.setId(1L);
        response.setTitle(request.getTitle());

        when(service.create(any())).thenReturn(course);
        when(mapper.toDto(course)).thenReturn(response);

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Java Basics"));
    }
}