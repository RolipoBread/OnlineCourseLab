package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings("checkstyle:EmptyLineSeparator")
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    // GET all
    @GetMapping
    public List<Course> getAll() {
        return service.getAll();
    }

    // GET by id
    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // GET with RequestParam (search)
    @GetMapping("/search")
    public List<Course> findByAuthor(@RequestParam String author) {
        return service.findByAuthor(author);
    }

    // POST
    @PostMapping
    public Course create(@RequestBody Course course) {
        return service.create(course);
    }

    // PUT
    @PutMapping("/{id}")
    public Course update(@PathVariable Long id,
                         @RequestBody Course course) {
        return service.update(id, course);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
