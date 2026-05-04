package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.dto.LessonCacheKeyDto;
import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import com.example.onlinecourseslab.mapper.LessonMapper;
import com.example.onlinecourseslab.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;
    private final CourseService courseService;
    private final LessonMapper mapper;

    private final Map<LessonCacheKeyDto, Page<Lesson>> lessonCache = new ConcurrentHashMap<>();

    @Override
    public Page<Lesson> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Lesson getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Lesson not found with id " + id
                )
            );
    }

    @Override
    public Lesson create(Lesson lesson) {
        Lesson saved = repository.save(lesson);
        lessonCache.clear();
        return saved;
    }

    @Override
    public Lesson update(Long id, Lesson lesson) {
        Lesson existing = getById(id);

        existing.setTitle(lesson.getTitle());
        existing.setContent(lesson.getContent());
        existing.setOrderNumber(lesson.getOrderNumber());
        existing.setCourse(lesson.getCourse());

        Lesson saved = repository.save(existing);
        lessonCache.clear();
        return saved;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        lessonCache.clear();
    }

    // =========================
    // 🔥 FIXED PAGINATION + CACHE
    // =========================
    @Override
    public Page<Lesson> getByCourse(Course course, Pageable pageable) {

        LessonCacheKeyDto key = new LessonCacheKeyDto(
            course.getId(),
            pageable.getPageNumber(),
            pageable.getPageSize()
        );

        if (lessonCache.containsKey(key)) {
            log.info("Cache hit");
            return lessonCache.get(key);
        }

        log.info("SQL hit");

        Page<Lesson> page = repository.findByCourse(course, pageable);

        lessonCache.put(key, page);

        return page;
    }

    // =========================
    // BULK (оставил, но упростил)
    // =========================

    @Override
    @Transactional
    public List<LessonResponseDto> addLessonsBulkTransactional(List<LessonRequestDto> dtos) {

        return dtos.stream()
            .map(dto -> {
                Course course = courseService.getById(dto.getCourseId());
                return mapper.toEntity(dto, course);
            })
            .map(repository::save)
            .map(mapper::toDto)
            .toList();
    }

    @Override
    public List<LessonResponseDto> addLessonsBulkNonTransactional(List<LessonRequestDto> dtos) {

        return dtos.stream()
            .map(dto -> {
                Course course = courseService.getById(dto.getCourseId());
                return mapper.toEntity(dto, course);
            })
            .map(repository::save)
            .map(mapper::toDto)
            .toList();
    }
}