package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CourseMapperTest {

	private CourseMapper courseMapper;

	@BeforeEach
	void setUp() {
		courseMapper = new CourseMapper();
	}

	@Test
	void toEntity_ShouldMapRequestDtoToCourse() {
		// Given
		CourseRequestDto requestDto = new CourseRequestDto();
		requestDto.setTitle("Test Course");
		requestDto.setDescription("Test Description");
		requestDto.setAuthor("Test Author");
		requestDto.setPrice(BigDecimal.valueOf(99.99));
		requestDto.setLessonsCount(25);

		// When
		Course result = courseMapper.toEntity(requestDto);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isEqualTo("Test Course");
		assertThat(result.getDescription()).isEqualTo("Test Description");
		assertThat(result.getAuthor()).isEqualTo("Test Author");
		assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(99.99));
		assertThat(result.getLessonsCount()).isEqualTo(25);
		assertThat(result.getId()).isNull();
	}

	@Test
	void toEntity_WithNullValues_ShouldMapNulls() {
		// Given
		CourseRequestDto requestDto = new CourseRequestDto();

		// When
		Course result = courseMapper.toEntity(requestDto);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isNull();
		assertThat(result.getDescription()).isNull();
		assertThat(result.getAuthor()).isNull();
		assertThat(result.getPrice()).isNull();
		assertThat(result.getLessonsCount()).isZero();
	}

	@Test
	void toDto_ShouldMapCourseToResponseDto() {
		// Given
		Course course = new Course("Test Course", "Test Author", "Test Description",
						BigDecimal.valueOf(99.99), 25);
		course.setId(1L);

		// When
		CourseResponseDto result = courseMapper.toDto(course);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getTitle()).isEqualTo("Test Course");
		assertThat(result.getAuthor()).isEqualTo("Test Author");
		assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(99.99));
		assertThat(result.getLessonsCount()).isEqualTo(25);
	}

	@Test
	void toDto_WithEmptyCourse_ShouldMapNulls() {
		// Given
		Course course = new Course();

		// When
		CourseResponseDto result = courseMapper.toDto(course);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isNull();
		assertThat(result.getTitle()).isNull();
		assertThat(result.getAuthor()).isNull();
		assertThat(result.getPrice()).isNull();
		assertThat(result.getLessonsCount()).isZero();
	}

	@Test
	void toDto_WithPartialData_ShouldMapAvailableData() {
		// Given
		Course course = new Course();
		course.setId(5L);
		course.setTitle("Partial Course");
		// Не устанавливаем другие поля

		// When
		CourseResponseDto result = courseMapper.toDto(course);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(5L);
		assertThat(result.getTitle()).isEqualTo("Partial Course");
		assertThat(result.getAuthor()).isNull();
		assertThat(result.getPrice()).isNull();
		assertThat(result.getLessonsCount()).isZero();
	}
}