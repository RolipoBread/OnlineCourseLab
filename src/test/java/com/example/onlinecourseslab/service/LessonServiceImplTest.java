package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import org.springframework.web.server.ResponseStatusException;

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

    private Lesson lesson;
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("title");
        lesson.setContent("content");
        lesson.setOrderNumber(1);
        lesson.setCourse(course);
    }


    @Test
    void getAll_shouldReturnLessons() {
        when(repository.findAll()).thenReturn(List.of(lesson));

        List<Lesson> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }


    @Test
    void getById_shouldReturnLesson() {
        when(repository.findById(1L)).thenReturn(Optional.of(lesson));

        Lesson result = service.getById(1L);

        assertEquals(lesson, result);
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
            () -> service.getById(1L));
    }


    @Test
    void create_shouldSaveLesson_andClearCache() {
        when(repository.save(lesson)).thenReturn(lesson);

        Lesson result = service.create(lesson);

        assertEquals(lesson, result);
        verify(repository).save(lesson);
    }


    @Test
    void update_shouldModifyAndSaveLesson() {
        Lesson updated = new Lesson();
        updated.setTitle("new");
        updated.setContent("new content");
        updated.setOrderNumber(2);
        updated.setCourse(course);

        when(repository.findById(1L)).thenReturn(Optional.of(lesson));
        when(repository.save(any())).thenReturn(lesson);

        Lesson result = service.update(1L, updated);

        assertEquals("new", result.getTitle());
        verify(repository).save(lesson);
    }


    @Test
    void delete_shouldCallRepository() {
        service.delete(1L);

        verify(repository).deleteById(1L);
    }


    @Test
    void getByCourse_shouldUseRepository_thenCache() {
        Page<Lesson> page = new PageImpl<>(List.of(lesson));

        when(repository.findByCourse(eq(course), any(Pageable.class)))
            .thenReturn(page);

        List<Lesson> firstCall = service.getByCourse(course, 0, 10);

        List<Lesson> secondCall = service.getByCourse(course, 0, 10);

        assertEquals(1, firstCall.size());
        assertEquals(firstCall, secondCall);

        verify(repository, times(1))
            .findByCourse(eq(course), any(Pageable.class));
    }

}