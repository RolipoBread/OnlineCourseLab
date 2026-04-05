package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.Role;
import com.example.onlinecourseslab.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void shouldSaveAndFindUser() {
        User user = new User(null, "John", "john@test.com", "123", Role.STUDENT, null);

        repository.save(user);

        Optional<User> result = repository.findByEmail("john@test.com");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }
}