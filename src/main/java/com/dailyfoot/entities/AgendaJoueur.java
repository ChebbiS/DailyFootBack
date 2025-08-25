package com.dailyfoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AgendaJoueur {

    @Id
    @GeneratedValue
    private Long agenda_id;
    private Long joueur_id;

    public AgendaJoueur() {

    }

    public AgendaJoueur (Long agenda_id, Long joueur_id) {
        this.agenda_id = agenda_id;
        this.joueur_id = joueur_id;
    }

    public Long getAgenda_id() {
        return agenda_id;
    }

    public void setAgenda_id(Long agenda_id) {
        this.agenda_id = agenda_id;
    }

    public Long getJoueur_id() {
        return joueur_id;
    }

    public void setJoueur_id(Long joueur_id) {
        this.joueur_id = joueur_id;
    }
}
