package com.example.onlinecourseslab.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

	@Test
	void defaultConstructor_andGettersSetters_shouldWork() {
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

	@Test
	void parameterizedConstructor_shouldSetFields() {
		Course course = new Course(1L, "Java", "John Doe", "Desc", BigDecimal.valueOf(100), 10);

		assertEquals(1L, course.getId());
		assertEquals("Java", course.getTitle());
		assertEquals("John Doe", course.getAuthor());
		assertEquals("Desc", course.getDescription());
		assertEquals(BigDecimal.valueOf(100), course.getPrice());
		assertEquals(10, course.getLessonCount());
	}

	@Test
	void equalsAndHashCode_shouldWork() {
		Course course1 = new Course();
		course1.setId(1L);
		Course course2 = new Course();
		course2.setId(1L);
		Course course3 = new Course();
		course3.setId(2L);

		// equals
		assertEquals(course1, course2);
		assertNotEquals(course1, course3);
		assertNotEquals(course1, null);
		assertNotEquals(course1, new Object());

		// canEqual
		assertTrue(course1.canEqual(course2));
		assertFalse(course1.canEqual(new Object()));

		// hashCode
		assertEquals(course1.hashCode(), course2.hashCode());
		assertNotEquals(course1.hashCode(), course3.hashCode());
	}

	@Test
	void toString_shouldIncludeFields() {
		Course course = new Course();
		course.setId(1L);
		course.setTitle("Java");
		course.setAuthor("John Doe");
		course.setDescription("Description");
		course.setPrice(BigDecimal.valueOf(50));
		course.setLessonCount(5);

		String str = course.toString();
		assertTrue(str.contains("1"));
		assertTrue(str.contains("Java"));
		assertTrue(str.contains("John Doe"));
		assertTrue(str.contains("Description"));
		assertTrue(str.contains("50"));
		assertTrue(str.contains("5"));
	}
}