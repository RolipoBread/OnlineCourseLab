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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        LessonRequestDto request = new LessonRequestDto();
        request.setTitle("Lesson 1");
        request.setContent("Content");
        request.setOrderNumber(1);
        request.setCourseId(1L);

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

        when(courseService.getById(anyLong())).thenReturn(course);
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
}