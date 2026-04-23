package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.LessonCacheKeyDto;
import com.example.onlinecourseslab.dto.LessonRequestDto;
import com.example.onlinecourseslab.dto.LessonResponseDto;
import com.example.onlinecourseslab.mapper.LessonMapper;
import com.example.onlinecourseslab.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;
    private final CourseService courseService;
    private final LessonMapper mapper;
    private final Map<LessonCacheKeyDto, List<Lesson>> lessonCache = new ConcurrentHashMap<>();

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
        lessonCache.clear();
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
        lessonCache.clear();
        return saved;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        lessonCache.clear();
    }

    @Override
    public List<Lesson> getByCourse(Course course, int page, int size) {
        final LessonCacheKeyDto key = new LessonCacheKeyDto(course.getId(), page, size);

        if (lessonCache.containsKey(key)) {
            log.info("Cache hit");
            return lessonCache.get(key);
        }

        log.info("SQL hit");
        final Pageable pageable = PageRequest.of(page, size);
        final List<Lesson> lessons = repository
            .findByCourse(course, pageable)
            .getContent();

        lessonCache.put(key, lessons);
        return lessons;
    }

    @Override
    @Transactional
    public List<LessonResponseDto> addLessonsBulkTransactional(List<LessonRequestDto> dtos) {
        return dtos.stream()
            .map(dto -> Optional.ofNullable(courseService.getById(dto.getCourseId()))
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Course not found with id " + dto.getCourseId())))
            .map(course -> dtos.stream()
                .filter(d -> d.getCourseId().equals(course.getId()))
                .map(d -> mapper.toEntity(d, course))
                .toList())
            .flatMap(List::stream)
            .map(repository::save)
            .map(mapper::toDto)
            .toList();
    }

    @Override
    public List<LessonResponseDto> addLessonsBulkNonTransactional(List<LessonRequestDto> dtos) {
        return dtos.stream()
            .map(dto -> Optional.ofNullable(courseService.getById(dto.getCourseId()))
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Course not found with id " + dto.getCourseId())))
            .map(course -> dtos.stream()
                .filter(d -> d.getCourseId().equals(course.getId()))
                .map(d -> mapper.toEntity(d, course))
                .toList())
            .flatMap(List::stream)
            .map(repository::save)
            .map(mapper::toDto)
            .toList();
    }
}