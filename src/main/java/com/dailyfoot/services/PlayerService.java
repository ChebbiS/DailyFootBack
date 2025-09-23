package com.dailyfoot.services;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.dto.CreatePlayerDTO;
import com.dailyfoot.dto.PlayerDTO;
import com.dailyfoot.entities.*;
import com.dailyfoot.exceptions.CannotDeleteStrangerPlayerException;
import com.dailyfoot.exceptions.CannotUpdatePlayerException;
import com.dailyfoot.exceptions.PlayerAlreadyExistsException;
import com.dailyfoot.exceptions.PlayerNotFoundException;
import com.dailyfoot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final AgentRepository agentRepository;
    private final MailService mailService;
    private final StatisticRepository statisticRepository;
    private final EventRepository eventRepository;
    private final AgendaRepository agendaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerService(
            AgendaRepository agendaRepository,
            StatisticRepository statisticRepository,
            AgentRepository agentRepository,
            PlayerRepository playerRepository,
            MailService mailService,
            EventRepository eventRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.playerRepository = playerRepository;
        this.mailService = mailService;
        this.agentRepository = agentRepository;
        this.statisticRepository = statisticRepository;
        this.eventRepository = eventRepository;
        this.agendaRepository = agendaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------------------------- CREATION DU JOUEUR ----------------------------
    @Transactional
    public Player createPlayer(CreatePlayerDTO request) {
        Agent agent = getCurrentAgent();

        if (playerRepository.existsByEmail(request.getEmail())) {
            throw new PlayerAlreadyExistsException("Un joueur avec cet email existe déjà !");
        }

        // Création du joueur
        Player player = new Player();
        player.setName(request.getName());
        player.setAge(request.getAge());
        player.setNationality(request.getNationality());
        player.setPoste(request.getPoste());
        player.setClub(request.getClub());
        player.setEmail(request.getEmail());
        player.setImage(request.getImage());
        player.setHeight(request.getHeight());
        player.setWeight(request.getWeight());
        player.setAgent(agent);

        // Génération et encodage du mot de passe
        String rawPassword = generateRandomPassword(10);

        // Création du user associé
        User user = new User();
        user.setName(player.getName());
        user.setEmail(player.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(User.Role.PLAYER);

        // Associer le user au player
        player.setUser(user);

        // Sauvegarde du player (avec user)
        Player savedPlayer = playerRepository.save(player);

        // Création agenda
        Agenda agenda = new Agenda();
        agenda.setOwnerType(OwnerType.PLAYER);
        agenda.setColor("#FF5733");
        agenda.setOwnerId(savedPlayer.getId());
        agendaRepository.save(agenda);

        // Statistiques initiales
        Statistic stats = new Statistic(
                savedPlayer,
                "2025/2026",
                0,
                0,
                0,
                0.0,
                0,
                0,
                0
        );
        statisticRepository.save(stats);

        // Envoi du mot de passe par email
        mailService.sendAccessCodeEmail(savedPlayer.getEmail(), savedPlayer.getName(), rawPassword);

        return savedPlayer;
    }

    // ---------------------------- UTILS ----------------------------
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Agent getCurrentAgent() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer agentId = userDetails.getAgentId();
        return agentRepository.findByUserId(agentId)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé"));
    }

    // ---------------------------- AUTRES MÉTHODES ----------------------------
    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(PlayerDTO::new)
                .toList();
    }

    public Optional<PlayerDTO> getPlayerById(Integer id) {
        return playerRepository.findById(id).map(PlayerDTO::new);
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Integer playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Joueur non trouvé"));

        Agent currentAgent = getCurrentAgent();

        if (player.getAgent().getId() != currentAgent.getId()) {
            throw new CannotDeleteStrangerPlayerException("Vous ne pouvez pas supprimer un joueur qui ne vous appartient pas !");
        }

        playerRepository.delete(player);
    }

    public List<PlayerDTO> getPlayersByAgent(Agent agent) {
        return playerRepository.findByAgentId(agent.getId())
                .stream()
                .map(PlayerDTO::new)
                .toList();
    }

    public Optional<PlayerDTO> getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email)
                .map(PlayerDTO::new);
    }

    public Player updatePlayer(Integer playerId, Player updatedPlayer) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Joueur non trouvé"));

        Agent currentAgent = getCurrentAgent();

        if (player.getAgent().getId() != currentAgent.getId()) {
            throw new CannotUpdatePlayerException("Vous ne pouvez pas modifier un joueur qui ne vous appartient pas !");
        }

        if (updatedPlayer.getName() != null) player.setName(updatedPlayer.getName());
        if (updatedPlayer.getAge() != 0) player.setAge(updatedPlayer.getAge());
        if (updatedPlayer.getNationality() != null) player.setNationality(updatedPlayer.getNationality());
        if (updatedPlayer.getPoste() != null) player.setPoste(updatedPlayer.getPoste());
        if (updatedPlayer.getClub() != null) player.setClub(updatedPlayer.getClub());
        if (updatedPlayer.getEmail() != null) player.setEmail(updatedPlayer.getEmail());
        if (updatedPlayer.getImage() != null) player.setImage(updatedPlayer.getImage());
        if (updatedPlayer.getHeight() != 0) player.setHeight(updatedPlayer.getHeight());
        if (updatedPlayer.getWeight() != 0) player.setWeight(updatedPlayer.getWeight());

        return playerRepository.save(player);
    }

}
