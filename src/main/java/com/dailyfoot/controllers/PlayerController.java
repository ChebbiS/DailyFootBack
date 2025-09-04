package com.dailyfoot.controllers;

import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.dto.PlayerDTO;
import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('AGENT', 'ADMIN')")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody CreatePlayerRequest request) {
        Player createdPlayer = playerService.createPlayer(request);
        return ResponseEntity.ok(new PlayerDTO(createdPlayer));
    }

    @GetMapping("/ListOfPlayers")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Integer id) {
        Optional<PlayerDTO> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Capter l'exception si le player n'existe pas
    }
}
