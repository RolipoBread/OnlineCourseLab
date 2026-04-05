package com.example.onlinecourseslab.exception;

import com.example.onlinecourseslab.controller.UserController;
import com.example.onlinecourseslab.service.UserService;
import com.example.onlinecourseslab.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @Test
    void shouldReturn404_whenUserNotFound() throws Exception {
        when(service.getById(1L))
            .thenThrow(new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "User not found"));

        mockMvc.perform(get("/users/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturn500_whenUnexpectedError() throws Exception {
        when(service.getById(1L))
            .thenThrow(new RuntimeException("Boom"));

        mockMvc.perform(get("/users/1"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status").value(500));
    }
}