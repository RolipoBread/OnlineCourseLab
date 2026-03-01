package com.example.onlinecourseslab.mapper;

import com.example.onlinecourseslab.domain.Category;
import com.example.onlinecourseslab.dto.CategoryRequestDto;
import com.example.onlinecourseslab.dto.CategoryResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDto dto) {
        final Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public CategoryResponseDto toDto(Category category) {
        final CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}