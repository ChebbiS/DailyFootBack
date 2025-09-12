package com.dailyfoot.controllers;

import com.dailyfoot.dto.CreatePlayerDTO;
import com.dailyfoot.dto.PlayerDTO;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.services.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final PlayerService playerService;
    private final AgentRepository agentRepository;

    public AgentController(PlayerService playerService, AgentRepository agentRepository) {
        this.playerService = playerService;
        this.agentRepository = agentRepository;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<PlayerDTO> addPlayer(@Valid @RequestBody CreatePlayerDTO request) {
        // Création du joueur pour l'agent actuellement connecté
        Player createdPlayer = playerService.createPlayer(request);
        return ResponseEntity.ok(new PlayerDTO(createdPlayer));
    }


    @DeleteMapping
    public ResponseEntity<Map<String, String>> deletePlayer(@RequestParam int playerId) {
        playerService.deletePlayer(playerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Joueur supprimé !");
        return ResponseEntity.ok(response);
    }

}