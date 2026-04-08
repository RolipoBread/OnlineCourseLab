package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.dto.CategoryRequestDto;
import com.example.onlinecourseslab.dto.CategoryResponseDto;
import com.example.onlinecourseslab.mapper.CategoryMapper;
import com.example.onlinecourseslab.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "API для управления категориями курсов")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @Operation(summary = "Получить список всех категорий")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        final List<CategoryResponseDto> list = service.getAll()
            .stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Получить категорию по ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }
    
    @Operation(summary = "Создать новую категорию")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(
        @Valid @RequestBody CategoryRequestDto dto) {

        final Category category = service.create(mapper.toEntity(dto));
        return ResponseEntity.status(201).body(mapper.toDto(category));
    }

    @Operation(summary = "Обновить категорию по ID")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(
        @PathVariable Long id,
        @Valid @RequestBody CategoryRequestDto dto) {

        final Category category = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDto(category));
    }

    @Operation(summary = "Удалить категорию по ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}