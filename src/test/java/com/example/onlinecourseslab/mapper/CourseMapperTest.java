package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CourseMapperTest {

	private final CourseMapper mapper = new CourseMapper();

	@Test
	void toEntity_ShouldMapAllFields() {
		CourseRequestDto dto = new CourseRequestDto();
		dto.setTitle("Java Basics");
		dto.setDescription("Learn Java");
		dto.setAuthor("John Doe");
		dto.setPrice(BigDecimal.valueOf(99.99));
		dto.setLessonsCount(10);

		Course course = mapper.toEntity(dto);

		assertEquals("Java Basics", course.getTitle());
		assertEquals("Learn Java", course.getDescription());
		assertEquals("John Doe", course.getAuthor());
		assertEquals(BigDecimal.valueOf(99.99), course.getPrice());
		assertEquals(10, course.getLessonCount());
	}

	@Test
	void toDto_ShouldMapAllFields() {
		Course course = new Course();
		course.setId(1L);
		course.setTitle("Spring Boot");
		course.setDescription("Full course");
		course.setAuthor("Jane Doe");
		course.setPrice(BigDecimal.valueOf(150.0));
		course.setLessonCount(20);

		CourseResponseDto dto = mapper.toDto(course);

		assertEquals(1L, dto.getId());
		assertEquals("Spring Boot", dto.getTitle());
		assertEquals("Jane Doe", dto.getAuthor());
		assertEquals(BigDecimal.valueOf(150.0), dto.getPrice());
		assertEquals(20, dto.getLessonCount());
	}

	@Test
	void toDto_WithEmptyCourse_ShouldHandleNulls() {
		Course course = new Course(); // все поля null по умолчанию

		CourseResponseDto dto = mapper.toDto(course);

		assertNull(dto.getId());
		assertNull(dto.getTitle());
		assertNull(dto.getAuthor());
		assertNull(dto.getPrice());
		assertNull(dto.getLessonCount());
	}

	@Test
	void toEntity_WithEmptyDto_ShouldHandleNulls() {
		CourseRequestDto dto = new CourseRequestDto(); // все поля null

		Course course = mapper.toEntity(dto);

		assertNull(course.getTitle());
		assertNull(course.getDescription());
		assertNull(course.getAuthor());
		assertNull(course.getPrice());
		assertNull(course.getLessonCount());
	}
}