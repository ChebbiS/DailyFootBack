package com.dailyfoot.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Statistique> statistiques = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;
    private String name;
    private int age;
    private String nationality;
    private String poste;
    private String club;
    @Column(unique = true, nullable = false)
    private String email;
    private String image;

    @Column(name = "access_code", unique = true, nullable = false)
    private int accessCode;

    public Player() {
    }

    public Player(Agent agent, int playerId, String name, int age, String nationality,
                  String poste, String club, String email, String image, int accessCode) {
        this.agent = agent;
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.poste = poste;
        this.club = club;
        this.email = email;
        this.image = image;
        this.accessCode = accessCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }
}
