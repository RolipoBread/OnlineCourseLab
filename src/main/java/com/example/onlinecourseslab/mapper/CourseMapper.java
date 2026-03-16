    package com.example.onlinecourseslab.mapper;

    import com.example.onlinecourseslab.domain.Course;
    import com.example.onlinecourseslab.dto.CourseRequestDto;
    import com.example.onlinecourseslab.dto.CourseResponseDto;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Component;

    @Component
    @RequiredArgsConstructor
    public class CourseMapper {

        public Course toEntity(CourseRequestDto dto) {
            final Course course = new Course();

            course.setTitle(dto.getTitle());
            course.setDescription(dto.getDescription());
            course.setAuthor(dto.getAuthor());
            course.setPrice(dto.getPrice());
            course.setLessonCount(dto.getLessonCount());

            return course;
        }

        public CourseResponseDto toDto(Course course) {
            final CourseResponseDto dto = new CourseResponseDto();

            dto.setId(course.getId());
            dto.setTitle(course.getTitle());
            dto.setAuthor(course.getAuthor());
            dto.setPrice(course.getPrice());
            dto.setLessonCount(course.getLessonCount());

            if (course.getCategory() != null) {
                dto.setCategoryId(course.getCategory().getId());
                dto.setCategoryName(course.getCategory().getName());
            }

            return dto;
        }
    }