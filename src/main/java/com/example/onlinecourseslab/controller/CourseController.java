package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import com.example.onlinecourseslab.mapper.CourseMapper;
import com.example.onlinecourseslab.service.CourseService;
import com.example.onlinecourseslab.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;
    private final CourseMapper mapper;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getCourses(
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String category,
        Pageable pageable) {

        final List<Course> courses;

        if (author != null && !author.isBlank()) {
            courses = service.findByAuthor(author);
        } else if (category != null && !category.isBlank()) {
            courses = service.findByCategory(category);
        } else {
            courses = service.getAll(pageable).getContent();
        }

        final List<CourseResponseDto> list = courses.stream()
            .map(mapper::toDto)
            .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }


    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CourseRequestDto dto) {
        final Course course = service.create(dto);

        return ResponseEntity.status(201).body(mapper.toDto(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(
        @PathVariable Long id,
        @RequestBody CourseRequestDto dto) {

        final Course updated = service.update(id, dto);

        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}