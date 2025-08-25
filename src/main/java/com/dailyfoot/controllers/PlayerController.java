package com.dailyfoot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @GetMapping("/search")
    public Map<String, List<String>> searchPlayers(@RequestParam String name){
        return Map.of("joueurs", List.of("joueur1", "joueur2"));
    }
}
