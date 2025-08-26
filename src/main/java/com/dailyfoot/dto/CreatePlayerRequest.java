package com.dailyfoot.dto;

public class CreatePlayerRequest {
    private String name;
    private int age;
    private String nationality;
    private String poste;
    private String club;
    private String email;
    private String image;

    public CreatePlayerRequest() {}

    public CreatePlayerRequest(String name, int age, String nationality,
                               String poste, String club, String email, String image) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.poste = poste;
        this.club = club;
        this.email = email;
        this.image = image;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public String getClub() { return club; }
    public void setClub(String club) { this.club = club; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
