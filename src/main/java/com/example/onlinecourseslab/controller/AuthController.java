package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.dto.AuthRequestDto;
import com.example.onlinecourseslab.dto.UserResponseDto;
import com.example.onlinecourseslab.mapper.UserMapper;
import com.example.onlinecourseslab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody AuthRequestDto dto) {

        User user = service.findByEmail(dto.getEmail());

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }

        return ResponseEntity.ok(mapper.toDto(user));
    }
}
