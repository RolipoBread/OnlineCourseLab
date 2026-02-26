package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;

	private Course course1;
	private Course course2;

	@BeforeEach
	void setUp() {
		courseRepository.deleteAll();

		course1 = new Course();
		course1.setTitle("Java Basics");
		course1.setAuthor("Alice");
		course1.setDescription("Introduction to Java");
		course1.setPrice(BigDecimal.valueOf(49.99));
		course1.setLessonCount(10);

		course2 = new Course();
		course2.setTitle("Spring Boot Advanced");
		course2.setAuthor("Bob");
		course2.setDescription("Deep dive into Spring Boot");
		course2.setPrice(BigDecimal.valueOf(79.99));
		course2.setLessonCount(15);

		courseRepository.save(course1);
		courseRepository.save(course2);
	}

	@Test
	void testFindByAuthor_existingAuthor_returnsCourse() {
		List<Course> courses = courseRepository.findByAuthor("Alice");

		assertThat(courses).hasSize(1);
		assertThat(courses.get(0).getTitle()).isEqualTo("Java Basics");
	}

	@Test
	void testFindByAuthor_nonExistingAuthor_returnsEmptyList() {
		List<Course> courses = courseRepository.findByAuthor("Charlie");

		assertThat(courses).isEmpty();
	}
}