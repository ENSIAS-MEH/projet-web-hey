package com.meditrack.models;

public class Doctor extends User {
    private String nom;
    private String prenom;
    private String specialite;

    public Doctor() {
        this.role = "MEDECIN";
    }

    public Doctor(int id, String email, String motDePasseHash, String nom, String prenom, String specialite) {
        super(id, email, motDePasseHash, "MEDECIN");
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
}