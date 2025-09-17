package com.dailyfoot.controllers;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.dto.PlayerDTO;
import com.dailyfoot.dto.PlayerStatisticDTO;
import com.dailyfoot.dto.StatisticDTO;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.User;
import com.dailyfoot.services.AgentService;
import com.dailyfoot.services.PlayerService;
import com.dailyfoot.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private final StatisticService statisticService;
    private final PlayerService playerService;
    private final AgentService agentService;

    @Autowired
    public StatisticController(AgentService agentService,StatisticService statisticService, PlayerService playerService) {
        this.agentService = agentService;
        this.statisticService = statisticService;
        this.playerService = playerService;
    }

    // Récupérer toutes les statistiques
    @GetMapping
    public ResponseEntity<List<StatisticDTO>> getAllStatistics() {
        List<StatisticDTO> stats = statisticService.getAllStatistics();
        return ResponseEntity.ok(stats);
    }

    // Récupérer les statistiques d'un joueur avec ses infos
    @GetMapping("/player/{playerId}")
    public ResponseEntity<PlayerStatisticDTO> getStatByPlayer(
            @PathVariable Integer playerId,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        // Récupérer les stats du joueur
        Optional<StatisticDTO> statsOpt = statisticService.getStatsByPlayerId(playerId);
        if (statsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Récupérer le joueur
        Optional<PlayerDTO> playerOpt = playerService.getPlayerById(playerId);
        if (playerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PlayerDTO player = playerOpt.get();

        // Vérifie si le joueur consulté est lui-même
        boolean isSelf = currentUser.getUser().getId() == player.getUserId(); // si tu as ajouté userId dans PlayerDTO

        // Vérifie si le user connecté est agent et s'il est l'agent de ce joueur
        Optional<Agent> agentOpt = agentService.findByUserId(currentUser.getUser().getId());
        boolean isAgentOfPlayer = agentOpt.isPresent() && player.getAgentId() != null
                && player.getAgentId().equals(String.valueOf(agentOpt.get().getId()));

        // Si ce n’est ni le joueur lui-même, ni son agent, on bloque l’accès
        if (!isSelf && !isAgentOfPlayer) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        PlayerStatisticDTO response = new PlayerStatisticDTO(player, statsOpt.get());
        return ResponseEntity.ok(response);
    }

    // Mettre à jour les statistiques
        @PatchMapping("/update/{id}")
        public ResponseEntity<StatisticDTO> updateStatistic (
        @PathVariable int id,
        @RequestBody Map<String, Object> updates){
            StatisticDTO statisticDTO = statisticService.updateFields(id, updates);
            return ResponseEntity.ok(statisticDTO);
        }
    }

