package com.example.talent_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @GeneratedValue
    @Id
    private Integer id;

    private String username;
    private String password;
    private String type;

    
    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public String getType() {return this.type;}
    public Integer getId() {return this.id;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setType(String type) {this.type = type;}
    public void setId(Integer id) {this.id = id;}


    
}
