package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.ProgressRequestDto;
import com.example.onlinecourseslab.dto.ProgressResponseDto;
import com.example.onlinecourseslab.mapper.ProgressMapper;
import com.example.onlinecourseslab.service.ProgressService;
import com.example.onlinecourseslab.service.UserService;
import com.example.onlinecourseslab.service.LessonService;
import com.example.onlinecourseslab.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;
    private final UserService userService;
    private final LessonService lessonService;
    private final CourseService courseService;
    private final ProgressMapper mapper;

    @PostMapping("/complete")
    public ResponseEntity<ProgressResponseDto> markCompleted(@RequestBody ProgressRequestDto dto) {
        final User student = userService.getById(dto.getStudentId());
        final Lesson lesson = lessonService.getById(dto.getLessonId());
        final Progress progress = progressService.markCompleted(student, lesson);
        return ResponseEntity.ok(mapper.toDto(progress));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ProgressResponseDto>> getByStudent(@PathVariable Long studentId) {
        final User student = userService.getById(studentId);
        final List<Progress> progresses = progressService.getByStudent(student);
        final List<ProgressResponseDto> dtos = progresses.stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<ProgressResponseDto>> getByStudentAndCourse(@PathVariable Long studentId,
                                                                           @PathVariable Long courseId) {
        final User student = userService.getById(studentId);
        final Course course = courseService.getById(courseId);
        final List<Progress> progresses = progressService.getByStudentAndCourse(student, course);
        final List<ProgressResponseDto> dtos = progresses.stream()
            .map(mapper::toDto)
            .toList();
        return ResponseEntity.ok(dtos);
    }
}