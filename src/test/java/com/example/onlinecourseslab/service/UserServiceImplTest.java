package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Role;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private ProgressService progressService;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void getAll_shouldReturnUsers() {
        List<User> users = List.of(
            new User("Alice", "a@mail.com", "123", Role.STUDENT),
            new User("Bob", "b@mail.com", "456", Role.TEACHER)
        );
        when(repository.findAll()).thenReturn(users);

        List<User> result = service.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getById_shouldReturnUser() {
        User user = new User("Alice", "a@mail.com", "123", Role.STUDENT);
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        User result = service.getById(1L);

        assertEquals(user, result);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getById(1L));
    }

    @Test
    void create_shouldSaveUser() {
        User user = new User("Alice", "a@mail.com", "123", Role.STUDENT);
        when(repository.save(user)).thenReturn(user);

        User result = service.create(user);

        assertEquals(user, result);
        verify(repository).save(user);
    }

    @Test
    void update_shouldUpdateExistingUser() {
        User existing = new User("Old", "old@mail.com", "123", Role.STUDENT);
        User update = new User("New", "new@mail.com", "456", Role.TEACHER);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        User result = service.update(1L, update);

        assertEquals("New", result.getName());
        assertEquals(Role.TEACHER, result.getRole());
        verify(repository).save(existing);
    }

    @Test
    void update_shouldThrowIfNotFound() {
        User update = new User("New", "new@mail.com", "456", Role.TEACHER);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.update(1L, update));
    }

    @Test
    void delete_shouldCallDependencies() {
        User user = new User("Alice", "a@mail.com", "123", Role.STUDENT);
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        service.delete(1L);

        verify(progressService).deleteByStudent(user);
        verify(repository).delete(user);
    }

    @Test
    void delete_shouldThrowIfUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.delete(1L));
    }

    @Test
    void findByEmail_shouldReturnUser() {
        User user = new User("Alice", "a@mail.com", "123", Role.STUDENT);
        when(repository.findByEmail("a@mail.com")).thenReturn(Optional.of(user));

        User result = service.findByEmail("a@mail.com");

        assertEquals(user, result);
    }

    @Test
    void findByEmail_shouldThrowIfNotFound() {
        when(repository.findByEmail("a@mail.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findByEmail("a@mail.com"));
    }

    @Test
    void findByRole_shouldReturnUsers() {
        List<User> users = List.of(
            new User("Alice", "a@mail.com", "123", Role.STUDENT),
            new User("Bob", "b@mail.com", "456", Role.STUDENT)
        );
        when(repository.findByRoleNative("STUDENT")).thenReturn(users);

        List<User> result = service.findByRole("STUDENT");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(u -> u.getRole() == Role.STUDENT));
    }
}