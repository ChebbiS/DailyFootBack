package com.dailyfoot.controllers;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.dto.EventDTO;
import com.dailyfoot.entities.Agenda;
import com.dailyfoot.entities.Event;
import com.dailyfoot.entities.User;
import com.dailyfoot.repositories.AgendaRepository;
import com.dailyfoot.services.AgendaService;
import com.dailyfoot.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {
    @Autowired
    private final AgendaService agendaService;
    private final EventService eventService;
    private final AgendaRepository agendaRepository;
    public AgendaController(AgendaRepository agendaRepository, EventService eventService, AgendaService agendaService) {
        this.agendaRepository = agendaRepository;
        this.eventService = eventService;
        this.agendaService = agendaService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getMyAgenda(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Event> events = agendaService.getAgentFullAgenda(userDetails.getUser().getId());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/agent/{id}")
    public ResponseEntity<List<Event>> getAgentFullAgenda(@PathVariable int id) {
        List<Event> events = agendaService.getAgentFullAgenda(id);
        return ResponseEntity.ok(events);
    }
    @PostMapping("/event")
    public ResponseEntity<Event> addEvent(@RequestBody EventDTO dto,
                                          @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Agenda agenda = agendaRepository.findByOwnerId(user.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Agenda introuvable"));

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());  // match/entrainement/etc.
        event.setDateHeureDebut(dto.getDateHeureDebut());
        event.setDateHeureFin(dto.getDateHeureFin());
        event.setOwnerType(dto.getOwnerType());      // AGENT ou PLAYER
        event.setOwnerId(user.getUser().getId());
        event.setAgenda(agenda);

        Event savedEvent = eventService.saveEvent(event);
        return ResponseEntity.ok(savedEvent);
    }



}
