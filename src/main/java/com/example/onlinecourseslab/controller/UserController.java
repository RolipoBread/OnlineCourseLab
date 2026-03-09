package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.dto.UserRequestDto;
import com.example.onlinecourseslab.dto.UserResponseDto;
import com.example.onlinecourseslab.mapper.UserMapper;
import com.example.onlinecourseslab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        final List<UserResponseDto> list = service.getAll()
            .stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(
        @PathVariable Long id) {
        return ResponseEntity.ok(
            mapper.toDto(service.getById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(
        @RequestBody UserRequestDto dto) {
        final User saved =
            service.create(mapper.toEntity(dto));
        return ResponseEntity.status(201)
            .body(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
        @PathVariable Long id,
        @RequestBody UserRequestDto dto) {
        final User updated =
            service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(
            mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @DeleteMapping("/test/{id}")
    public void deleteTest(@PathVariable Long id) {
        service.deleteTest(id);
    }
}