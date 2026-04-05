package com.example.onlinecourseslab.controller;

import com.example.onlinecourseslab.dto.CategoryRequestDto;
import com.example.onlinecourseslab.dto.CategoryResponseDto;
import com.example.onlinecourseslab.mapper.CategoryMapper;
import com.example.onlinecourseslab.service.CategoryService;
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

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @MockBean
    private CategoryMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        when(service.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/categories"))
            .andExpect(status().isOk());
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        CategoryRequestDto request = new CategoryRequestDto("IT", "desc");
        CategoryResponseDto response = new CategoryResponseDto(1L, "IT", "desc");

        when(service.create(any())).thenReturn(null);
        when(mapper.toDto(any())).thenReturn(response);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }
}