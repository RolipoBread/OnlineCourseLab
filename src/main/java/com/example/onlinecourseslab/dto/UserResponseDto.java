package com.example.onlinecourseslab.dto;

import com.example.onlinecourseslab.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String avatarUrl;
    private List<CourseResponseDto> courses;
}