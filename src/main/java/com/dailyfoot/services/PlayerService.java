package com.dailyfoot.services;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.config.JwtUtil;
import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.dto.PlayerResponse;
import com.dailyfoot.entities.*;
import com.dailyfoot.exceptions.CannotDeleteStrangerPlayerException;
import com.dailyfoot.exceptions.PlayerAlreadyExistsException;
import com.dailyfoot.exceptions.PlayerNotFoundException;
import com.dailyfoot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private JwtUtil jwtUtil;
    private final PlayerRepository playerRepository;
    private final AgentRepository agentRepository;
    private final MailService mailService;
    private final StatistiqueRepository statistiqueRepository;
    private final EventRepository eventRepository;
    private final AgendaRepository agendaRepository;


    @Autowired
    public PlayerService(AgendaRepository agendaRepository, StatistiqueRepository statistiqueRepository, AgentRepository agentRepository, PlayerRepository playerRepository, MailService mailService,
                         EventRepository eventRepository) {
        this.playerRepository = playerRepository;
        this.mailService = mailService;
        this.agentRepository = agentRepository;
        this.statistiqueRepository = statistiqueRepository;
        this.eventRepository = eventRepository;
        this.agendaRepository = agendaRepository;
    }

    @Transactional
    public Player createPlayer(Integer agent, CreatePlayerRequest request) {
        Agent foundAgent = agentRepository.findByUserId(agent)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé")); // A capter dans les exceptions
        int accessCode = generateAccessCode();
        if (playerRepository.existsByEmail(request.getEmail())) {
            throw new PlayerAlreadyExistsException("Un joueur avec cet email existe déjà !");
        }
        Player player = new Player();
        player.setName(request.getName());
        player.setAge(request.getAge());
        player.setNationality(request.getNationality());
        player.setPoste(request.getPoste());
        player.setClub(request.getClub());
        player.setEmail(request.getEmail());
        player.setImage(request.getImage());
        player.setAccessCode(accessCode);
        player.setAgent(foundAgent);

        Player savedPlayer = playerRepository.save(player);
        Agenda agenda = new Agenda();
        agenda.setOwnerType(OwnerType.PLAYER);
        agenda.setColor("#FF5733");
        agenda.setOwnerId(savedPlayer.getId());
        agendaRepository.save(agenda);
        Event defaultEvent = new Event(
                "titre",
                "Description",
                "PLAYER",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                Event.OwnerType.PLAYER,
                savedPlayer.getId()
        );
        defaultEvent.setAgenda(agenda);
        eventRepository.save(defaultEvent);

        Statistique stats = new Statistique(
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
        statistiqueRepository.save(stats);

        try {
            mailService.sendAccessCodeEmail(
                    savedPlayer.getEmail(),
                    savedPlayer.getName(),
                    accessCode
            );

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage()); // a capter dans les exceptions
            e.printStackTrace();
        }
        return savedPlayer;
    }

    public List<PlayerResponse> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> new PlayerResponse(
                        player.getName(),
                        player.getPoste(),
                        player.getImage(),
                        player.getClub(),
                        player.getAge(),
                        player.getNationality()
                ))
                .toList();
    }


    public Optional<PlayerResponse> getPlayerById(Integer id) {
        return playerRepository.findById(id)
                .map(player -> new PlayerResponse(
                        player.getName(),
                        player.getPoste(),
                        player.getImage(),
                        player.getClub(),
                        player.getAge(),
                        player.getNationality()
                ));
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Integer playerId, Integer agentId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException("Joueur non trouvé"));
        if (player.getAgent().getUser().getId() != agentId) {
            throw new CannotDeleteStrangerPlayerException("Vous ne pouvez pas supprimer un joueur qui ne vous appartient pas !")
        }
        playerRepository.delete(player);
    }

    public Optional<Player> getPlayerByPlayerId(Integer playerId) {
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getId() == playerId)
                .findFirst();
    }

    public Optional<Player> getPlayerByAccessCode(int accessCode) {
        return playerRepository.findByAccessCode(accessCode);
    }

    private int generateAccessCode() {
        SecureRandom random = new SecureRandom();
        return 100000 + random.nextInt(900000);
    }

    public Agent getCurrentAgent() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer agentId = userDetails.getAgentId();
        return agentRepository.findByUserId(agentId)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé")); // A capter dans les exceptions (se répète 2x)
    }


    @Transactional
    public Player createPlayer(CreatePlayerRequest request) {
        Agent agent = getCurrentAgent();
        return createPlayer(agent.getUser().getId(), request);
    }

}
