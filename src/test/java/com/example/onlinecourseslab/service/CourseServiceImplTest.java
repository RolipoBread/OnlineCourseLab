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
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

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

        Course result = service.getById(1L);

        assertEquals(course, result);
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
            () -> service.getById(1L));
    }

    @Test
    void create_shouldSaveCourse_withoutCategory() {
        CourseRequestDto dto = new CourseRequestDto();
        Course course = new Course();

        when(mapper.toEntity(dto)).thenReturn(course);
        when(repository.save(course)).thenReturn(course);

        Course result = service.create(dto);

        assertNotNull(result);
        verify(repository).save(course);
    }

    @Test
    void create_shouldSaveCourse_withCategory() {
        CourseRequestDto dto = new CourseRequestDto();
        dto.setCategoryId(1L);

        Course course = new Course();
        Category category = new Category();

        when(mapper.toEntity(dto)).thenReturn(course);
        when(categoryService.getById(1L)).thenReturn(category);
        when(repository.save(course)).thenReturn(course);

        Course result = service.create(dto);

        assertNotNull(result);
        verify(categoryService).getById(1L);
        verify(repository).save(course);
        assertEquals(category, course.getCategory());
    }

    @Test
    void findByAuthor_shouldReturnList() {
        when(repository.findByAuthor("John"))
            .thenReturn(List.of(new Course()));
        List<Course> result = service.findByAuthor("John");
        assertEquals(1, result.size());
    }

    @Test
    void findByCategory_shouldReturnList() {
        when(repository.findByCategoryName("Java"))
            .thenReturn(List.of(new Course()));
        List<Course> result = service.findByCategory("Java");
        assertEquals(1, result.size());
    }

    @Test
    void getAllPageable_shouldReturnPage() {
        PageRequest pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable))
            .thenReturn(new PageImpl<>(List.of(new Course())));
        var result = service.getAll(pageable);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void delete_shouldRemoveCourse() {
        Course course = new Course();
        when(repository.findById(1L)).thenReturn(Optional.of(course));
        service.delete(1L);
        verify(repository).delete(course);
    }
}