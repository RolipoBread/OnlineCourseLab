package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

	private CourseRepository repository;
	private CourseServiceImpl service;

	@BeforeEach
	void setUp() {
		repository = mock(CourseRepository.class);
		service = new CourseServiceImpl(repository);
	}

	@Test
	void getAll_shouldReturnAllCourses() {
		Course course = new Course();
		when(repository.findAll()).thenReturn(List.of(course));

		List<Course> result = service.getAll();

		assertEquals(1, result.size());
		verify(repository).findAll();
	}

	@Test
	void getById_shouldReturnCourse_whenFound() {
		Course course = new Course();
		when(repository.findById(1L)).thenReturn(Optional.of(course));

		Course result = service.getById(1L);

		assertEquals(course, result);
		verify(repository).findById(1L);
	}

	@Test
	void getById_shouldThrowException_whenNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
						() -> service.getById(1L));

		assertTrue(exception.getMessage().contains("Course not found with id 1"));
		verify(repository).findById(1L);
	}

	@Test
	void create_shouldSaveCourse() {
		Course course = new Course();
		when(repository.save(course)).thenReturn(course);

		Course result = service.create(course);

		assertEquals(course, result);
		verify(repository).save(course);
	}

	@Test
	void update_shouldModifyAndSaveCourse_whenExists() {
		Course existing = new Course();
		existing.setTitle("Old");
		existing.setAuthor("Old Author");
		existing.setDescription("Old Desc");
		existing.setPrice(BigDecimal.valueOf(50));
		existing.setLessonCount(10);

		Course update = new Course();
		update.setTitle("New");
		update.setAuthor("New Author");
		update.setDescription("New Desc");
		update.setPrice(BigDecimal.valueOf(100));
		update.setLessonCount(20);

		when(repository.findById(1L)).thenReturn(Optional.of(existing));
		when(repository.save(existing)).thenReturn(existing);

		Course result = service.update(1L, update);

		assertEquals("New", result.getTitle());
		assertEquals("New Author", result.getAuthor());
		assertEquals("New Desc", result.getDescription());
		assertEquals(BigDecimal.valueOf(100), result.getPrice());
		assertEquals(20, result.getLessonCount());

		verify(repository).findById(1L);
		verify(repository).save(existing);
	}

	@Test
	void update_shouldThrowException_whenNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		Course update = new Course();

		assertThrows(ResponseStatusException.class, () -> service.update(1L, update));
		verify(repository).findById(1L);
	}

	@Test
	void delete_shouldCallRepository_whenExists() {
		when(repository.existsById(1L)).thenReturn(true);

		service.delete(1L);

		verify(repository).existsById(1L);
		verify(repository).deleteById(1L);
	}

	@Test
	void delete_shouldThrowException_whenNotFound() {
		when(repository.existsById(1L)).thenReturn(false);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
						() -> service.delete(1L));

		assertTrue(exception.getMessage().contains("Course not found with id 1"));
		verify(repository).existsById(1L);
		verify(repository, never()).deleteById(anyLong());
	}

	@Test
	void findByAuthor_shouldReturnCoursesByAuthor() {
		Course course = new Course();
		course.setAuthor("John");
		when(repository.findByAuthor("John")).thenReturn(List.of(course));

		List<Course> result = service.findByAuthor("John");

		assertEquals(1, result.size());
		assertEquals("John", result.get(0).getAuthor());
		verify(repository).findByAuthor("John");
	}
}