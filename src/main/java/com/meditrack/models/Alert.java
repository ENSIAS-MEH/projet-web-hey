package com.meditrack.models;

import java.util.Date;

public class Alert {
    private int id;
    private String message;
    private Date dateCreation;
    private boolean lue;
    private int doctorId; // Le médecin qui reçoit l'alerte
    private int patientId; // Le patient concerné par l'alerte

    public Alert() {}

    public Alert(int id, String message, Date dateCreation, boolean lue, int doctorId) {
        this.id = id;
        this.message = message;
        this.dateCreation = dateCreation;
        this.lue = lue;
        this.doctorId = doctorId;
    }

    public Alert(int id, String message, Date dateCreation, boolean lue, int doctorId, int patientId) {
        this.id = id;
        this.message = message;
        this.dateCreation = dateCreation;
        this.lue = lue;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    public boolean isLue() { return lue; }
    public void setLue(boolean lue) { this.lue = lue; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
}