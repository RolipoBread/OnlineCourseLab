package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.mapper.CourseMapper;
import com.example.onlinecourseslab.repository.CourseRepository;
import com.example.onlinecourseslab.repository.ProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
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
        course.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(course));

        Course result = service.getById(1L);

        assertEquals(1L, result.getId());
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
        verify(categoryService, never()).getById(any());
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
        assertEquals(category, course.getCategory());
    }

    @Test
    void update_shouldUpdateCourse_withCategory() {
        Course existing = new Course();
        existing.setId(1L);

        CourseRequestDto dto = new CourseRequestDto();
        dto.setTitle("New title");
        dto.setCategoryId(1L);

        Category category = new Category();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryService.getById(1L)).thenReturn(category);
        when(repository.save(existing)).thenReturn(existing);

        Course result = service.update(1L, dto);

        assertEquals("New title", result.getTitle());
        assertEquals(category, result.getCategory());
    }

    @Test
    void update_shouldRemoveCategory_whenNull() {
        Course existing = new Course();
        existing.setId(1L);
        existing.setCategory(new Category());

        CourseRequestDto dto = new CourseRequestDto();
        dto.setCategoryId(null);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Course result = service.update(1L, dto);

        assertNull(result.getCategory());
    }

    @Test
    void delete_shouldCallRepository() {
        Course course = new Course();

        when(repository.findById(1L)).thenReturn(Optional.of(course));

        service.delete(1L);

        verify(repository).delete(course);
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
        when(repository.findByCategoryName("IT"))
            .thenReturn(List.of(new Course()));

        List<Course> result = service.findByCategory("IT");

        assertEquals(1, result.size());
    }

    @Test
    void getAllPageable_shouldReturnPage() {
        Page<Course> page = new PageImpl<>(List.of(new Course()));

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Course> result = service.getAll(PageRequest.of(0, 10));

        assertEquals(1, result.getContent().size());
    }
}