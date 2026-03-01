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
		course.setCategory(null); // безопасно

		assertEquals(1L, course.getId());
		assertEquals("Java Basics", course.getTitle());
		assertEquals("John Doe", course.getAuthor());
		assertEquals("Learn Java from scratch", course.getDescription());
		assertEquals(BigDecimal.valueOf(99.99), course.getPrice());
		assertEquals(10, course.getLessonCount());
		assertNull(course.getCategory());
	}

	@Test
	void allArgsConstructor_shouldCreateObject() {
		Course course = new Course(
			2L,
			"Spring Boot",
			"Jane Smith",
			"Advanced Spring Boot course",
			BigDecimal.valueOf(149.99),
			20,
			null // category
		);

		assertEquals(2L, course.getId());
		assertEquals("Spring Boot", course.getTitle());
		assertEquals("Jane Smith", course.getAuthor());
		assertEquals("Advanced Spring Boot course", course.getDescription());
		assertEquals(BigDecimal.valueOf(149.99), course.getPrice());
		assertEquals(20, course.getLessonCount());
		assertNull(course.getCategory());
	}

	@Test
	void constructorWithoutId_shouldWork() {
		Course course = new Course(
			"Kotlin Basics",
			"Alice",
			"Learn Kotlin from scratch",
			BigDecimal.valueOf(129.99),
			12,
			null // category
		);

		assertNull(course.getId()); // id еще не присвоен
		assertEquals("Kotlin Basics", course.getTitle());
		assertEquals("Alice", course.getAuthor());
		assertEquals("Learn Kotlin from scratch", course.getDescription());
		assertEquals(BigDecimal.valueOf(129.99), course.getPrice());
		assertEquals(12, course.getLessonCount());
		assertNull(course.getCategory());
	}

	@Test
	void equalsAndHashCode_shouldWork() {
		Course course1 = new Course();
		course1.setId(1L);
		course1.setTitle("Java");
		course1.setAuthor("Author");

		Course course2 = new Course();
		course2.setId(1L);
		course2.setTitle("Java");
		course2.setAuthor("Author");

		Course course3 = new Course();
		course3.setId(2L);

		assertEquals(course1, course2);
		assertNotEquals(course1, course3);

		assertEquals(course1.hashCode(), course2.hashCode());
		assertNotEquals(course1.hashCode(), course3.hashCode());
	}

	@Test
	void equals_shouldCoverAllBranches() {
		Course course1 = new Course();
		Course course2 = new Course();

		// same reference
		assertEquals(course1, course1);

		// null comparison
		assertNotEquals(null, course1);

		// different class
		assertNotEquals("not a course", course1);

		// different ids
		course1.setId(1L);
		course2.setId(2L);
		assertNotEquals(course1, course2);

		// same ids
		course2.setId(1L);
		assertEquals(course1, course2);
	}

	@Test
	void equals_shouldHandleNullFields() {
		Course course1 = new Course();
		Course course2 = new Course();

		course1.setId(1L);
		course2.setId(1L);

		// остальные поля null
		assertEquals(course1, course2);
	}

	@Test
	void hashCode_shouldNotThrowWhenFieldsNull() {
		Course course = new Course();
		assertDoesNotThrow(course::hashCode);
	}

	@Test
	void toString_shouldContainFields() {
		Course course = new Course();
		course.setId(1L);
		course.setTitle("Java Basics");
		course.setAuthor("John Doe");
		course.setDescription("Learn Java from scratch");
		course.setPrice(BigDecimal.valueOf(99.99));
		course.setLessonCount(10);
		course.setCategory(null);

		String str = course.toString();

		assertTrue(str.contains("Java Basics"));
		assertTrue(str.contains("John Doe"));
		assertTrue(str.contains("Learn Java from scratch"));
	}

	@Test
	void toString_shouldNotThrowWhenFieldsNull() {
		Course course = new Course();
		assertDoesNotThrow(course::toString);
	}
}