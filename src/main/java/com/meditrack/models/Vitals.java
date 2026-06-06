package com.meditrack.models;

import java.util.Date;

public class Vitals {
    private int id;
    private Date dateMesure;
    private String tension;
    private double poids;
    private double glycemie;
    private int patientId; // Clé étrangère pour relier au Patient

    public Vitals() {}

    public Vitals(int id, Date dateMesure, String tension, double poids, double glycemie, int patientId) {
        this.id = id;
        this.dateMesure = dateMesure;
        this.tension = tension;
        this.poids = poids;
        this.glycemie = glycemie;
        this.patientId = patientId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDateMesure() { return dateMesure; }
    public void setDateMesure(Date dateMesure) { this.dateMesure = dateMesure; }
    public String getTension() { return tension; }
    public void setTension(String tension) { this.tension = tension; }
    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }
    public double getGlycemie() { return glycemie; }
    public void setGlycemie(double glycemie) { this.glycemie = glycemie; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
}