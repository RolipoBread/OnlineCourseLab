package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import com.example.onlinecourseslab.mapper.LessonMapper;
import com.example.onlinecourseslab.service.CourseService;
import com.example.onlinecourseslab.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private LessonMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        when(lessonService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/lessons"))
            .andExpect(status().isOk());
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        LessonRequestDto request = new LessonRequestDto();

        when(courseService.getById(any())).thenReturn(null);
        when(lessonService.create(any())).thenReturn(null);
        when(mapper.toDto(any())).thenReturn(new LessonResponseDto());

        mockMvc.perform(post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }
}