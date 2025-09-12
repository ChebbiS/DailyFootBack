package com.dailyfoot.services;

import com.dailyfoot.entities.Agenda;
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
import java.util.Optional;

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

    public List<Agenda> getAllAgendas() {
        return agendaRepository.findAll();
    }

    public Agenda saveAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public Optional<Agenda> getAgendaById(Integer id) {
        return agendaRepository.findById(id);
    }

    public void deleteAgenda(Integer id) {
        agendaRepository.deleteById(id);
    }

    public Optional<Agenda> getAgendaByOwnerType(OwnerType ownerType) {
        return agendaRepository.findByOwnerType(ownerType);
    }

    public List<Event> getAgentFullAgenda(int agentId) {
        List<Event> allEvents = new ArrayList<>(eventRepository.findByOwnerTypeAndOwnerId(Event.OwnerType.AGENT, agentId));
        List<Player> players = playerRepository.findByAgentId(agentId);
        for (Player player : players) {
            List<Event> playerEvents = eventRepository.findByOwnerTypeAndOwnerId(Event.OwnerType.PLAYER, player.getId());
            allEvents.addAll(playerEvents);
        }
        return allEvents;
    }

}
