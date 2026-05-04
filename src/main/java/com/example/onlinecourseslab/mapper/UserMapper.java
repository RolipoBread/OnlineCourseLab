package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.dto.CourseResponseDto;
import com.example.onlinecourseslab.dto.UserRequestDto;
import com.example.onlinecourseslab.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {
        final User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }

    public UserResponseDto toDto(User user) {
        final UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAvatarUrl(user.getAvatarUrl());

        dto.setCourses(
            user.getCourses()
                .stream()
                .map(course -> {
                    final CourseResponseDto c = new CourseResponseDto();
                    c.setId(course.getId());
                    c.setTitle(course.getTitle());
                    c.setDescription(course.getDescription());
                    c.setAuthor(course.getAuthor());
                    c.setCategoryColor(course.getCategory().getColor());
                    return c;
                })
                .toList()
        );
        return dto;
    }
}