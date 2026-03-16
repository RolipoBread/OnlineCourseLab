package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;
    private final Map<Long, List<Lesson>> lessonCache = new HashMap<>();

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
        final Lesson saved = repository.save(lesson);
        lessonCache.remove(lesson.getCourse().getId());
        return saved;
    }

    @Override
    public Lesson update(Long id, Lesson lesson) {
        final Lesson existing = getById(id);
        existing.setTitle(lesson.getTitle());
        existing.setContent(lesson.getContent());
        existing.setOrderNumber(lesson.getOrderNumber());
        existing.setCourse(lesson.getCourse());
        final Lesson saved = repository.save(existing);
        lessonCache.remove(existing.getCourse().getId());
        return saved;
    }

    @Override
    public void delete(Long id) {
        final Lesson lesson = getById(id);
        repository.deleteById(id);
        lessonCache.remove(lesson.getCourse().getId());
    }

    @Override
    public List<Lesson> getByCourse(Long courseId) {

        if (lessonCache.containsKey(courseId)) {
            return lessonCache.get(courseId);
        }
        List<Lesson> lessons = repository.findByCourse_Id(courseId);
        lessonCache.put(courseId, lessons);

        return lessons;
    }
}