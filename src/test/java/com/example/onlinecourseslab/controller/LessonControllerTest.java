package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.domain.Lesson;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void create_shouldReturnCreated() throws Exception {
        LessonRequestDto request = new LessonRequestDto("Lesson 1", "Content", 1, 1L);
        Course course = new Course();
        course.setId(1L);

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setOrderNumber(request.getOrderNumber());
        lesson.setCourse(course);

        LessonResponseDto responseDto = new LessonResponseDto(
            lesson.getId(),
            lesson.getTitle(),
            lesson.getContent(),
            lesson.getOrderNumber(),
            course.getId()
        );

        when(courseService.getById(any(Long.class))).thenReturn(course);
        when(mapper.toEntity(any(LessonRequestDto.class), any(Course.class))).thenReturn(lesson);
        when(lessonService.create(any(Lesson.class))).thenReturn(lesson);
        when(mapper.toDto(any(Lesson.class))).thenReturn(responseDto);

        mockMvc.perform(post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("Lesson 1"))
            .andExpect(jsonPath("$.courseId").value(1L));
    }

    @Test
    void createBulkTransactional_shouldReturnCreated() throws Exception {
        LessonRequestDto dto = new LessonRequestDto("Lesson 2", "Content 2", 2, 1L);
        LessonResponseDto responseDto = new LessonResponseDto(2L, "Lesson 2", "Content 2", 2, 1L);

        when(lessonService.addLessonsBulkTransactional(any(List.class)))
            .thenReturn(List.of(responseDto));

        mockMvc.perform(post("/lessons/bulk/transactional")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(dto))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$[0].id").value(2L))
            .andExpect(jsonPath("$[0].title").value("Lesson 2"))
            .andExpect(jsonPath("$[0].courseId").value(1L));
    }

    @Test
    void createBulkNonTransactional_shouldReturnCreated() throws Exception {
        LessonRequestDto dto = new LessonRequestDto("Lesson 3", "Content 3", 3, 1L);
        LessonResponseDto responseDto = new LessonResponseDto(3L, "Lesson 3", "Content 3", 3, 1L);

        when(lessonService.addLessonsBulkNonTransactional(any(List.class)))
            .thenReturn(List.of(responseDto));

        mockMvc.perform(post("/lessons/bulk/non-transactional")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(dto))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$[0].id").value(3L))
            .andExpect(jsonPath("$[0].title").value("Lesson 3"))
            .andExpect(jsonPath("$[0].courseId").value(1L));
    }
}