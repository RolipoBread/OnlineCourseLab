package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CourseService courseService;

	@InjectMocks
	private CourseController courseController;

	private Course course1;
	private Course course2;
	private List<Course> courseList;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();

		course1 = new Course("Java Basics", "John Doe", "Basic Java course",
						BigDecimal.valueOf(99.99), 20);
		course1.setId(1L);

		course2 = new Course("Spring Boot", "Jane Smith", "Advanced Spring",
						BigDecimal.valueOf(149.99), 30);
		course2.setId(2L);

		courseList = Arrays.asList(course1, course2);
	}

	@Test
	void getAll_ShouldReturnListOfCourses() throws Exception {
		when(courseService.getAll()).thenReturn(courseList);

		mockMvc.perform(get("/courses"))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$[0].id").value(1L))
						.andExpect(jsonPath("$[0].title").value("Java Basics"))
						.andExpect(jsonPath("$[1].id").value(2L))
						.andExpect(jsonPath("$[1].title").value("Spring Boot"));

		verify(courseService, times(1)).getAll();
	}

	@Test
	void getById_WithValidId_ShouldReturnCourse() throws Exception {
		when(courseService.getById(1L)).thenReturn(course1);

		mockMvc.perform(get("/courses/1"))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.id").value(1L))
						.andExpect(jsonPath("$.title").value("Java Basics"))
						.andExpect(jsonPath("$.author").value("John Doe"));

		verify(courseService, times(1)).getById(1L);
	}

	@Test
	void getById_WithInvalidId_ShouldThrowException() throws Exception {
		when(courseService.getById(999L)).thenThrow(new RuntimeException("Course not found with id 999"));

		mockMvc.perform(get("/courses/999"))
						.andExpect(status().is5xxServerError());

		verify(courseService, times(1)).getById(999L);
	}

	@Test
	void findByAuthor_WithValidAuthor_ShouldReturnCourses() throws Exception {
		when(courseService.findByAuthor("John Doe")).thenReturn(Arrays.asList(course1));

		mockMvc.perform(get("/courses/search")
										.param("author", "John Doe"))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$[0].author").value("John Doe"))
						.andExpect(jsonPath("$[0].title").value("Java Basics"));

		verify(courseService, times(1)).findByAuthor("John Doe");
	}

	@Test
	void findByAuthor_WithNoResults_ShouldReturnEmptyList() throws Exception {
		when(courseService.findByAuthor("Unknown")).thenReturn(Arrays.asList());

		mockMvc.perform(get("/courses/search")
										.param("author", "Unknown"))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$").isArray())
						.andExpect(jsonPath("$").isEmpty());

		verify(courseService, times(1)).findByAuthor("Unknown");
	}

	@Test
	void create_WithValidCourse_ShouldReturnCreatedCourse() throws Exception {
		Course newCourse = new Course("New Course", "New Author", "Description",
						BigDecimal.valueOf(199.99), 40);
		newCourse.setId(3L);

		when(courseService.create(any(Course.class))).thenReturn(newCourse);

		// Создаем JSON строку вручную
		String courseJson = "{"
						+ "\"title\":\"New Course\","
						+ "\"author\":\"New Author\","
						+ "\"description\":\"Description\","
						+ "\"price\":199.99,"
						+ "\"lessonsCount\":40"
						+ "}";

		mockMvc.perform(post("/courses")
										.contentType(MediaType.APPLICATION_JSON)
										.content(courseJson))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").value(3L))
						.andExpect(jsonPath("$.title").value("New Course"))
						.andExpect(jsonPath("$.author").value("New Author"));

		verify(courseService, times(1)).create(any(Course.class));
	}

	@Test
	void update_WithValidId_ShouldReturnUpdatedCourse() throws Exception {
		Course updatedCourse = new Course("Updated Title", "Updated Author", "Description",
						BigDecimal.valueOf(299.99), 50);
		updatedCourse.setId(1L);

		when(courseService.update(eq(1L), any(Course.class))).thenReturn(updatedCourse);

		// Создаем JSON строку вручную
		String courseJson = "{"
						+ "\"title\":\"Updated Title\","
						+ "\"author\":\"Updated Author\","
						+ "\"description\":\"Description\","
						+ "\"price\":299.99,"
						+ "\"lessonsCount\":50"
						+ "}";

		mockMvc.perform(put("/courses/1")
										.contentType(MediaType.APPLICATION_JSON)
										.content(courseJson))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").value(1L))
						.andExpect(jsonPath("$.title").value("Updated Title"))
						.andExpect(jsonPath("$.author").value("Updated Author"));

		verify(courseService, times(1)).update(eq(1L), any(Course.class));
	}

	@Test
	void delete_WithValidId_ShouldReturnOk() throws Exception {
		doNothing().when(courseService).delete(1L);

		mockMvc.perform(delete("/courses/1"))
						.andExpect(status().isOk());

		verify(courseService, times(1)).delete(1L);
	}

	@Test
	void delete_WithInvalidId_ShouldThrowException() throws Exception {
		doThrow(new RuntimeException("Course not found")).when(courseService).delete(999L);

		mockMvc.perform(delete("/courses/999"))
						.andExpect(status().is5xxServerError());

		verify(courseService, times(1)).delete(999L);
	}
}