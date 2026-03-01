package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import com.example.onlinecourseslab.mapper.CourseMapper;
import com.example.onlinecourseslab.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;
    private final CourseMapper mapper;

    // GET all
    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAll() {
        final List<CourseResponseDto> list = service.getAll()
                .stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(list);
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    // GET by author (search)
    @GetMapping("/search")
    public ResponseEntity<List<CourseResponseDto>> findByAuthor(@RequestParam String author) {
        final List<CourseResponseDto> list = service.findByAuthor(author)
                .stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(list);
    }

    // POST create
    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CourseRequestDto dto) {
        final Course saved = service.create(mapper.toEntity(dto));
        return ResponseEntity.status(201).body(mapper.toDto(saved));
    }

    // PUT update
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(@PathVariable Long id,
                                                    @RequestBody CourseRequestDto dto) {
        final Course updated = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}