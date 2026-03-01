package com.example.onlinecourseslab.repository;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    @EntityGraph(value = "User.courses", type = EntityGraph.EntityGraphType.LOAD)
    List<User> findAll();
}