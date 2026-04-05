package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.domain.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldFindByAuthor() {
        Category cat = categoryRepository.save(new Category("IT", "desc"));

        Course course = new Course(
            "Java", "John", "desc",
            BigDecimal.TEN, 10, cat
        );

        repository.save(course);

        List<Course> result = repository.findByAuthor("John");

        assertEquals(1, result.size());
    }
}