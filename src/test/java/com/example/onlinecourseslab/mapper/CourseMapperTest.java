package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CourseMapperTest {

	private CourseMapper courseMapper;

	@BeforeEach
	void setUp() {
		courseMapper = new CourseMapper();
	}

	@Test
	void toEntity_ShouldMapRequestDtoToCourse() {
		CourseRequestDto requestDto = new CourseRequestDto();
		requestDto.setTitle("Test Course");
		requestDto.setDescription("Test Description");
		requestDto.setAuthor("Test Author");
		requestDto.setPrice(BigDecimal.valueOf(99.99));
		requestDto.setLessonsCount(25);

		Course result = courseMapper.toEntity(requestDto);

		assertNotNull(result);
		assertEquals("Test Course", result.getTitle());
		assertEquals("Test Description", result.getDescription());
		assertEquals("Test Author", result.getAuthor());
		assertEquals(BigDecimal.valueOf(99.99), result.getPrice());
		assertEquals(25, result.getLessonCount());
		assertNull(result.getId());
	}

	@Test
	void toEntity_WithNullValues_ShouldMapNulls() {
		CourseRequestDto requestDto = new CourseRequestDto();

		Course result = courseMapper.toEntity(requestDto);

		assertNotNull(result);
		assertNull(result.getTitle());
		assertNull(result.getDescription());
		assertNull(result.getAuthor());
		assertNull(result.getPrice());
		assertEquals(0, result.getLessonCount());
	}

	@Test
	void toEntity_WithPartialData_ShouldMapAvailableData() {
		CourseRequestDto requestDto = new CourseRequestDto();
		requestDto.setTitle("Only Title");
		requestDto.setLessonsCount(10);

		Course result = courseMapper.toEntity(requestDto);

		assertNotNull(result);
		assertEquals("Only Title", result.getTitle());
		assertEquals(10, result.getLessonCount());
		assertNull(result.getDescription());
		assertNull(result.getAuthor());
		assertNull(result.getPrice());
	}

	@Test
	void toDto_ShouldMapCourseToResponseDto() {
		Course course = new Course("Test Course", "Test Author", "Test Description",
						BigDecimal.valueOf(99.99), 25);
		course.setId(1L);

		CourseResponseDto result = courseMapper.toDto(course);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Test Course", result.getTitle());
		assertEquals("Test Author", result.getAuthor());
		assertEquals(BigDecimal.valueOf(99.99), result.getPrice());
		assertEquals(25, result.getLessonCount());
	}

	@Test
	void toDto_WithEmptyCourse_ShouldMapDefaults() {
		Course course = new Course();

		CourseResponseDto result = courseMapper.toDto(course);

		assertNotNull(result);
		assertNull(result.getId());
		assertNull(result.getTitle());
		assertNull(result.getAuthor());
		assertNull(result.getPrice());
		assertEquals(0, result.getLessonCount());
	}

	@Test
	void toDto_WithPartialData_ShouldMapAvailableData() {
		Course course = new Course();
		course.setId(5L);
		course.setTitle("Partial Course");

		CourseResponseDto result = courseMapper.toDto(course);

		assertNotNull(result);
		assertEquals(5L, result.getId());
		assertEquals("Partial Course", result.getTitle());
		assertNull(result.getAuthor());
		assertNull(result.getPrice());
		assertEquals(0, result.getLessonCount());
	}
}