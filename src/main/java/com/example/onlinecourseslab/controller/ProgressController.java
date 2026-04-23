package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.ProgressRequestDto;
import com.example.onlinecourseslab.dto.ProgressResponseDto;
import com.example.onlinecourseslab.mapper.ProgressMapper;
import com.example.onlinecourseslab.service.*;
import com.example.onlinecourseslab.domain.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Progress", description = "API для отслеживания прогресса студентов")
@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;
    private final UserService userService;
    private final LessonService lessonService;
    private final CourseService courseService;
    private final ProgressMapper mapper;
    private final AsyncProgressService asyncProgressService;
    private final TaskService taskService;

    @Operation(
        summary = "Асинхронно отметить урок как завершённый",
        description = "Создаёт фоновую задачу для отметки урока как завершённого. Возвращает taskId для отслеживания статуса."
    )
    @PostMapping("/complete")
    public ResponseEntity<String> markCompletedAsync(
        @Valid @RequestBody ProgressRequestDto dto) {

        String taskId = taskService.createTask();

        asyncProgressService.markCompletedAsync(
            taskId,
            dto.getStudentId(),
            dto.getLessonId()
        );

        return ResponseEntity.accepted().body(taskId);
    }

    @Operation(summary = "Получить прогресс студента по всем урокам")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ProgressResponseDto>> getByStudent(
        @PathVariable Long studentId) {

        final User student = userService.getById(studentId);
        final List<Progress> progresses = progressService.getByStudent(student);

        final List<ProgressResponseDto> dtos = progresses.stream()
            .map(mapper::toDto)
            .toList();

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Получить прогресс студента по конкретному курсу")
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<ProgressResponseDto>> getByStudentAndCourse(
        @PathVariable Long studentId,
        @PathVariable Long courseId) {

        final User student = userService.getById(studentId);
        final Course course = courseService.getById(courseId);
        final List<Progress> progresses =
            progressService.getByStudentAndCourse(student, course);

        final List<ProgressResponseDto> dtos = progresses.stream()
            .map(mapper::toDto)
            .toList();

        return ResponseEntity.ok(dtos);
    }
    @Operation(
        summary = "Получить статус асинхронной задачи",
        description = "Позволяет узнать текущий статус выполнения задачи по её taskId"
    )
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskStatus> getTaskStatus(@PathVariable String taskId) {
        return ResponseEntity.ok(taskService.getStatus(taskId));
    }

}