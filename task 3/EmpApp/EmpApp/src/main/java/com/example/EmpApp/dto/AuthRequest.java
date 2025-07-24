package com.example.EmpApp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
