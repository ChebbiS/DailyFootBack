package com.dailyfoot.services;

import com.dailyfoot.dto.LoginRequest;
import com.dailyfoot.dto.RegisterRequest;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.entities.User;
import com.dailyfoot.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final AgentService agentService;

    @Autowired
    public AuthService(UserService userService , PlayerService playerService,
                       PlayerRepository playerRepository, AgentService agentService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.agentService = agentService;
    }

    public User register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        User savedUser = userService.saveUser(user);
        if (savedUser.getRole() == User.Role.AGENT) {
            Agent agent = new Agent();
            agent.setUser(savedUser);
            agentService.saveAgent(agent);
        }
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
    }

