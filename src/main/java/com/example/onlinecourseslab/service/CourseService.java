    package com.example.onlinecourseslab.service;

    import com.example.onlinecourseslab.domain.Course;
    import com.example.onlinecourseslab.dto.CourseRequestDto;
    import org.springframework.data.domain.Page;

    import org.springframework.data.domain.Pageable;
    import java.util.List;

    public interface CourseService {
        List<Course> getAll();
        Course getById(Long id);
        Course create(CourseRequestDto dto);
        Course update(Long id, CourseRequestDto dto);
        void delete(Long id);
        List<Course> findByAuthor(String author);
        List<Course> findByCategory(String categoryName);
        Page<Course> getAll(Pageable pageable);
    }