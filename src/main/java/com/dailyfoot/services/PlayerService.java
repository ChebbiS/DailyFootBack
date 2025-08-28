package com.dailyfoot.services;

import com.dailyfoot.config.CustomUserDetails;
import com.dailyfoot.config.JwtUtil;
import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.dto.PlayerResponse;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
import com.dailyfoot.exceptions.PlayerAlreadyExistsException;
import com.dailyfoot.repositories.AgentRepository;
import com.dailyfoot.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private JwtUtil jwtUtil;
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

        try {
            String subject = "Bienvenue sur DailyFoot - Votre code d'accès";

            // Version texte simple (fallback)
            String textPart = "Bonjour " + savedPlayer.getName() + ",\n\n"
                    + "Bienvenue sur DailyFoot !\n"
                    + "Voici votre code d'accès pour vous connecter : " + accessCode + "\n\n"
                    + "Conservez-le précieusement et ne le partagez avec personne.\n\n"
                    + "L'équipe DailyFoot";

            // Version HTML simple et lisible
            String htmlPart = "<!DOCTYPE html>"
                    + "<html lang='fr'>"
                    + "<head><meta charset='UTF-8'><title>DailyFoot</title></head>"
                    + "<body style='font-family:Arial,sans-serif; line-height:1.6;'>"
                    + "<h2 style='color:#2E86C1;'>Bonjour " + savedPlayer.getName() + ",</h2>"
                    + "<p>Bienvenue sur <strong>DailyFoot</strong> !</p>"
                    + "<p>Voici votre code d'accès pour vous connecter :</p>"
                    + "<p style='font-size:18px; font-weight:bold; color:#C0392B;'>" + accessCode + "</p>"
                    + "<p>Conservez-le précieusement et ne le partagez avec personne.</p>"
                    + "<hr style='border:none; border-top:1px solid #ccc;'/>"
                    + "<p style='font-size:12px; color:#888;'>"
                    + "Si vous n'êtes pas à l'origine de cette demande, veuillez ignorer cet email."
                    + "</p>"
                    + "<p style='font-size:12px; color:#888;'>L'équipe DailyFoot</p>"
                    + "</body></html>";

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

    public Agent getCurrentAgent() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer agentId = userDetails.getAgentId();
        return agentRepository.findByUserId(agentId)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé"));
    }


    @Transactional
    public Player createPlayer(CreatePlayerRequest request) {
        Agent agent = getCurrentAgent();
        return createPlayer(agent.getUser().getId(), request);
    }

}
