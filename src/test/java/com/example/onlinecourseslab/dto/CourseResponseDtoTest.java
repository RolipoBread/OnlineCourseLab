package com.example.onlinecourseslab.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CourseResponseDtoTest {

	@Test
	void gettersAndSetters_shouldWork() {
		CourseResponseDto dto = new CourseResponseDto();

		dto.setId(1L);
		dto.setTitle("Java Basics");
		dto.setAuthor("John Doe");
		dto.setPrice(BigDecimal.valueOf(199.99));
		dto.setLessonCount(10);

		assertEquals(1L, dto.getId());
		assertEquals("Java Basics", dto.getTitle());
		assertEquals("John Doe", dto.getAuthor());
		assertEquals(BigDecimal.valueOf(199.99), dto.getPrice());
		assertEquals(10, dto.getLessonCount());
	}
}