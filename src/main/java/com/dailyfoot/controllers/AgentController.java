package com.dailyfoot.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class AgentController {

    @PutMapping("/{id}/password")
    public String updatePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return "Mot de passe modifié pour l’utilisateur " + id;
    }
}

