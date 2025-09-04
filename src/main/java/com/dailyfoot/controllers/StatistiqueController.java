package com.dailyfoot.controllers;

import com.dailyfoot.dto.StatistiqueDTO;
import com.dailyfoot.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/statistique")
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    @Autowired
    public StatistiqueController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    @GetMapping //http://localhost:8080/statistique
    public ResponseEntity<List<StatistiqueDTO>>getAllStatistiques() {
        List<StatistiqueDTO> stats = statistiqueService.getAllStatistiques();
        return ResponseEntity.ok(stats);
    }


    @PatchMapping("/update/{id}") //https://localhost:8080/statistique/update/1
    public ResponseEntity<StatistiqueDTO> updateStatistiques(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {
        StatistiqueDTO statistiqueDTO = statistiqueService.updateFields(id, updates);
        return ResponseEntity.ok(statistiqueDTO);
    }

    }
