package com.meditrack.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class PatientTest {

    @Test
    public void testCreationPatient() {
        Date dateNaissance = new Date();
        Patient patient = new Patient(1, "patient@test.com", "hash123", "Dupont", "Jean", "M", dateNaissance);

        assertEquals("Dupont", patient.getNom(), "Le nom du patient doit être Dupont");
        assertEquals("PATIENT", patient.getRole(), "Le rôle doit être automatiquement PATIENT");
        assertNotNull(patient.getDateNaissance(), "La date de naissance ne doit pas être nulle");
    }

    @Test
    public void testModificationSexe() {
        Patient patient = new Patient();
        patient.setSexe("F");
        
        assertEquals("F", patient.getSexe(), "Le sexe doit être mis à jour correctement");
    }
}