package com.dailyfoot.dto;

import com.dailyfoot.entities.Event;
import java.time.LocalDateTime;

public class EventDisplayDTO {
    private int id;
    private String title;
    private String description;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private Event.OwnerType ownerType;
    private String userName;

    public EventDisplayDTO(Event event, String userName) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.dateHeureDebut = event.getDateHeureDebut();
        this.dateHeureFin = event.getDateHeureFin();
        this.ownerType = event.getOwnerType();
        this.userName = userName;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateHeureDebut() { return dateHeureDebut; }
    public void setDateHeureDebut(LocalDateTime dateHeureDebut) { this.dateHeureDebut = dateHeureDebut; }

    public LocalDateTime getDateHeureFin() { return dateHeureFin; }
    public void setDateHeureFin(LocalDateTime dateHeureFin) { this.dateHeureFin = dateHeureFin; }

    public Event.OwnerType getOwnerType() { return ownerType; }
    public void setOwnerType(Event.OwnerType ownerType) { this.ownerType = ownerType; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
