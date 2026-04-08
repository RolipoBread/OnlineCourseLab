package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.repository.CategoryRepository;
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
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    @Test
    void getAll_shouldReturnCategories() {
        List<Category> categories = List.of(
            new Category("Math", "Math courses"),
            new Category("Science", "Science courses")
        );
        when(repository.findAll()).thenReturn(categories);

        List<Category> result = service.getAll();

        assertEquals(2, result.size());
        verify(repository).findAll(); // покрываем вызов repository.findAll()
    }

    @Test
    void getById_shouldReturnCategory() {
        Category category = new Category("Math", "Math courses");
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        Category result = service.getById(1L);

        assertEquals(category, result);
        verify(repository).findById(1L);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getById(1L));
    }

    @Test
    void create_shouldSaveCategory() {
        Category category = new Category("Math", "Math courses");
        when(repository.save(category)).thenReturn(category);

        Category result = service.create(category);

        assertEquals(category, result);
        verify(repository).save(category);
    }

    @Test
    void update_shouldUpdateExistingCategory() {
        Category existing = new Category("Old", "Old description");
        Category update = new Category("New", "New description");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Category result = service.update(1L, update);

        assertEquals("New", result.getName());
        assertEquals("New description", result.getDescription());
        verify(repository).save(existing); // покрываем repository.save(existing)
    }

    @Test
    void update_shouldThrowIfNotFound() {
        Category update = new Category("New", "New description");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.update(1L, update));
    }

    @Test
    void delete_shouldDeleteIfExists() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L); // покрываем repository.deleteById()
    }

    @Test
    void delete_shouldThrowIfNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.delete(1L));
    }

    @Test
    void findByName_shouldReturnCategory() {
        Category category = new Category("Math", "Math courses");
        when(repository.findByName("Math")).thenReturn(Optional.of(category));

        Category result = service.findByName("Math");

        assertEquals(category, result);
        verify(repository).findByName("Math"); // покрываем repository.findByName()
    }

    @Test
    void findByName_shouldThrowIfNotFound() {
        when(repository.findByName("Math")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findByName("Math"));
    }
}