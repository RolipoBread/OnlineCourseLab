package com.example.onlinecourseslab.service;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.repository.CourseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public Course getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));
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
        existing.setPrice(course.getPrice());
        existing.setLessonsCount(course.getLessonsCount());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Course not found with id " + id
            );
        }
        repository.deleteById(id);
    }

    @Override
    public List<Course> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }
}