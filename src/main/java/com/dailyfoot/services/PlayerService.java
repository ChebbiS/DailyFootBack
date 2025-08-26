package com.dailyfoot.services;

import com.dailyfoot.dto.CreatePlayerRequest;
import com.dailyfoot.entities.Agent;
import com.dailyfoot.entities.Player;
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
    private final MailService mailService;
    @Autowired
    public PlayerService(PlayerRepository playerRepository, MailService mailService) {
        this.playerRepository = playerRepository;
        this.mailService = mailService;
    }
@Transactional
    public Player createPlayer(Agent agent, CreatePlayerRequest request){
        int accessCode = generateAccessCode();

        Player player = new Player();
        player.setAgent(agent);
        player.setName(request.getName());
        player.setAge(request.getAge());
        player.setNationality(request.getNationality());
        player.setPoste(request.getPoste());
        player.setClub(request.getClub());
        player.setEmail(request.getEmail());
        player.setImage(request.getImage());
        player.setAccessCode(accessCode);

        Player savedPlayer = playerRepository.save(player);

        mailService.sendAccessCodeEmail(savedPlayer.getEmail(), savedPlayer.getName(), accessCode);
        return savedPlayer;
}
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    public Optional<Player> getPlayerById(Integer id) {
        return playerRepository.findById(id);
    }
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
    public void deletePlayer(Integer id) {
        playerRepository.deleteById(id);
    }
    public Optional<Player> getPlayerByPlayerId(Integer id) {
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getId() == id)
                .findFirst();
    }
    public Optional<Player> getPlayerByAccessCode(int accessCode) {
        return playerRepository.findByAccessCode(accessCode);
    }

    public int generateAccessCode() {
        SecureRandom random = new SecureRandom();
        return 100000 + random.nextInt(900000);
    }


}
