package com.dailyfoot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "statistique")
public class Statistique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    private String saison;
    private int matchsJoues;
    private int buts;
    private int passesDecisives;
    private double taille;
    private double poids;
    private int cartonsJaunes;
    private int cartonsRouges;

    public Statistique() {
    }


    public Statistique(Player player, String saison, int matchsJoues, int buts, int passesDecisives,
                       double taille, double poids, int cartonsJaunes, int cartonsRouges) {
        this.player = player;
        this.saison = saison;
        this.matchsJoues = matchsJoues;
        this.buts = buts;
        this.passesDecisives = passesDecisives;
        this.taille = taille;
        this.poids = poids;
        this.cartonsJaunes = cartonsJaunes;
        this.cartonsRouges = cartonsRouges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
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
