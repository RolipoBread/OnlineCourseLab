package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ProgressService progressService; // ДОБАВЛЯЕМ

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id " + id));
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        final User existing = getById(id);

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setRole(user.getRole());

        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final User user = getById(id);

        progressService.deleteByStudent(user);

        repository.delete(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with email " + email));
    }
}