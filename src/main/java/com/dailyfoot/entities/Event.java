package com.dailyfoot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;
    private String title;
    private String description;
    @Column(name = "date_heure_debut")
    private java.time.LocalDateTime dateHeureDebut;
    @Column(name = "date_heure_fin")
    private java.time.LocalDateTime dateHeureFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OwnerType ownerType;
    @Column(name = "owner_id", nullable = false)
    private int ownerId;

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public enum OwnerType {
        AGENT, PLAYER
    }

    public Event() {
    }

    public Event(String title, String description, String type,
                 java.time.LocalDateTime dateHeureDebut,
                 java.time.LocalDateTime dateHeureFin,
                 OwnerType ownerType, int ownerId) {
        this.title = title;
        this.description = description;
        this.dateHeureDebut = dateHeureDebut;
        this.dateHeureFin = dateHeureFin;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public java.time.LocalDateTime getDateHeureDebut() {
        return dateHeureDebut;
    }

    public void setDateHeureDebut(java.time.LocalDateTime dateHeureDebut) {
        this.dateHeureDebut = dateHeureDebut;
    }

    public java.time.LocalDateTime getDateHeureFin() {
        return dateHeureFin;
    }

    public void setDateHeureFin(java.time.LocalDateTime dateHeureFin) {
        this.dateHeureFin = dateHeureFin;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(OwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

}
