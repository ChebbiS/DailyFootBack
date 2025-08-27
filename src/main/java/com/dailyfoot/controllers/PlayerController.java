package com.dailyfoot.controllers;

import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.dto.PlayerResponse;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/create/{agentId}")
    public ResponseEntity<Player> createPlayer(
            @PathVariable Integer agentId,
            @RequestBody CreatePlayerRequest request
    ) {
        Agent agent = new Agent();
        agent.setId(agentId);

        try {
            Player createdPlayer = playerService.createPlayer(agent, request);
            return ResponseEntity.ok(createdPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // gerer l'exception'
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
