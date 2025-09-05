package com.dailyfoot.controllers;

import com.dailyfoot.entities.Event;
import com.dailyfoot.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {
    @Autowired
    private final AgendaService agendaService;
    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping("/agent/{id}")
    public ResponseEntity<List<Event>> getAgentFullAgenda(@PathVariable int id) {
        List<Event> events = agendaService.getAgentFullAgenda(id);
        return ResponseEntity.ok(events);
    }
}
