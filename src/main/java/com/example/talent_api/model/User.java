package com.example.talent_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @GeneratedValue
    @Id
    private int id;

    private String username;
    private String password;
    private String type;

    
    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    
}
