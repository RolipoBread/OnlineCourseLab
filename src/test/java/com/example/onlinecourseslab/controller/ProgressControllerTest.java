package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.dto.ProgressRequestDto;
import com.example.onlinecourseslab.dto.ProgressResponseDto;
import com.example.onlinecourseslab.mapper.ProgressMapper;
import com.example.onlinecourseslab.service.CourseService;
import com.example.onlinecourseslab.service.LessonService;
import com.example.onlinecourseslab.service.ProgressService;
import com.example.onlinecourseslab.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProgressController.class)
class ProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgressService progressService;

    @MockBean
    private UserService userService;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ProgressMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void markCompleted_shouldReturnOk() throws Exception {
        ProgressRequestDto request = new ProgressRequestDto(1L, 1L);

        when(userService.getById(any())).thenReturn(null);
        when(lessonService.getById(any())).thenReturn(null);
        when(progressService.markCompleted(any(), any())).thenReturn(null);
        when(mapper.toDto(any())).thenReturn(new ProgressResponseDto());

        mockMvc.perform(post("/progress/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }
}