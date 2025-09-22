package com.dailyfoot.services;

import com.dailyfoot.dto.EventDisplayDTO;
import com.dailyfoot.entities.Event;
import com.dailyfoot.entities.OwnerType;
import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.AgendaRepository;
import com.dailyfoot.repositories.EventRepository;
import com.dailyfoot.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public AgendaService(EventService eventService, AgendaRepository agendaRepository,
                         EventRepository eventRepository,
                         PlayerRepository playerRepository) {
        this.agendaRepository = agendaRepository;
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.playerRepository = playerRepository;
    }

    // Méthodes existantes pour récupérer Event

    public List<Event> getAgentFullAgenda(int agentId) {
        List<Event> allEvents = new ArrayList<>(eventRepository.findByOwnerTypeAndOwnerId(Event.OwnerType.AGENT, agentId));
        List<Player> players = playerRepository.findByAgentId(agentId);
        for (Player player : players) {
            List<Event> playerEvents = eventRepository.findByOwnerTypeAndOwnerId(Event.OwnerType.PLAYER, player.getId());
            allEvents.addAll(playerEvents);
        }
        return allEvents;
    }

    public List<Event> getPlayerFullAgenda(int playerId) {
        return agendaRepository.findByOwnerTypeAndOwnerId(OwnerType.PLAYER, playerId)
                .map(agenda -> eventRepository.findByAgendaId(agenda.getId()))
                .orElse(List.of());
    }

    // Nouvelles méthodes pour retourner DTO
    public List<EventDisplayDTO> getAgentFullAgendaDTO(int agentId) {
        return eventService.mapListToDTO(getAgentFullAgenda(agentId));
    }

    public List<EventDisplayDTO> getPlayerFullAgendaDTO(int playerId) {
        return eventService.mapListToDTO(getPlayerFullAgenda(playerId));
    }
}
