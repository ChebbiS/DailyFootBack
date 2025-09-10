package com.dailyfoot.dto;

public class PlayerLoginDTO {
    private int id;
    private String name;
    private String poste;
    private String image;
    private String club;
    private int age;
    private String nationality;
    private String token;
    private String role;
    public PlayerLoginDTO(int id, String name, String poste, String image, String club, int age, String nationality, String token, String role) {
        this.id = id;
        this.name = name;
        this.poste = poste;
        this.image = image;
        this.club = club;
        this.age = age;
        this.nationality = nationality;
        this.token = token;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
