package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository repository;

    @Override
    public Progress markCompleted(User student, Lesson lesson) {
        Progress progress = repository.findByStudentAndLesson(student, lesson)
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
        return repository.findByStudentAndLesson_Course(student, course);
    }

    @Override
    public Progress getByStudentAndLesson(User student, Lesson lesson) {
        return repository.findByStudentAndLesson(student, lesson)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Progress not found for student and lesson"));
    }
}