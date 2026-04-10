package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import com.example.onlinecourseslab.mapper.LessonMapper;
import com.example.onlinecourseslab.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private LessonRepository repository;

    @Mock
    private CourseService courseService;

    @Mock
    private LessonMapper mapper;

    @InjectMocks
    private LessonServiceImpl service;

    private Lesson lesson;
    private Course course;
    private LessonRequestDto lessonDto;
    private LessonResponseDto lessonResponseDto;

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

        lessonDto = new LessonRequestDto("title", "content", 1, course.getId());
        lessonResponseDto = new LessonResponseDto(1L, "title", "content", 1, course.getId());
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
    void create_shouldSaveLesson() {
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

        when(repository.findByCourse(eq(course), any(Pageable.class))).thenReturn(page);

        List<Lesson> firstCall = service.getByCourse(course, 0, 10);
        List<Lesson> secondCall = service.getByCourse(course, 0, 10);

        assertEquals(1, firstCall.size());
        assertEquals(firstCall, secondCall);
        verify(repository, times(1)).findByCourse(eq(course), any(Pageable.class));
    }

    // ---------- BULK ----------

    @Test
    void addLessonsBulkTransactional_shouldSaveAllLessons() {
        when(courseService.getById(course.getId())).thenReturn(course);
        when(mapper.toEntity(lessonDto, course)).thenReturn(lesson);
        when(repository.saveAll(anyList())).thenReturn(List.of(lesson));
        when(mapper.toDto(lesson)).thenReturn(lessonResponseDto);

        List<LessonRequestDto> dtos = List.of(lessonDto);

        List<LessonResponseDto> result = service.addLessonsBulkTransactional(dtos);

        assertEquals(1, result.size());
        assertEquals(lessonResponseDto, result.get(0));

        verify(repository).saveAll(anyList());
    }

    @Test
    void addLessonsBulkNonTransactional_shouldSaveAllLessons() {
        when(courseService.getById(course.getId())).thenReturn(course);
        when(mapper.toEntity(lessonDto, course)).thenReturn(lesson);
        when(repository.saveAll(anyList())).thenReturn(List.of(lesson));
        when(mapper.toDto(lesson)).thenReturn(lessonResponseDto);

        List<LessonRequestDto> inputList = new ArrayList<>();
        inputList.add(lessonDto);

        List<LessonResponseDto> result = service.addLessonsBulkNonTransactional(inputList);

        assertEquals(1, result.size());
        assertEquals(lessonResponseDto, result.get(0));

        verify(repository).saveAll(anyList());
    }

    @Test
    void addLessonsBulkNonTransactional_shouldThrow_whenCourseNotFound() {
        when(courseService.getById(course.getId())).thenReturn(null);

        List<LessonRequestDto> dtos = List.of(lessonDto);

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> service.addLessonsBulkNonTransactional(dtos)
        );

        assertTrue(exception.getReason().contains("Course not found"));
    }

    @Test
    void addLessonsBulkTransactional_shouldThrow_whenCourseNotFound() {
        when(courseService.getById(course.getId())).thenReturn(null);

        List<LessonRequestDto> dtos = List.of(lessonDto);

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> service.addLessonsBulkTransactional(dtos)
        );

        assertTrue(exception.getReason().contains("Course not found"));
    }
}