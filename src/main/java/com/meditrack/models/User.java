package com.meditrack.models;

public abstract class User {
    protected int id;
    protected String email;
    protected String motDePasseHash;
    protected String role;

    public User() {}

    public User(int id, String email, String motDePasseHash, String role) {
        this.id = id;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.role = role;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasseHash() { return motDePasseHash; }
    public void setMotDePasseHash(String motDePasseHash) { this.motDePasseHash = motDePasseHash; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}