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
    private int userId;
    private double Height;
    private double Weight;

    public PlayerDTO(int id, String name, String poste, String image, String club, int age, String nationality, double Height, double Weight) {
        this.id = id;
        this.name = name;
        this.poste = poste;
        this.image = image;
        this.club = club;
        this.age = age;
        this.nationality = nationality;
        this.Height = Height;
        this.Weight = Weight;
    }

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.poste = player.getPoste();
        this.image = player.getImage();
        this.club = player.getClub();
        this.age = player.getAge();
        this.nationality = player.getNationality();
        // Stocker l'identifiant de l'agent en toute sécurité (évite NPE sur user)
        this.agentId = player.getAgent() != null ? String.valueOf(player.getAgent().getId()) : null;
        this.userId = player.getUser() != null ? player.getUser().getId() : null;
        this.Height = player.getHeight();
        this.Weight = player.getWeight();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }
}
