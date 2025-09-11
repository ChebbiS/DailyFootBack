package com.dailyfoot.dto;

import com.dailyfoot.entities.Event;
import java.time.LocalDateTime;

public class EventDTO {
    private String title;
    private String description; // match, entrainement, medical, autre
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private Event.OwnerType ownerType; // AGENT ou PLAYER

    // getters / setters
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
}
