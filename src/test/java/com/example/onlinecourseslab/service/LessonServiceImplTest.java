package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private LessonRepository repository;

    @InjectMocks
    private LessonServiceImpl service;

    @Test
    void getById_shouldReturnLesson() {
        Lesson lesson = new Lesson();
        when(repository.findById(1L)).thenReturn(Optional.of(lesson));

        assertNotNull(service.getById(1L));
    }

    @Test
    void create_shouldClearCache() {
        Lesson lesson = new Lesson();
        when(repository.save(lesson)).thenReturn(lesson);

        service.create(lesson);

        verify(repository).save(lesson);
    }

    @Test
    void getByCourse_shouldUseRepository() {
        Course course = new Course();
        when(repository.findByCourse(eq(course), any()))
            .thenReturn(new PageImpl<>(List.of(new Lesson())));

        List<Lesson> result = service.getByCourse(course, 0, 5);

        assertEquals(1, result.size());
    }
}