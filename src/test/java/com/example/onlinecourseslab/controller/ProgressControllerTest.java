package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.dto.ProgressRequestDto;
import com.example.onlinecourseslab.dto.ProgressResponseDto;
import com.example.onlinecourseslab.mapper.ProgressMapper;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private ProgressMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void markCompleted_shouldReturnOk() throws Exception {
        ProgressRequestDto request = new ProgressRequestDto(1L, 1L);

        User user = new User();
        user.setId(1L);

        Lesson lesson = new Lesson();
        lesson.setId(1L);

        Progress progress = new Progress();
        progress.setId(1L);
        progress.setStudent(user);
        progress.setLesson(lesson);
        progress.setCompleted(true);

        ProgressResponseDto responseDto = new ProgressResponseDto(
            1L,
            1L,
            1L,
            true
        );

        when(userService.getById(1L)).thenReturn(user);
        when(lessonService.getById(1L)).thenReturn(lesson);
        when(progressService.markCompleted(user, lesson)).thenReturn(progress);
        when(mapper.toDto(progress)).thenReturn(responseDto);

        mockMvc.perform(post("/progress/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.studentId").value(1))
            .andExpect(jsonPath("$.lessonId").value(1))
            .andExpect(jsonPath("$.completed").value(true));
    }
}