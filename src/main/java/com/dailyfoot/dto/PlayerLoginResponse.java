package com.dailyfoot.dto;

public class PlayerLoginResponse {
    private String name;
    private String post;
    private String image;
    private String club;
    private int age;
    private String nationality;

    public PlayerLoginResponse(String name, String post, String image, String club, int age, String nationality) {
        this.name = name;
        this.post = post;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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
