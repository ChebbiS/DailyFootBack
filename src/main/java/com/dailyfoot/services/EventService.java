package com.dailyfoot.services;

import com.dailyfoot.dto.EventDisplayDTO;
import com.dailyfoot.entities.Event;
import com.dailyfoot.entities.User;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.repositories.EventRepository;
import com.dailyfoot.repositories.PlayerRepository;
import com.dailyfoot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final AgentRepository agentRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public EventService(PlayerRepository playerRepository,AgentRepository agentRepository ,EventRepository eventRepository) {
        this.playerRepository = playerRepository;
        this.agentRepository = agentRepository;
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }
    public List<Event> getEventsByAgendaId(int agendaId) {
        return eventRepository.findByAgendaId(agendaId);
    }
    public EventDisplayDTO mapToDTO(Event event) {
        String userName = "Inconnu";

        if (event.getOwnerType() == Event.OwnerType.AGENT) {
            userName = agentRepository.findById(event.getOwnerId())
                    .map(agent -> agent.getUser().getName())
                    .orElse("Inconnu");
        } else if (event.getOwnerType() == Event.OwnerType.PLAYER) {
            userName = playerRepository.findById(event.getOwnerId())
                    .map(player -> player.getUser().getName())
                    .orElse("Inconnu");
        }

        return new EventDisplayDTO(event, userName);
    }

    public List<EventDisplayDTO> mapListToDTO(List<Event> events) {
        List<EventDisplayDTO> dtos = new ArrayList<>();
        for (Event event : events) {
            dtos.add(mapToDTO(event));
        }
        return dtos;
    }

}
