package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository repository;

    @Override
    @Transactional
    public Progress markCompleted(User student, Lesson lesson) {
        final Progress progress = repository.findByStudentAndLesson(student, lesson)
            .orElse(new Progress(student, lesson, false));
        progress.setCompleted(true);
        return repository.save(progress);
    }

    @Override
    public List<Progress> getByStudent(User student) {
        return repository.findByStudent(student);
    }

    @Override
    public List<Progress> getByStudentAndCourse(User student, Course course) {
        return repository.findByStudentAndLessonCourse(student, course);
    }

    @Override
    public Progress getByStudentAndLesson(User student, Lesson lesson) {
        return repository.findByStudentAndLesson(student, lesson)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Progress not found for student and lesson"));
    }

    @Override
    @Transactional
    public void deleteByLessons(List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            final List<Progress> progresses = repository.findByLessonId(lesson.getId());
            if (!progresses.isEmpty()) {
                repository.deleteAll(progresses);
            }
        }
    }

    @Override
    @Transactional
    public void deleteByStudent(User student) {
        final List<Progress> progresses = repository.findByStudent(student);
        if (!progresses.isEmpty()) {
            repository.deleteAll(progresses);
        }
    }
}