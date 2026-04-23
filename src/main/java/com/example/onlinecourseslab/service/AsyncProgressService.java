package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.TaskStatus;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncProgressService {

    private final ProgressService progressService;
    private final UserService userService;
    private final LessonService lessonService;
    private final TaskService taskService;

    @Async
    public void markCompletedAsync(String taskId, Long studentId, Long lessonId) {

        try {
            taskService.setStatus(taskId, TaskStatus.IN_PROGRESS);

            User student = userService.getById(studentId);
            Lesson lesson = lessonService.getById(lessonId);

            Thread.sleep(3000);

            progressService.markCompleted(student, lesson);

            taskService.setStatus(taskId, TaskStatus.DONE);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            taskService.setStatus(taskId, TaskStatus.FAILED);

        } catch (RuntimeException e) {
            taskService.setStatus(taskId, TaskStatus.FAILED);
        }
    }
}