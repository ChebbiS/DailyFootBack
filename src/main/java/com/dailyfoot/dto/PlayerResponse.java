package com.dailyfoot.dto;

public class PlayerResponse {
    private String name;
    private String poste;
    private String image;
    private String club;
    private int age;
    private String nationality;

    public PlayerResponse(String name, String poste, String image, String club, int age, String nationality) {
        this.name = name;
        this.poste = poste;
        this.image = image;
        this.club = club;
        this.age = age;
        this.nationality = nationality;
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
