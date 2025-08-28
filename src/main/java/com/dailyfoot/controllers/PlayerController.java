package com.dailyfoot.controllers;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.dto.PlayerResponse;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final AgentRepository agentRepository;

    @Autowired
    public PlayerController(PlayerService playerService,
                            AgentRepository agentRepository) {
        this.playerService = playerService;
        this.agentRepository = agentRepository;
    }

    @PostMapping("/addPlayer")
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerRequest request) {
        try {
            // Récupérer l'agent connecté via JWT
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            Integer agentId = userDetails.getAgentId(); // doit exister dans CustomUserDetails
            Agent agent = agentRepository.findByUserId(agentId)
                    .orElseThrow(() -> new RuntimeException("Agent non trouvé"));

            Player createdPlayer = playerService.createPlayer(agentId, request);

            return ResponseEntity.ok(createdPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/ListOfPlayers")
    public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
        List<PlayerResponse> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable Integer id) {
        Optional<PlayerResponse> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
