package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.dto.UserRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);

    User create(User user);

    User update(Long id, User user);

    void delete(Long id);

    User findByEmail(String email);

    List<User> findByRole(String role);

    User updateAvatar(Long userId, MultipartFile file);

    User addCourse(Long userId, Long courseId);

    User removeCourse(Long userId, Long courseId);

    }