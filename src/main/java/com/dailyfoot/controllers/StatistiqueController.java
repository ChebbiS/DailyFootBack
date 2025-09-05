package com.dailyfoot.controllers;

import com.dailyfoot.dto.PlayerDTO;
import com.dailyfoot.dto.PlayerStatisticsDTO;
import com.dailyfoot.dto.StatistiqueDTO;
import com.dailyfoot.services.PlayerService;
import com.dailyfoot.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/statistique")
public class StatistiqueController {

    private final StatistiqueService statistiqueService;
    private final PlayerService playerService;

    @Autowired
    public StatistiqueController(StatistiqueService statistiqueService, PlayerService playerService) {
        this.statistiqueService = statistiqueService;
        this.playerService = playerService;
    }

    // Récupérer toutes les statistiques
    @GetMapping
    public ResponseEntity<List<StatistiqueDTO>> getAllStatistiques() {
        List<StatistiqueDTO> stats = statistiqueService.getAllStatistiques();
        return ResponseEntity.ok(stats);
    }

    // Récupérer les statistiques d'un joueur avec ses infos
    @GetMapping("/player/{playerId}")
    public ResponseEntity<PlayerStatisticsDTO> getStatByPlayer(@PathVariable Integer playerId) {
        Optional<StatistiqueDTO> statsOpt = statistiqueService.getStatsByPlayerId(playerId);
        if (statsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<PlayerDTO> playerOpt = playerService.getPlayerById(playerId);
        if (playerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PlayerStatisticsDTO response = new PlayerStatisticsDTO(
                playerOpt.get(),
                statsOpt.get()
        );

        return ResponseEntity.ok(response);
    }

    // Mettre à jour les statistiques
    @PatchMapping("/update/{id}")
    public ResponseEntity<StatistiqueDTO> updateStatistiques(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {
        StatistiqueDTO statistiqueDTO = statistiqueService.updateFields(id, updates);
        return ResponseEntity.ok(statistiqueDTO);
    }
}
