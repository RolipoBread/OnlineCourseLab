<<<<<<< HEAD
package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDto dto) {
        final Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAuthor(dto.getAuthor());
        course.setPrice(dto.getPrice());
        course.setLessonsCount(dto.getLessonsCount());
        return course;
    }
    public CourseResponseDto toDto(Course course) {
        final CourseResponseDto dto = new CourseResponseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setAuthor(course.getAuthor());
        dto.setPrice(course.getPrice());
        dto.setLessonsCount(course.getLessonsCount());
        return dto;
    }
}
=======
package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Course;
import com.example.onlinecourseslab.dto.CourseRequestDto;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDto dto) {
        final Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAuthor(dto.getAuthor());
        course.setPrice(dto.getPrice());
        course.setLessonsCount(dto.getLessonsCount());
        return course;
    }
    public CourseResponseDto toDto(Course course) {
        final CourseResponseDto dto = new CourseResponseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setAuthor(course.getAuthor());
        dto.setPrice(course.getPrice());
        dto.setLessonsCount(course.getLessonsCount());
        return dto;
    }
}
>>>>>>> 6c85d3303b4ccd8764bf92559f3314f4bec1f888
