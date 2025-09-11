package com.dailyfoot.controllers;

import com.dailyfoot.dto.PlayerDTO;
import com.dailyfoot.dto.PlayerStatisticDTO;
import com.dailyfoot.dto.StatisticDTO;
import com.dailyfoot.services.PlayerService;
import com.dailyfoot.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private final StatisticService statisticService;
    private final PlayerService playerService;

    @Autowired
    public StatisticController(StatisticService statisticService, PlayerService playerService) {
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
    public ResponseEntity<PlayerStatisticDTO> getStatByPlayer(@PathVariable Integer playerId) {
        Optional<StatisticDTO> statsOpt = statisticService.getStatsByPlayerId(playerId);
        if (statsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<PlayerDTO> playerOpt = playerService.getPlayerById(playerId);
        if (playerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PlayerStatisticDTO response = new PlayerStatisticDTO(
                playerOpt.get(),
                statsOpt.get()
        );

        return ResponseEntity.ok(response);
    }

    // Mettre à jour les statistiques
    @PatchMapping("/update/{id}")
    public ResponseEntity<StatisticDTO> updateStatistic(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {
        StatisticDTO statisticDTO = statisticService.updateFields(id, updates);
        return ResponseEntity.ok(statisticDTO);
    }
}
