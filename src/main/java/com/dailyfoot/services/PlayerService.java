package com.dailyfoot.services;

import com.dailyfoot.entities.Player;
import com.dailyfoot.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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
                .filter(player -> player.getPlayerId() == id)
                .findFirst();
    }

}
