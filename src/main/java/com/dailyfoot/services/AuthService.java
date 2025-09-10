package com.dailyfoot.services;

import com.dailyfoot.dto.LoginRequest;
import com.dailyfoot.dto.RegisterRequest;
import com.dailyfoot.entities.*;
import com.dailyfoot.repositories.AgendaRepository;
import com.dailyfoot.repositories.EventRepository;
import com.dailyfoot.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public User register(RegisterRequest request, User.Role role) {
        User savedUser = this.saveUser(request, role);

            Agent agent = new Agent();
            agent.setUser(savedUser);
            agentService.saveAgent(agent);
            this.setAgenda(savedUser, User.Role.AGENT);

        return savedUser;
    }

    public User register(RegisterRequest request, User.Role role, Integer agentId) {
        User savedUser = this.saveUser(request, role);

            Player player = new Player();
            player.setUser(savedUser);
            player.setEmail(request.getEmail());

            Agent agent = agentService.getAgentById(agentId)
                    .orElseThrow(() -> new IllegalArgumentException("Agent not found with id: " + agentId));
            player.setAgent(agent);

            playerService.savePlayer(player);
            this.setAgenda(savedUser, User.Role.PLAYER);

        return savedUser;
    }

    public User login(LoginRequest request) {
        Optional<User> userOpt = userService.getUserByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public Player loginPlayer(int accessCode) {
        Optional<Player> playerOpt = playerService.getPlayerByAccessCode(accessCode);
        return playerOpt.orElse(null);
    }

    private User saveUser(RegisterRequest request, User.Role role) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        return userService.saveUser(user);
    }

    private void setAgenda(User savedUser, User.Role role) {
        String BASE_AGENDA_COLOR = "#000000";

        Agenda agenda = new Agenda();
        agenda.setOwnerType(role == User.Role.AGENT ? OwnerType.AGENT : OwnerType.PLAYER);
        agenda.setOwnerId(savedUser.getId());
        agenda.setColor(BASE_AGENDA_COLOR);

        Agenda savedAgenda = agendaRepository.save(agenda);

        Event defaultEvent = new Event(
                "Agenda " + role.name(),
                "Agenda",
                role.name().toUpperCase(), // EN MAJ BB
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                role == User.Role.AGENT ? Event.OwnerType.AGENT : Event.OwnerType.PLAYER,
                savedAgenda.getId()
        );

        eventRepository.save(defaultEvent);
    }
}

