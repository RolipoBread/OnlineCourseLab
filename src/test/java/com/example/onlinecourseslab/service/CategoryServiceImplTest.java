package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    @Test
    void getById_shouldReturnCategory() {
        Category category = new Category(1L, "IT", "desc", null);
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        Category result = service.getById(1L);

        assertEquals("IT", result.getName());
    }

    @Test
    void getById_shouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getById(1L));
    }

    @Test
    void create_shouldSaveCategory() {
        Category category = new Category("IT", "desc");

        when(repository.save(category)).thenReturn(category);

        Category result = service.create(category);

        assertNotNull(result);
        verify(repository).save(category);
    }

    @Test
    void delete_shouldCallRepository() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}