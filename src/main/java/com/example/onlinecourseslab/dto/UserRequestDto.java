package com.example.onlinecourseslab.dto;

import com.example.onlinecourseslab.domain.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @Size(min = 6)
    private String password;
    @NotNull
    private Role role;
}