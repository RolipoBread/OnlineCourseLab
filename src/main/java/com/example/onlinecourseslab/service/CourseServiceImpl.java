package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.mapper.CourseMapper;
import com.example.onlinecourseslab.repository.CourseRepository;
import com.example.onlinecourseslab.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final ProgressRepository progressRepository;
    private final CategoryService categoryService;
    private final CourseMapper mapper;

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
    public Course create(CourseRequestDto dto) {

        final Course course = mapper.toEntity(dto);

        if (dto.getCategoryId() != null) {
            final Category category = categoryService.getById(dto.getCategoryId());
            course.setCategory(category);
        }

        return repository.save(course);
    }

    @Override
    public Course update(Long id, CourseRequestDto dto) {

        final Course existing = getById(id);

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setAuthor(dto.getAuthor());
        existing.setPrice(dto.getPrice());
        existing.setLessonCount(dto.getLessonCount());

        if (dto.getCategoryId() != null) {
            final Category category = categoryService.getById(dto.getCategoryId());
            existing.setCategory(category);
        } else {
            existing.setCategory(null);
        }

        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final Course course = getById(id);
        repository.delete(course);
    }

    @Override
    public List<Course> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    @Override
    public List<Course> findByCategory(String categoryName) {
        return repository.findByCategoryName(categoryName);
    }

    @Override
    public Page<Course> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


}