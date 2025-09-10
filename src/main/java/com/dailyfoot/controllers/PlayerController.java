package com.dailyfoot.controllers;


import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.dto.PlayerDTO;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.services.PlayerService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody CreatePlayerRequest request) {
        Player createdPlayer = playerService.createPlayer(request);
        return ResponseEntity.ok(new PlayerDTO(createdPlayer));
    }

    @GetMapping
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

    // TODO : restreindre ça à l'agent connecté
    @GetMapping("/my-players")
    public ResponseEntity<List<PlayerDTO>> getMyPlayers() {
        Agent currentAgent = playerService.getCurrentAgent();
        List<PlayerDTO> players = playerService.getPlayersByAgent(currentAgent);
        return ResponseEntity.ok(players);
    }

    // TODO : à revoir
    @GetMapping("/me")
    public ResponseEntity<PlayerDTO> getMyProfile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        // userDetails.getUsername() renvoie normalement l'email du joueur connecté
        Optional<PlayerDTO> player = playerService.getPlayerByEmail(userDetails.getUsername());
        return player.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
