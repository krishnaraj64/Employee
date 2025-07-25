package com.example.EmpApp.Controll;

import com.example.EmpApp.Entity.Employee;
import com.example.EmpApp.Repository.EmployeeRepository;
import com.example.EmpApp.Security.JwtUtil;
import com.example.EmpApp.dto.LoginDTO;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmployeeRepository employeeRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDto) {
        // 1. Authenticate user
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 2. Load employee by email
        Employee employee = employeeRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + loginDto.getEmail()));

        // 3. Generate tokens
        String accessToken = jwtUtil.generateAccessToken(employee);
        String refreshToken = jwtUtil.generateRefreshToken(employee);

        // 4. Extract expiration time
        Claims claims = jwtUtil.getClaims(accessToken);
        long expiresAtMillis = claims.getExpiration().getTime();

        String expiresAtFormatted = Instant.ofEpochMilli(expiresAtMillis)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 5. Build response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("email", employee.getEmail());
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("accessTokenExpiresAt", expiresAtFormatted);

        return ResponseEntity.ok(response);
    }
}
