package com.dailyfoot.services;

import com.dailyfoot.dto.LoginRequestDTO;
import com.dailyfoot.dto.RegisterDTO;
import com.dailyfoot.entities.*;
import com.dailyfoot.exceptions.AgentNotFoundException;
import com.dailyfoot.repositories.AgendaRepository;
import com.dailyfoot.repositories.EventRepository;
import com.dailyfoot.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final AgentService agentService;
    private final EventRepository eventRepository;
    private final AgendaRepository agendaRepository;

    @Autowired
    public AuthService(AgendaRepository agendaRepository, UserService userService, PlayerService playerService,
                       PlayerRepository playerRepository, AgentService agentService,
                       EventRepository eventRepository) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.agentService = agentService;
        this.eventRepository = eventRepository;
        this.agendaRepository = agendaRepository;
    }

    public User register(RegisterDTO request, User.Role role) {
        User savedUser = this.saveUser(request, role);

            Agent agent = new Agent();
            agent.setUser(savedUser);
            agentService.saveAgent(agent);
            this.setAgenda(savedUser, User.Role.AGENT);

        return savedUser;
    }

    public User register(RegisterDTO request, User.Role role, Integer agentId) {
        User savedUser = this.saveUser(request, role);

            Player player = new Player();
            player.setUser(savedUser);
            player.setEmail(request.getEmail());

            Agent agent = agentService.getAgentById(agentId)
                    .orElseThrow(() -> new AgentNotFoundException("Agent not found with id: " + agentId));
            player.setAgent(agent);

            playerService.savePlayer(player);
            this.setAgenda(savedUser, User.Role.PLAYER);

        return savedUser;
    }

    public User login(LoginRequestDTO request) {
        User user = userService.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }


    private User saveUser(RegisterDTO request, User.Role role) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        return userService.saveUser(user);
    }

    private void setAgenda(User savedUser, User.Role role) {
        String BASE_AGENDA_COLOR = "#000000";

        // Déterminer l'ID de l'entité métier propriétaire (Agent.id ou Player.id)
        int ownerEntityId;
        OwnerType ownerType;
        if (role == User.Role.AGENT) {
            Agent agent = agentService.findByUserId(savedUser.getId())
                    .orElseThrow(() -> new AgentNotFoundException("Agent not found with user id: " + savedUser.getId()));
            ownerEntityId = agent.getId();
            ownerType = OwnerType.AGENT;
        } else {
            Player player = playerRepository.findByUserId(savedUser.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Player not found for user id: " + savedUser.getId()));
            ownerEntityId = player.getId();
            ownerType = OwnerType.PLAYER;
        }

        // Créer l'agenda avec l'ownerId pointant vers l'entité (Agent/Player)
        Agenda agenda = new Agenda();
        agenda.setOwnerType(ownerType);
        agenda.setOwnerId(ownerEntityId);
        agenda.setColor(BASE_AGENDA_COLOR);

        Agenda savedAgenda = agendaRepository.save(agenda);

        // Créer un événement par défaut correctement lié
        Event defaultEvent = new Event(
                "Agenda " + role.name(),
                "Agenda",
                role.name().toUpperCase(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                role == User.Role.AGENT ? Event.OwnerType.AGENT : Event.OwnerType.PLAYER,
                ownerEntityId
        );
        defaultEvent.setAgenda(savedAgenda);

        eventRepository.save(defaultEvent);
    }
}

