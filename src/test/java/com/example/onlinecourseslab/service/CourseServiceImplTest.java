package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.mapper.CourseMapper;
import com.example.onlinecourseslab.repository.CourseRepository;
import com.example.onlinecourseslab.repository.ProgressRepository;
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
class CourseServiceImplTest {

    @Mock
    private CourseRepository repository;

    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CourseMapper mapper;

    @InjectMocks
    private CourseServiceImpl service;

    @Test
    void getById_shouldReturnCourse() {
        Course course = new Course();
        when(repository.findById(1L)).thenReturn(Optional.of(course));

        assertNotNull(service.getById(1L));
    }

    @Test
    void create_shouldSaveCourse() {
        CourseRequestDto dto = new CourseRequestDto();
        Course course = new Course();

        when(mapper.toEntity(dto)).thenReturn(course);
        when(repository.save(course)).thenReturn(course);

        Course result = service.create(dto);

        assertNotNull(result);
        verify(repository).save(course);
    }

    @Test
    void findByAuthor_shouldReturnList() {
        when(repository.findByAuthor("John")).thenReturn(List.of(new Course()));

        List<Course> result = service.findByAuthor("John");

        assertEquals(1, result.size());
    }

    @Test
    void getAllPageable_shouldReturnPage() {
        when(repository.findAll(any())).thenReturn(new PageImpl<>(List.of()));

        assertNotNull(service.getAll(any()));
    }
}