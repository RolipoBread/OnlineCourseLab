package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.dto.CategoryRequestDto;
import com.example.onlinecourseslab.dto.CategoryResponseDto;
import com.example.onlinecourseslab.mapper.CategoryMapper;
import com.example.onlinecourseslab.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        final List<CategoryResponseDto> list = service.getAll()
            .stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryRequestDto dto) {
        final Category category = service.create(mapper.toEntity(dto));
        return ResponseEntity.status(201).body(mapper.toDto(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id,
                                                      @RequestBody CategoryRequestDto dto) {
        final Category category = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDto(category));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}