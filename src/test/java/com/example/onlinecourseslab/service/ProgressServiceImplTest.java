package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.*;
import com.example.onlinecourseslab.repository.ProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressServiceImplTest {

    @Mock
    private ProgressRepository repository;

    @InjectMocks
    private ProgressServiceImpl service;

    // markCompleted - новый прогресс
    @Test
    void markCompleted_shouldSaveProgress() {
        User user = new User();
        Lesson lesson = new Lesson();

        when(repository.findByStudentAndLesson(user, lesson))
            .thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(new Progress(user, lesson, true));

        Progress result = service.markCompleted(user, lesson);

        assertNotNull(result);
        assertTrue(result.isCompleted());
        verify(repository).save(any());
    }

    // markCompleted - уже существующий прогресс
    @Test
    void markCompleted_existingProgress_shouldSetCompletedTrue() {
        User user = new User();
        Lesson lesson = new Lesson();
        Progress existingProgress = new Progress(user, lesson, false);

        when(repository.findByStudentAndLesson(user, lesson))
            .thenReturn(Optional.of(existingProgress));
        when(repository.save(existingProgress)).thenReturn(existingProgress);

        Progress result = service.markCompleted(user, lesson);

        assertTrue(result.isCompleted());
        verify(repository).save(existingProgress);
    }

    // getByStudent
    @Test
    void getByStudent_shouldReturnProgressList() {
        User user = new User();
        List<Progress> progresses = List.of(new Progress(), new Progress());

        when(repository.findByStudent(user)).thenReturn(progresses);

        List<Progress> result = service.getByStudent(user);

        assertEquals(2, result.size());
    }

    // getByStudentAndCourse
    @Test
    void getByStudentAndCourse_shouldReturnProgressList() {
        User user = new User();
        Course course = new Course();
        List<Progress> progresses = List.of(new Progress());

        when(repository.findByStudentAndLessonCourse(user, course))
            .thenReturn(progresses);

        List<Progress> result = service.getByStudentAndCourse(user, course);

        assertEquals(progresses, result);
    }

    // getByStudentAndLesson - найдено
    @Test
    void getByStudentAndLesson_existingProgress_shouldReturnProgress() {
        User user = new User();
        Lesson lesson = new Lesson();
        Progress progress = new Progress(user, lesson, true);

        when(repository.findByStudentAndLesson(user, lesson))
            .thenReturn(Optional.of(progress));

        Progress result = service.getByStudentAndLesson(user, lesson);

        assertEquals(progress, result);
    }

    // getByStudentAndLesson - не найдено
    @Test
    void getByStudentAndLesson_notFound_shouldThrow() {
        User user = new User();
        Lesson lesson = new Lesson();

        when(repository.findByStudentAndLesson(user, lesson))
            .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
            () -> service.getByStudentAndLesson(user, lesson));
    }

    // deleteByLessons - непустой список
    @Test
    void deleteByLessons_shouldDeleteAllProgressesForLessons() {
        Lesson lesson1 = new Lesson();
        lesson1.setId(1L);
        Lesson lesson2 = new Lesson();
        lesson2.setId(2L);

        Progress p1 = new Progress();
        Progress p2 = new Progress();

        when(repository.findByLessonId(1L)).thenReturn(List.of(p1));
        when(repository.findByLessonId(2L)).thenReturn(List.of(p2));

        service.deleteByLessons(List.of(lesson1, lesson2));

        // Проверяем вызовы deleteAll
        ArgumentCaptor<List<Progress>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository, times(2)).deleteAll(captor.capture());

        List<List<Progress>> deletedLists = captor.getAllValues();
        assertTrue(deletedLists.contains(List.of(p1)));
        assertTrue(deletedLists.contains(List.of(p2)));
    }

    // deleteByLessons - пустой список
    @Test
    void deleteByLessons_emptyList_shouldDoNothing() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        when(repository.findByLessonId(1L)).thenReturn(List.of());

        service.deleteByLessons(List.of(lesson));

        verify(repository, never()).deleteAll(any());
    }

    // deleteByStudent - непустой список
    @Test
    void deleteByStudent_shouldDeleteAllProgresses() {
        User user = new User();
        Progress p1 = new Progress();
        Progress p2 = new Progress();

        when(repository.findByStudent(user)).thenReturn(List.of(p1, p2));

        service.deleteByStudent(user);

        verify(repository).deleteAll(List.of(p1, p2));
    }

    // deleteByStudent - пустой список
    @Test
    void deleteByStudent_emptyList_shouldDoNothing() {
        User user = new User();

        when(repository.findByStudent(user)).thenReturn(List.of());

        service.deleteByStudent(user);

        verify(repository, never()).deleteAll(any());
    }
}