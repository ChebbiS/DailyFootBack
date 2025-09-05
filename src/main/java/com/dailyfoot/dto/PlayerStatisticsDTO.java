package com.dailyfoot.dto;

import com.dailyfoot.entities.Player;

import java.util.List;

public class PlayerStatisticsDTO {

    private PlayerDTO player; // Infos du joueur
    private StatistiqueDTO statistics; // Stats
     // Optionnel, si tu veux les matchs

    public PlayerStatisticsDTO(PlayerDTO player, StatistiqueDTO statistics) {
        this.player = player;
        this.statistics = statistics;

    }

    // Getters et setters
    public PlayerDTO getPlayer() { return player; }
    public void setPlayer(PlayerDTO player) { this.player = player; }

    public StatistiqueDTO getStatistics() { return statistics; }
    public void setStatistics(StatistiqueDTO statistics) { this.statistics = statistics; }


}
