package com.dailyfoot.dto;

import com.dailyfoot.entities.Statistique;

public class StatistiqueDTO {
    private int id;
    private int matchsJoues;
    private int buts;
    private int passesDecisives;
    private double taille;
    private double poids;
    private int cartonsJaunes;
    private int cartonsRouges;

    public StatistiqueDTO(Statistique stat) {
        this.id = stat.getId();
        this.matchsJoues = stat.getMatchsJoues();
        this.buts = stat.getButs();
        this.passesDecisives = stat.getPassesDecisives();
        this.taille = stat.getTaille();
        this.poids = stat.getPoids();
        this.cartonsJaunes = stat.getCartonsJaunes();
        this.cartonsRouges = stat.getCartonsRouges();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchsJoues() {
        return matchsJoues;
    }

    public void setMatchsJoues(int matchsJoues) {
        this.matchsJoues = matchsJoues;
    }

    public int getButs() {
        return buts;
    }

    public void setButs(int buts) {
        this.buts = buts;
    }

    public int getPassesDecisives() {
        return passesDecisives;
    }

    public void setPassesDecisives(int passesDecisives) {
        this.passesDecisives = passesDecisives;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public int getCartonsJaunes() {
        return cartonsJaunes;
    }

    public void setCartonsJaunes(int cartonsJaunes) {
        this.cartonsJaunes = cartonsJaunes;
    }

    public int getCartonsRouges() {
        return cartonsRouges;
    }

    public void setCartonsRouges(int cartonsRouges) {
        this.cartonsRouges = cartonsRouges;
    }
}
