package com.dailyfoot.controllers;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.dto.EventDTO;
import com.dailyfoot.entities.Agenda;
import com.dailyfoot.entities.Event;
import com.dailyfoot.entities.OwnerType;
import com.dailyfoot.entities.User;
import com.dailyfoot.repositories.AgendaRepository;
import com.dailyfoot.services.AgendaService;
import com.dailyfoot.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        // Récupérer un agenda correspondant au user
        // Ici on prend juste le premier trouvé (simple et rapide)
        Agenda agenda = agendaRepository.findFirstByOwnerId(user.getUser().getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Agenda introuvable"));

        // 🔹 Créer l'événement
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDateHeureDebut(dto.getDateHeureDebut());
        event.setDateHeureFin(dto.getDateHeureFin());
        event.setOwnerType(dto.getOwnerType());  // utilise directement le DTO
        event.setOwnerId(user.getUser().getId());
        event.setAgenda(agenda);

        // 🔹 Sauvegarder et retourner
        Event savedEvent = eventService.saveEvent(event);
        return ResponseEntity.ok(savedEvent);
    }
    @PostMapping("/event/player/{playerId}")
    public ResponseEntity<Event> addEventForPlayer(@PathVariable int playerId,
                                                   @RequestBody EventDTO dto,
                                                   @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Récupérer l'agenda du joueur
        Agenda agenda = agendaRepository.findFirstByOwnerId(playerId)
                .orElseThrow(() -> new RuntimeException("Agenda du joueur introuvable"));

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDateHeureDebut(dto.getDateHeureDebut());
        event.setDateHeureFin(dto.getDateHeureFin());
        event.setOwnerType(dto.getOwnerType()); // AGENT qui crée l'événement
        event.setOwnerId(playerId);             // ⚠️ important : ownerId = playerId
        event.setAgenda(agenda);

        Event savedEvent = eventService.saveEvent(event);
        return ResponseEntity.ok(savedEvent);
    }
    @DeleteMapping("/event/player/{eventId}")
    public ResponseEntity<Void> deletePlayerEvent(@PathVariable int eventId,
                                                  @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Vérifie que l'utilisateur est agent
        if (user.getUser().getRole() != User.Role.AGENT) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));

        // Supprimer l’événement même si l’ownerId n’est pas celui de l’agent
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }



    @DeleteMapping("/event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Event event = eventService.getEventById(id)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));

        if (event.getOwnerId() != user.getUser().getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Supprimer l'événement
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public List<Event> getMyEvents(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Utilisateur non connecté");
        }

        int userId = userDetails.getUser().getId();

        // Récupère tous les agendas de l'utilisateur
        List<Agenda> agendas = agendaRepository.findByOwnerId(userId);

        if (agendas.isEmpty()) {
            throw new RuntimeException("Aucun agenda trouvé pour cet utilisateur");
        }

        // Récupère tous les événements de tous les agendas
        List<Event> allEvents = new ArrayList<>();
        for (Agenda agenda : agendas) {
            List<Event> events = eventService.getEventsByAgendaId(agenda.getId());
            allEvents.addAll(events);
        }

        return allEvents;
    }
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Event>> getPlayerAgenda(@PathVariable int playerId) {
        List<Event> events = agendaService.getPlayerFullAgenda(playerId);
        return ResponseEntity.ok(events);
    }


}
