package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.repository.CourseRepository;
import com.example.onlinecourseslab.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final ProgressRepository progressRepository; // Добавлено для каскадного удаления

    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public Course getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Course not found with id " + id));
    }

    @Override
    public Course create(Course course) {
        return repository.save(course);
    }

    @Override
    public Course update(Long id, Course course) {
        final Course existing = getById(id);
        existing.setTitle(course.getTitle());
        existing.setAuthor(course.getAuthor());
        existing.setDescription(course.getDescription());
        existing.setPrice(course.getPrice());
        existing.setLessonCount(course.getLessonCount());
        existing.setCategory(course.getCategory()); // Обновляем категорию
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final Course course = getById(id);

        final List<Long> lessonIds = course.getLessons().stream()
            .map(Lesson::getId)
            .toList();

        if (!lessonIds.isEmpty()) {
            progressRepository.deleteAllByLessonIds(lessonIds);
        }

        repository.delete(course);
    }

    @Override
    public List<Course> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    @Override
    @Transactional
    public Course createCourseWithLessons(Course course) {
        if (course.getLessons() != null) {
            for (Lesson lesson : course.getLessons()) {
                lesson.setCourse(course);
            }
        }
        return repository.save(course);
    }
}