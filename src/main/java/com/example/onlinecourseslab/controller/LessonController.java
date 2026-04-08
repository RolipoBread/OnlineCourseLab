package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import com.example.onlinecourseslab.mapper.LessonMapper;
import com.example.onlinecourseslab.service.LessonService;
import com.example.onlinecourseslab.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lessons", description = "API для управления уроками")
@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final LessonMapper mapper;

    @Operation(summary = "Получить список всех уроков")
    @GetMapping
    public ResponseEntity<List<LessonResponseDto>> getAll() {
        final List<LessonResponseDto> list = lessonService.getAll()
            .stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Получить урок по ID")
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(lessonService.getById(id)));
    }

    @Operation(summary = "Получить уроки по курсу с пагинацией")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonResponseDto>> getByCourse(
        @PathVariable Long courseId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        final Course course = courseService.getById(courseId);

        final List<LessonResponseDto> list = lessonService
            .getByCourse(course, page, size)
            .stream()
            .map(mapper::toDto)
            .toList();

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Создать новый урок")
    @PostMapping
    public ResponseEntity<LessonResponseDto> create(
        @Valid @RequestBody LessonRequestDto dto) {

        final Course course = courseService.getById(dto.getCourseId());
        final Lesson lesson = lessonService.create(mapper.toEntity(dto, course));

        return ResponseEntity.status(201).body(mapper.toDto(lesson));
    }

    @Operation(summary = "Обновить урок по ID")
    @PutMapping("/{id}")
    public ResponseEntity<LessonResponseDto> update(
        @PathVariable Long id,
        @Valid @RequestBody LessonRequestDto dto) {

        final Course course = courseService.getById(dto.getCourseId());
        final Lesson lesson = lessonService.update(id, mapper.toEntity(dto, course));

        return ResponseEntity.ok(mapper.toDto(lesson));
    }

    @Operation(summary = "Удалить урок по ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lessonService.delete(id);
    }

    // ----------------- BULK-ОПЕРАЦИИ -----------------

    @Operation(summary = "Создать несколько уроков (транзакционно)")
    @PostMapping("/bulk/transactional")
    public ResponseEntity<List<LessonResponseDto>> createBulkTransactional(
        @Valid @RequestBody List<LessonRequestDto> dtos) {

        final List<LessonResponseDto> saved = lessonService.addLessonsBulkTransactional(dtos);
        return ResponseEntity.status(201).body(saved);
    }

    @Operation(summary = "Создать несколько уроков (без транзакции)")
    @PostMapping("/bulk/non-transactional")
    public ResponseEntity<List<LessonResponseDto>> createBulkNonTransactional(
        @Valid @RequestBody List<LessonRequestDto> dtos) {

        final List<LessonResponseDto> saved = lessonService.addLessonsBulkNonTransactional(dtos);
        return ResponseEntity.status(201).body(saved);
    }
}