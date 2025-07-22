package com.example.EmpApp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class EmployeeDTO {

    private Long empId;

    @NotBlank(message = "Employee name is required")
    private String empName;

    @Email(message = "Please enter a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @NotBlank(message = "Phone number is required")
    private String phoneNo;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @Valid
    @NotNull(message = "Address is required")
    private AddressDTO address;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    private Set<Long> projectIds;
}
