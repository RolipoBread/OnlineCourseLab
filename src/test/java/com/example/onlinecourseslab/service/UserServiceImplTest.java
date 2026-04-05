package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

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
    void getById_shouldReturnUser() {
        User user = new User();
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        assertNotNull(service.getById(1L));
    }

    @Test
    void getById_shouldThrow() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getById(1L));
    }

    @Test
    void delete_shouldCallDependencies() {
        User user = new User();
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        service.delete(1L);

        verify(progressService).deleteByStudent(user);
        verify(repository).delete(user);
    }
}