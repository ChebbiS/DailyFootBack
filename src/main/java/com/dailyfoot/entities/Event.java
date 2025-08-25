package com.dailyfoot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String type;

    private java.time.LocalDateTime dateHeureDebut;
    private java.time.LocalDateTime dateHeureFin;

    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;
    private int ownerId;

    public enum OwnerType {
        AGENT, PLAYER
    }

    public Event() {}

    public Event(String title, String description, String type,
                 java.time.LocalDateTime dateHeureDebut,
                 java.time.LocalDateTime dateHeureFin,
                 OwnerType ownerType, int ownerId) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.dateHeureDebut = dateHeureDebut;
        this.dateHeureFin = dateHeureFin;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public java.time.LocalDateTime getDateHeureDebut() { return dateHeureDebut; }
    public void setDateHeureDebut(java.time.LocalDateTime dateHeureDebut) { this.dateHeureDebut = dateHeureDebut; }

    public java.time.LocalDateTime getDateHeureFin() { return dateHeureFin; }
    public void setDateHeureFin(java.time.LocalDateTime dateHeureFin) { this.dateHeureFin = dateHeureFin; }

    public OwnerType getOwnerType() { return ownerType; }
    public void setOwnerType(OwnerType ownerType) { this.ownerType = ownerType; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
}
