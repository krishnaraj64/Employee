package com.example.EmpApp.Security;

import com.example.EmpApp.Entity.Employee;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final long accessTokenValidity = 1000 * 60 * 60; // 1 hour
    private final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 7; // 7 days

    private final Key signingKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ✅ Generates access token with custom claims
    public String generateAccessToken(Employee employee) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("empId", employee.getEmpId());
        claims.put("empName", employee.getEmpName());
        return generateToken(claims, employee.getEmail(), accessTokenValidity);
    }

    public String generateRefreshToken(Employee employee) {
        return generateToken(new HashMap<>(), employee.getEmail(), refreshTokenValidity);
    }

    private String generateToken(Map<String, Object> claims, String subject, long duration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
