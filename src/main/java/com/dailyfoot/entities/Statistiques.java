package com.dailyfoot.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public class Statistiques {
    @Id
    @GeneratedValue
    private Long Id;

    private Long joueur_id;
    private String saison;
    private int matchs_joues;
    private int buts;
    private int passes_decisives;
    private BigDecimal taille;
    private BigDecimal poids;
    private int cartons_jaunes;
    private int cartons_rouges;

    public Statistiques() {

    }

    public Statistiques(Long Id, Long joueur_id, String saison, int matchs_joues, int buts, int passes_decisives, BigDecimal taille, BigDecimal poids, int cartons_jaunes, int cartons_rouges) {
        this.Id = Id;
        this.joueur_id = joueur_id;
        this.saison = saison;
        this.matchs_joues = matchs_joues;
        this.buts = buts;
        this.passes_decisives = passes_decisives;
        this.taille = taille;
        this.poids = poids;
        this.cartons_jaunes = cartons_jaunes;
        this.cartons_rouges = cartons_rouges;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getJoueur_id() {
        return joueur_id;
    }

    public void setJoueur_id(Long joueur_id) {
        this.joueur_id = joueur_id;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public int getMatchs_joues() {
        return matchs_joues;
    }

    public void setMatchs_joues(int matchs_joues) {
        this.matchs_joues = matchs_joues;
    }

    public int getButs() {
        return buts;
    }

    public void setButs(int buts) {
        this.buts = buts;

    }

    public int getPasses_decisives() {
        return passes_decisives;
    }

    public void setPasses_decisives(int passes_decisives) {
        this.passes_decisives = passes_decisives;
    }

    public BigDecimal getTaille() {
        return taille;
    }

    public void setTaille(BigDecimal taille) {
        this.taille = taille;
    }

    public BigDecimal getPoids() {
        return poids;
    }

    public void setPoids(BigDecimal poids) {
        this.poids = poids;
    }

    public int getCartons_jaunes() {
        return cartons_jaunes;
    }

    public void setCartons_jaunes(int cartons_jaunes) {
        this.cartons_jaunes = cartons_jaunes;
    }

    public int getCartons_rouges() {
        return cartons_rouges;
    }

    public void setCartons_rouges(int cartons_rouges) {
        this.cartons_rouges = cartons_rouges;
    }
}
