package com.meditrack.models;

import java.util.Date;

public class Patient extends User {
    private String nom;
    private String prenom;
    private String sexe;
    private Date dateNaissance;

    public Patient() {
        this.role = "PATIENT";
    }

    public Patient(int id, String email, String motDePasseHash, String nom, String prenom, String sexe, Date dateNaissance) {
        super(id, email, motDePasseHash, "PATIENT");
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
}