package com.dailyfoot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        return "Utilisateur inscrit : " + body.get("email");
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        return "Connexion réussie pour : " + body.get("email");
    }

    @PostMapping("/logout")
    public String logout() {
        return "Déconnexion réussie";
    }
}
