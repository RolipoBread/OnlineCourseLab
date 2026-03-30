package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.dto.UserRequestDto;
import com.example.onlinecourseslab.dto.UserResponseDto;
import com.example.onlinecourseslab.mapper.UserMapper;
import com.example.onlinecourseslab.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "API для управления пользователями")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        final List<UserResponseDto> list = service.getAll()
            .stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(
        @PathVariable Long id) {

        return ResponseEntity.ok(
            mapper.toDto(service.getById(id)));
    }

    @Operation(summary = "Создать нового пользователя")
    @PostMapping
    public ResponseEntity<UserResponseDto> create(
        @Valid @RequestBody UserRequestDto dto) {

        final User saved =
            service.create(mapper.toEntity(dto));

        return ResponseEntity.status(201)
            .body(mapper.toDto(saved));
    }

    @Operation(summary = "Обновить пользователя по ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
        @PathVariable Long id,
        @Valid @RequestBody UserRequestDto dto) {

        final User updated =
            service.update(id, mapper.toEntity(dto));

        return ResponseEntity.ok(
            mapper.toDto(updated));
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Получить пользователей по роли")
    @GetMapping("/role")
    public ResponseEntity<List<UserResponseDto>> getByRole(
        @RequestParam String role) {

        final List<UserResponseDto> list = service.findByRole(role.toUpperCase())
            .stream()
            .map(mapper::toDto)
            .toList();

        return ResponseEntity.ok(list);
    }
}