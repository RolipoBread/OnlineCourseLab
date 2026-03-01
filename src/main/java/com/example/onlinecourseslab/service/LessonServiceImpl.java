package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;

    @Override
    public List<Lesson> getAll() {
        return repository.findAll();
    }

    @Override
    public Lesson getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Lesson not found with id " + id));
    }

    @Override
    public Lesson create(Lesson lesson) {
        return repository.save(lesson);
    }

    @Override
    public Lesson update(Long id, Lesson lesson) {
        final Lesson existing = getById(id);
        existing.setTitle(lesson.getTitle());
        existing.setContent(lesson.getContent());
        existing.setOrderNumber(lesson.getOrderNumber());
        existing.setCourse(lesson.getCourse());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Lesson not found with id " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<Lesson> getByCourse(Course course) {
        return repository.findByCourse(course);
    }
}