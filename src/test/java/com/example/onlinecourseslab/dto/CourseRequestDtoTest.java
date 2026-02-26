package com.example.onlinecourseslab.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CourseRequestDtoTest {

	@Test
	void gettersAndSetters_shouldWork() {
		CourseRequestDto dto = new CourseRequestDto();

		dto.setTitle("Java Basics");
		dto.setDescription("Intro course");
		dto.setAuthor("John Doe");
		dto.setPrice(new BigDecimal("99.99"));
		dto.setLessonsCount(10);

		assertEquals("Java Basics", dto.getTitle());
		assertEquals("Intro course", dto.getDescription());
		assertEquals("John Doe", dto.getAuthor());
		assertEquals(new BigDecimal("99.99"), dto.getPrice());
		assertEquals(10, dto.getLessonsCount());
	}
}