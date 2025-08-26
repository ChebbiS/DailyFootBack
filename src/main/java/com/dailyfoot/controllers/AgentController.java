package com.dailyfoot.controllers;

import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Player> addPlayer(@RequestBody CreatePlayerRequest request, @RequestParam int agentId) {
        Optional<Agent> optionalAgent = agentRepository.findById(agentId);
        if (optionalAgent.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Agent agent = optionalAgent.get();
        Player player = playerService.createPlayer(agent, request);
        return ResponseEntity.ok(player);
    }
}
