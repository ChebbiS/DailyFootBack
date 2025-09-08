package com.dailyfoot.dto;

public class PlayerStatisticsDTO {

    private PlayerDTO player;
    private StatistiqueDTO statistics;


    public PlayerStatisticsDTO(PlayerDTO player, StatistiqueDTO statistics) {
        this.player = player;
        this.statistics = statistics;

    }


    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public StatistiqueDTO getStatistics() {
        return statistics;
    }

    public void setStatistics(StatistiqueDTO statistics) {
        this.statistics = statistics;
    }


}
