package com.example.onlinecourseslab.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CourseRequestDtoTest {

	@Test
	void gettersAndSetters_shouldWork() {
		CourseRequestDto dto = new CourseRequestDto();

		dto.setTitle("Java Basics");
		dto.setAuthor("John Doe");
		dto.setDescription("Learn Java");
		dto.setPrice(BigDecimal.valueOf(199.99));
		dto.setLessonsCount(10);

		assertEquals("Java Basics", dto.getTitle());
		assertEquals("John Doe", dto.getAuthor());
		assertEquals("Learn Java", dto.getDescription());
		assertEquals(BigDecimal.valueOf(199.99), dto.getPrice());
		assertEquals(10, dto.getLessonsCount());
	}
}