package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);
    User findByEmail(String email);
    void deleteTest(Long id);
}