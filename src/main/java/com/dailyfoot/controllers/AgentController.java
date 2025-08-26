package com.dailyfoot.controllers;

import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final PlayerService playerService;

    public AgentController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/addPlayer")
    public ResponseEntity<Player> addPlayer(@RequestBody CreatePlayerRequest request, @RequestParam int agentId) {
        // Récupérer l'agent depuis la base (ou via authentification)
        Agent agent = ; // méthode pour récupérer l’agent
        Player player = playerService.createPlayer(agent, request);
        return ResponseEntity.ok(player);
    }
}
