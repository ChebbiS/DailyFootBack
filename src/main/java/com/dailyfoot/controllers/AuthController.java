package com.dailyfoot.controllers;

import com.dailyfoot.config.JwtUtil;
import com.dailyfoot.config.PasswordUtil;
import com.dailyfoot.dto.*;
import com.dailyfoot.entities.User;
import com.dailyfoot.repositories.UserRepository;
import com.dailyfoot.services.AuthService;
import com.dailyfoot.services.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private  AuthService authService;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email requis");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Aucun utilisateur trouvé pour cet email");
        }

        User user = userOptional.get();
        String newPassword = PasswordUtil.generateRandomPassword(10);

        // Hash du mot de passe
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        mailService.sendForgotPasswordEmail(user.getEmail(), user.getName(), newPassword);

        return ResponseEntity.ok("Un nouveau mot de passe a été envoyé à votre email.");
    }

}
