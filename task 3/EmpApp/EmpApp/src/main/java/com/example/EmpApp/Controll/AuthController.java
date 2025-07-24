package com.example.EmpApp.Controll;

import com.example.EmpApp.Entity.Employee;
import com.example.EmpApp.Repository.EmployeeRepository;
import com.example.EmpApp.Security.JwtUtil;
import com.example.EmpApp.dto.AuthRequest;
import com.example.EmpApp.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        Employee emp = employeeRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), emp.getPassword())) {
            return ResponseEntity.status(401).body(
                    new AuthResponse("Invalid email or password", null, null)
            );
        }

        String accessToken = jwtUtil.generateAccessToken(emp.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(emp.getEmail());

        return ResponseEntity.ok(
                new AuthResponse("Login successful", accessToken, refreshToken)
        );
    }
}
