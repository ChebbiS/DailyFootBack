package com.dailyfoot.dto;

import com.dailyfoot.entities.Player;

public class PlayerDTO {
    private int id;
    private String name;
    private String poste;
    private String image;
    private String club;
    private int age;
    private String nationality;
    private String agentId;

    public PlayerDTO(String name, String poste, String image, String club, int age, String nationality) {
        this.name = name;
        this.poste = poste;
        this.image = image;
        this.club = club;
        this.age = age;
        this.nationality = nationality;
    }

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.poste = player.getPoste();
        this.image = player.getImage();
        this.club = player.getClub();
        this.age = player.getAge();
        this.nationality = player.getNationality();
        this.agentId = player.getAgent().getUser().getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
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
}
