package com.dailyfoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long Id; 

    private Long player_id;
    private String name;
    private int age;
    private String nationality;
    private String poste;
    private String club;
    private int access_code;
    
    public Player () {
        
    }

    public Player (Long Id, Long player_id,  String name, int age, String nationality, String poste, String club, int access_code) {
        
        this.Id = Id;
        this.player_id = player_id;
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.poste = poste;
        this.club = club;
        this.access_code = access_code;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(Long player_id) {
        this.player_id = player_id;
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

    public int getAccess_code() {
        return access_code;
    }

    public void setAccess_code(int access_code) {
        this.access_code = access_code;
    }
    
}
