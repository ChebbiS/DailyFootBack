package com.dailyfoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AgendaUser {

    @Id
    @GeneratedValue
    private Long agenda_id;
    private Long user_id;

    public AgendaUser() {

    }

    public AgendaUser (Long agenda_id, Long user_id) {
        this.agenda_id = agenda_id;
        this.user_id = user_id;
    }

    public Long getAgenda_id() {
        return agenda_id;
    }

    public void setAgenda_id(Long agenda_id) {
        this.agenda_id = agenda_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}