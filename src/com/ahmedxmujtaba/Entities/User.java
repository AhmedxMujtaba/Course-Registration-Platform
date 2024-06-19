package com.ahmedxmujtaba.Entities;

public class User {
    private int id;
    private String name;
    private String passwordHash;
    private String email;
    private int phoneNumber;

    // Constructor
    public User(int id, String name, String password, String email, int phoneNumber) {
        this.id = id;
        this.name = name;
        this.passwordHash = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public User(String name, String password, String email, int phoneNumber) {
        //id is null since no id is made if a new User is made until entered in DB
        this.name = name;
        this.passwordHash = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getId() {
        return id;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setId(int id) {
        this.id = id;
    }
}


