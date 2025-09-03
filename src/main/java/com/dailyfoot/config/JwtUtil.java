package com.dailyfoot.config;

import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.User;
import com.dailyfoot.repositories.AgentRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Autowired
    private AgentRepository agentRepository;
    private final String SECRET_KEY = "D76ai6447L9456786YF3113457965OO980T"; // au moins 32 chars
    private final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 heures

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Agent getCurrentAgent() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer agentId = userDetails.getAgentId();
        return agentRepository.findByUserId(agentId)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé")); // Capter l'exception
    }
}
