package com.dailyfoot.controllers;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.dto.EventDTO;
import com.dailyfoot.dto.EventDisplayDTO;
import com.dailyfoot.entities.Agenda;
import com.dailyfoot.entities.Event;
import com.dailyfoot.entities.OwnerType;
import com.dailyfoot.entities.User;
import com.dailyfoot.repositories.AgendaRepository;
import com.dailyfoot.repositories.PlayerRepository;
import com.dailyfoot.services.AgendaService;
import com.dailyfoot.services.EventService;
import com.dailyfoot.services.AgentService;
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
    private final AgentService agentService;
    private final PlayerRepository playerRepository;
    public AgendaController(AgendaRepository agendaRepository, EventService eventService, AgendaService agendaService,
                            AgentService agentService, PlayerRepository playerRepository) {
        this.agendaRepository = agendaRepository;
        this.eventService = eventService;
        this.agendaService = agendaService;
        this.agentService = agentService;
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getMyAgenda(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userDetails.getUser();
        if (user.getRole() == User.Role.AGENT) {
            int agentId = agentService.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Agent introuvable pour l'utilisateur"))
                    .getId();
            List<Event> events = agendaService.getAgentFullAgenda(agentId);
            return ResponseEntity.ok(events);
        } else if (user.getRole() == User.Role.PLAYER) {
            int playerId = playerRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Joueur introuvable pour l'utilisateur"))
                    .getId();
            List<Event> events = agendaService.getPlayerFullAgenda(playerId);
            return ResponseEntity.ok(events);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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

        User u = user.getUser();

        // Déterminer l'entité (Agent/Player) et récupérer le bon agenda
        Agenda agenda;
        Event.OwnerType ownerType;
        int ownerId;
        if (u.getRole() == User.Role.AGENT) {
            int agentId = agentService.findByUserId(u.getId())
                    .orElseThrow(() -> new RuntimeException("Agent introuvable pour l'utilisateur"))
                    .getId();
            agenda = agendaRepository.findByOwnerIdAndOwnerType(agentId, OwnerType.AGENT)
                    .orElseThrow(() -> new RuntimeException("Agenda agent introuvable"));
            ownerType = Event.OwnerType.AGENT;
            ownerId = agentId;
        } else if (u.getRole() == User.Role.PLAYER) {
            int playerId = playerRepository.findByUserId(u.getId())
                    .orElseThrow(() -> new RuntimeException("Joueur introuvable pour l'utilisateur"))
                    .getId();
            agenda = agendaRepository.findByOwnerIdAndOwnerType(playerId, OwnerType.PLAYER)
                    .orElseThrow(() -> new RuntimeException("Agenda joueur introuvable"));
            ownerType = Event.OwnerType.PLAYER;
            ownerId = playerId;
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Créer l'événement
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDateHeureDebut(dto.getDateHeureDebut());
        event.setDateHeureFin(dto.getDateHeureFin());
        event.setOwnerType(ownerType);
        event.setOwnerId(ownerId);
        event.setAgenda(agenda);

        Event savedEvent = eventService.saveEvent(event);
        return ResponseEntity.ok(savedEvent);
    }
    @PostMapping("/event/player/{playerId}")
    public ResponseEntity<Event> addEventForPlayer(@PathVariable int playerId,
                                                   @RequestBody EventDTO dto,
                                                   @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Récupérer l'agenda du joueur
        Agenda agenda = agendaRepository.findByOwnerIdAndOwnerType(playerId, OwnerType.PLAYER)
                .orElseThrow(() -> new RuntimeException("Agenda du joueur introuvable"));

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDateHeureDebut(dto.getDateHeureDebut());
        event.setDateHeureFin(dto.getDateHeureFin());
        event.setOwnerType(Event.OwnerType.PLAYER);
        event.setOwnerId(playerId);
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

        User u = user.getUser();
        boolean allowed = false;
        if (u.getRole() == User.Role.AGENT && event.getOwnerType() == Event.OwnerType.AGENT) {
            int agentId = agentService.findByUserId(u.getId())
                    .orElseThrow(() -> new RuntimeException("Agent introuvable pour l'utilisateur"))
                    .getId();
            allowed = (event.getOwnerId() == agentId);
        } else if (u.getRole() == User.Role.PLAYER && event.getOwnerType() == Event.OwnerType.PLAYER) {
            int playerId = playerRepository.findByUserId(u.getId())
                    .orElseThrow(() -> new RuntimeException("Joueur introuvable pour l'utilisateur"))
                    .getId();
            allowed = (event.getOwnerId() == playerId);
        }

        if (!allowed) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<EventDisplayDTO>> getMyEvents(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userDetails.getUser();
        List<EventDisplayDTO> events;

        if (user.getRole() == User.Role.AGENT) {
            int agentId = agentService.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Agent introuvable"))
                    .getId();
            events = agendaService.getAgentFullAgendaDTO(agentId);
        } else if (user.getRole() == User.Role.PLAYER) {
            int playerId = playerRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Joueur introuvable"))
                    .getId();
            events = agendaService.getPlayerFullAgendaDTO(playerId);
        } else {
            events = List.of();
        }

        return ResponseEntity.ok(events);
    }



    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Event>> getPlayerAgenda(@PathVariable int playerId) {
        List<Event> events = agendaService.getPlayerFullAgenda(playerId);
        return ResponseEntity.ok(events);
    }


}
