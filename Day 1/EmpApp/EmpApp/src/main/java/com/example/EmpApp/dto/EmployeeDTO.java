package com.example.EmpApp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDTO {

    private Long empid;

    @NotBlank(message = "Employee name is required")
    private String empname;

    @Email(message = "Please enter a valid email address")
    @NotBlank(message = "Email is required")
    private String Email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @NotBlank(message = "Phone number is required")
    private String PhoneNo;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "Password is required")
    private String password;
}