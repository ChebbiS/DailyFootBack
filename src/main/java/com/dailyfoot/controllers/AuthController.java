package com.dailyfoot.controllers;

import com.dailyfoot.config.JwtUtil;
import com.dailyfoot.dto.*;
import com.dailyfoot.entities.User;
import com.dailyfoot.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private  AuthService authService;


    @PostMapping("/register/agent")
    public ResponseEntity<RegisterRequestDTO> registerAgent(@Valid @RequestBody RegisterDTO request) {
        authService.register(request, User.Role.AGENT);
        return ResponseEntity.ok(new RegisterRequestDTO("Votre compte a été crée avec succès !"));
    }

    @PostMapping("/register/player/{agentId}")
    public ResponseEntity<RegisterRequestDTO> registerPlayer(@Valid @RequestBody RegisterDTO request, @PathVariable Integer agentId) {
        authService.register(request, User.Role.PLAYER, agentId);
        return ResponseEntity.ok(new RegisterRequestDTO("Votre compte a été crée avec succès !"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        User user = authService.login(request);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name()); // email = username
        return ResponseEntity.ok(new LoginDTO(user.getEmail(), token));
    }


}
