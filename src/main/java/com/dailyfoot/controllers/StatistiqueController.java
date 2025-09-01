package com.dailyfoot.controllers;

import com.dailyfoot.dto.StatistiqueDTO;
import com.dailyfoot.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/statistique")
public class StatistiqueController {
    private final StatistiqueService statistiqueService;
    @Autowired
    public StatistiqueController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<StatistiqueDTO> updateStatistiques(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {
        StatistiqueDTO statistiqueDTO = statistiqueService.updateFields(id, updates);
        return ResponseEntity.ok(statistiqueDTO);
    }

    }
