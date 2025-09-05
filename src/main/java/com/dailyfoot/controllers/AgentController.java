package com.dailyfoot.controllers;

import com.dailyfoot.dto.CreatePlayerRequest;
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

    @PostMapping("/addPlayer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerDTO> addPlayer(@Valid @RequestBody CreatePlayerRequest request, @RequestParam int agentId) {
        Optional<Agent> optionalAgent = agentRepository.findById(agentId);
        if (optionalAgent.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Agent agent = optionalAgent.get();
        Player createdPlayer = playerService.createPlayer(agent.getUser().getId(), request);

        return ResponseEntity.ok(new PlayerDTO(createdPlayer));
    }

    @DeleteMapping("/deletePlayer")
    public ResponseEntity<Map<String, String>> deletePlayer(@RequestParam int playerId) {
        Agent currentAgent = playerService.getCurrentAgent();
        playerService.deletePlayer(playerId, currentAgent.getUser().getId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Joueur supprimé !");
        return ResponseEntity.ok(response);
    }
}