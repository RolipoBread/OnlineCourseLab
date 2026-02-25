package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import com.example.onlinecourseslab.service.CourseService;
import com.example.onlinecourseslab.mapper.CourseMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;
    private final CourseMapper mapper;

    // GET all
    @GetMapping
    public List<CourseResponseDto> getAll() {
        return service.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // GET by id
    @GetMapping("/{id}")
    public CourseResponseDto getById(@PathVariable Long id) {
        Course course = service.getById(id);
        return mapper.toDto(course);
    }

    // GET with RequestParam (search)
    @GetMapping("/search")
    public List<CourseResponseDto> findByAuthor(@RequestParam String author) {
        return service.findByAuthor(author)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // POST
    @PostMapping
    public CourseResponseDto create(@RequestBody CourseRequestDto dto) {
        Course course = mapper.toEntity(dto);
        Course saved = service.create(course);
        return mapper.toDto(saved);
    }

    // PUT
    @PutMapping("/{id}")
    public CourseResponseDto update(@PathVariable Long id,
                                    @RequestBody CourseRequestDto dto) {
        Course course = mapper.toEntity(dto);
        Course updated = service.update(id, course);
        return mapper.toDto(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}