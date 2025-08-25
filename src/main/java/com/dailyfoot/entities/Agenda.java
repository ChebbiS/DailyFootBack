package com.dailyfoot.entities;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Agenda {
    
    @Id
    @GeneratedValue
    private Long Id;
    
    private String title;
    private String description;
    private ZonedDateTime date_heure_debut;
    private ZonedDateTime date_heure_fin;
    private String type;

    public Agenda() {

    }

    public Agenda (Long Id, String title, String description, ZonedDateTime date_heure_debut, ZonedDateTime date_heure_fin, String type) {
        this.Id = Id;
        this.title = title;
        this.description = description;
        this.date_heure_debut = date_heure_debut;
        this.date_heure_fin = date_heure_fin;
        this.type = type;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDate_heure_debut() {
        return date_heure_debut;
    }

    public void setPassword(ZonedDateTime date_heure_debut) {
        this.date_heure_debut = date_heure_debut;
    }

    public ZonedDateTime getDate_heure_fin() {
        return date_heure_fin;
    }

    public void setRole(ZonedDateTime date_heure_fin) {
        this.date_heure_fin = date_heure_fin;

    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
