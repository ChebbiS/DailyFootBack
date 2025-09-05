package com.dailyfoot.dto;

import com.dailyfoot.entities.User.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {
    @Email
    @NotBlank(message = "Un Email est obligatoire")
    private String email;
    @NotNull(message = "Un mot de passe est obligatoire")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$",
            message = "Le mot de passe doit contenir au minimum 8 caractères, avec au moins une majuscule, une minuscule, un chiffre et un caractère spécial"
    )
    private String password;
    @NotBlank(message = "Un nom est obligatoire")
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

