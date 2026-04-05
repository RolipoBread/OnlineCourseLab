package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.dto.UserRequestDto;
import com.example.onlinecourseslab.dto.UserResponseDto;
import com.example.onlinecourseslab.mapper.UserMapper;
import com.example.onlinecourseslab.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        when(service.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk());
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        UserRequestDto request = new UserRequestDto();

        when(service.create(any())).thenReturn(null);
        when(mapper.toDto(any())).thenReturn(new UserResponseDto());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }
}