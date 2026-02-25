package com.example.onlinecourseslab.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

	@Test
	void gettersAndSetters_shouldWork() {
		Course course = new Course();

		course.setId(1L);
		course.setTitle("Java Basics");
		course.setAuthor("John Doe");
		course.setDescription("Learn Java from scratch");
		course.setPrice(BigDecimal.valueOf(99.99));
		course.setLessonCount(10);

		assertEquals(1L, course.getId());
		assertEquals("Java Basics", course.getTitle());
		assertEquals("John Doe", course.getAuthor());
		assertEquals("Learn Java from scratch", course.getDescription());
		assertEquals(BigDecimal.valueOf(99.99), course.getPrice());
		assertEquals(10, course.getLessonCount());
	}
}