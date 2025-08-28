package com.dailyfoot.controllers;

import com.dailyfoot.config.JwtUtil;
import com.dailyfoot.dto.*;
import com.dailyfoot.entities.Player;
import com.dailyfoot.entities.User;
import com.dailyfoot.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterRequestResponse> register(@Valid @RequestBody RegisterRequest request) {
        User newUser = authService.register(request);
        return ResponseEntity.ok(new RegisterRequestResponse("Votre compte a été avec succès !"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request);
        if (user == null) {
            return ResponseEntity.status(401).body(new LoginResponse("Identifiants invalides", null));
        }
        String token = jwtUtil.generateToken(user.getEmail()); // email = username
        return ResponseEntity.ok(new LoginResponse(user.getEmail(), token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "logout successful"));
    }

    @PostMapping("/loginPlayer")
    public ResponseEntity<PlayerLoginResponse> loginPlayer(@Valid @RequestBody LoginPlayerRequest request) {
        Player player = authService.loginPlayer(request.getCodeAccess());
        if (player == null) {
            return ResponseEntity.status(401).build(); // Gerer l'exception
        }
        PlayerLoginResponse response = new PlayerLoginResponse(
                player.getName(),
                player.getClub(),
                player.getImage(),
                player.getNationality(),
                player.getAge(),
                player.getPoste()
        );
        return ResponseEntity.ok(response);
    }
}
