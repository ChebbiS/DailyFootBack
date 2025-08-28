package com.dailyfoot.services;

import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.dto.PlayerResponse;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final AgentRepository agentRepository;
    private final MailService mailService;

    @Autowired
    public PlayerService(AgentRepository agentRepository, PlayerRepository playerRepository, MailService mailService) {
        this.playerRepository = playerRepository;
        this.mailService = mailService;
        this.agentRepository = agentRepository;
    }

    @Transactional
    public Player createPlayer(Integer agent, CreatePlayerRequest request) {
        Agent foundAgent = agentRepository.findByUserId(agent)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé"));
        int accessCode = generateAccessCode();

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

        try {
            String subject = "Votre code d'accès DailyFoot";
            String textPart = "Bonjour " + savedPlayer.getName() + ",\nVoici votre code d'accès : " + accessCode;
            String htmlPart = "<h3>Bonjour " + savedPlayer.getName() + "</h3><p>Voici votre code d'accès : <b>" + accessCode + "</b></p>";
            mailService.sendAccessCodeEmail(
                    savedPlayer.getEmail(),
                    savedPlayer.getName(),
                    accessCode
            );

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
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

    public void deletePlayer(Integer id) {
        playerRepository.deleteById(id);
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
}
