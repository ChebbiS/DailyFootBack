package com.dailyfoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Agent {

    @Id
    private int agentId;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String role;

    public Agent() {}

    public Agent(int agentId, String nom, String prenom, String email, String password, String role) {
        this.agentId = agentId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
