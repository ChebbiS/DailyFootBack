package com.dailyfoot.dto;

import com.dailyfoot.entities.Statistic;

public class StatisticDTO {
    private int id;
    private int matchesPlayed;
    private int goals;
    private int assists;
    private double height;
    private double weight;
    private int yellowCards;
    private int redCards;

    public StatisticDTO(Statistic stat) {
        this.id = stat.getId();
        this.matchesPlayed = stat.getMatchesPlayed();
        this.goals = stat.getGoals();
        this.assists = stat.getAssists();
        this.height = stat.getHeight();
        this.weight = stat.getWeight();
        this.yellowCards = stat.getYellowCards();
        this.redCards = stat.getRedCards();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }
}
