package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category not found with id " + id));
    }

    @Override
    public Category create(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        final Category existing = getById(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Category not found with id " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Category findByName(String name) {
        return repository.findByName(name)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category not found with name " + name));
    }
}