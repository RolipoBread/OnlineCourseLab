package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import com.example.onlinecourseslab.mapper.LessonMapper;
import com.example.onlinecourseslab.service.LessonService;
import com.example.onlinecourseslab.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final LessonMapper mapper;

    @GetMapping
    public ResponseEntity<List<LessonResponseDto>> getAll() {
        final List<LessonResponseDto> list = lessonService.getAll()
            .stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(lessonService.getById(id)));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonResponseDto>> getByCourse(@PathVariable Long courseId) {
        final List<LessonResponseDto> list = lessonService.getByCourse(courseId)
            .stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<LessonResponseDto> create(@RequestBody LessonRequestDto dto) {
        final Course course = courseService.getById(dto.getCourseId());
        final Lesson lesson = lessonService.create(mapper.toEntity(dto, course));
        return ResponseEntity.status(201).body(mapper.toDto(lesson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonResponseDto> update(@PathVariable Long id,
                                                    @RequestBody LessonRequestDto dto) {
        final Course course = courseService.getById(dto.getCourseId());
        final Lesson lesson = lessonService.update(id, mapper.toEntity(dto, course));
        return ResponseEntity.ok(mapper.toDto(lesson));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lessonService.delete(id);
    }
}