package com.example.EmpApp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String accessToken;
    private String refreshToken;
}
