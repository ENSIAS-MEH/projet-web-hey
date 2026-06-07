package com.meditrack.models;

import java.util.Date;

public class Appointment {
    private int id;
    private Date dateHeure;
    private String statut;
    private int patientId;
    private int doctorId;
    private String patientNom;
    private String patientPrenom;

    public Appointment() {}

    public Appointment(int id, Date dateHeure, String statut, int patientId, int doctorId) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.statut = statut;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public Appointment(int id, Date dateHeure, String statut, int patientId, int doctorId, String patientNom, String patientPrenom) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.statut = statut;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientNom = patientNom;
        this.patientPrenom = patientPrenom;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDateHeure() { return dateHeure; }
    public void setDateHeure(Date dateHeure) { this.dateHeure = dateHeure; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public String getPatientNom() { return patientNom; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }
    public String getPatientPrenom() { return patientPrenom; }
    public void setPatientPrenom(String patientPrenom) { this.patientPrenom = patientPrenom; }
}