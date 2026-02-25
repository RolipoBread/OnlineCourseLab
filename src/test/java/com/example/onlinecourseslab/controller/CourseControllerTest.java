package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import com.example.onlinecourseslab.mapper.CourseMapper;
import com.example.onlinecourseslab.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CourseService courseService;

	@MockBean
	private CourseMapper courseMapper; // теперь мокаем mapper

	@BeforeEach
	void setupMapperMock() {
		// Чтобы mapper.toDto возвращал валидный DTO для любого Course
		Mockito.when(courseMapper.toDto(any(Course.class)))
						.thenAnswer(invocation -> {
							Course c = invocation.getArgument(0);
							CourseResponseDto dto = new CourseResponseDto();
							dto.setId(c.getId());
							dto.setTitle(c.getTitle());
							dto.setAuthor(c.getAuthor());
							dto.setPrice(c.getPrice());
							dto.setLessonCount(c.getLessonCount());
							return dto;
						});

		// Чтобы mapper.toEntity возвращал Course для любого DTO
		Mockito.when(courseMapper.toEntity(any(CourseRequestDto.class)))
						.thenAnswer(invocation -> {
							CourseRequestDto dto = invocation.getArgument(0);
							Course course = new Course();
							course.setTitle(dto.getTitle());
							course.setAuthor(dto.getAuthor());
							course.setPrice(dto.getPrice());
							course.setLessonCount(dto.getLessonsCount());
							return course;
						});
	}

	@Test
	void getAllCourses_ShouldReturnCourses() throws Exception {
		Course course = new Course();
		course.setId(1L);
		course.setTitle("Java");
		course.setAuthor("John");
		course.setPrice(BigDecimal.valueOf(100.0));
		course.setLessonCount(10);

		Mockito.when(courseService.getAll()).thenReturn(List.of(course));

		mockMvc.perform(get("/courses"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$[0].title").value("Java"))
						.andExpect(jsonPath("$[0].id").value(1));
	}

	@Test
	void getCourseById_ShouldReturnCourse() throws Exception {
		Course course = new Course();
		course.setId(1L);
		course.setTitle("Spring Boot");
		course.setAuthor("Jane");
		course.setPrice(BigDecimal.valueOf(200.0));
		course.setLessonCount(20);

		Mockito.when(courseService.getById(1L)).thenReturn(course);

		mockMvc.perform(get("/courses/1"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.title").value("Spring Boot"))
						.andExpect(jsonPath("$.id").value(1));
	}

	@Test
	void createCourse_ShouldReturnCreatedCourse() throws Exception {
		CourseRequestDto request = new CourseRequestDto();
		request.setTitle("Kotlin");
		request.setAuthor("Alice");
		request.setPrice(BigDecimal.valueOf(120.0));
		request.setLessonsCount(15);

		Course response = new Course();
		response.setId(1L);
		response.setTitle("Kotlin");
		response.setAuthor("Alice");
		response.setPrice(BigDecimal.valueOf(120.0));
		response.setLessonCount(15);

		Mockito.when(courseService.create(any(Course.class))).thenReturn(response);

		mockMvc.perform(post("/courses")
										.contentType(MediaType.APPLICATION_JSON)
										.content(objectMapper.writeValueAsString(request)))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("$.id").value(1))
						.andExpect(jsonPath("$.title").value("Kotlin"));
	}

	@Test
	void updateCourse_ShouldReturnUpdatedCourse() throws Exception {
		CourseRequestDto request = new CourseRequestDto();
		request.setTitle("Updated Kotlin");
		request.setAuthor("Alice");
		request.setPrice(BigDecimal.valueOf(150.0));
		request.setLessonsCount(18);

		Course response = new Course();
		response.setId(1L);
		response.setTitle("Updated Kotlin");
		response.setAuthor("Alice");
		response.setPrice(BigDecimal.valueOf(150.0));
		response.setLessonCount(18);

		Mockito.when(courseService.update(eq(1L), any(Course.class)))
						.thenReturn(response);

		mockMvc.perform(put("/courses/1")
										.contentType(MediaType.APPLICATION_JSON)
										.content(objectMapper.writeValueAsString(request)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.title").value("Updated Kotlin"))
						.andExpect(jsonPath("$.id").value(1));
	}

	@Test
	void deleteCourse_ShouldReturnOk() throws Exception {
		mockMvc.perform(delete("/courses/1"))
						.andExpect(status().isOk());

		Mockito.verify(courseService).delete(1L);
	}
}