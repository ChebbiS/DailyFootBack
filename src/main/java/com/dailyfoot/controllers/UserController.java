package com.dailyfoot.controllers;

import com.dailyfoot.dto.UpdateUserDTO;
import com.dailyfoot.dto.UpdateUserRequestDTO;
import com.dailyfoot.entities.User;
import com.dailyfoot.services.MailService;
import com.dailyfoot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @GetMapping("/me")
    public ResponseEntity<UpdateUserDTO> getCurrentUser(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();

        String email = authentication.getName();
        Optional<UpdateUserDTO> userOpt = userService.getUserDTOByEmail(email);

        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequestDTO request, Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(401).body("Token invalide");
        }

        String emailFromToken = authentication.getName();
        Optional<User> userOpt = userService.getUserByEmail(emailFromToken);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        }

        User user = userOpt.get();

        if (request.getName() != null) user.setName(request.getName());

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            Optional<User> existingUser = userService.getUserByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            if (request.getCurrentPassword() == null || !userService.matchesPassword(request.getCurrentPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Mot de passe actuel incorrect");
            }
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas");
            }
            user.setPassword(userService.encodePassword(request.getPassword()));
        }

        userService.saveUser(user);

        return ResponseEntity.ok(user);
    }
}

