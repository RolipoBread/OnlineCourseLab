package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    void shouldFindByName() {
        Category category = new Category(null, "IT", "desc", null);
        repository.save(category);

        Optional<Category> result = repository.findByName("IT");

        assertTrue(result.isPresent());
    }
}