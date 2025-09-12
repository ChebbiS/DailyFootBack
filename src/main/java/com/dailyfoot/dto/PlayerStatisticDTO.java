package com.dailyfoot.dto;

public class PlayerStatisticDTO {

    private PlayerDTO player;
    private StatisticDTO statistics;


    public PlayerStatisticDTO(PlayerDTO player, StatisticDTO statistics) {
        this.player = player;
        this.statistics = statistics;

    }


    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public StatisticDTO getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticDTO statistics) {
        this.statistics = statistics;
    }


}
