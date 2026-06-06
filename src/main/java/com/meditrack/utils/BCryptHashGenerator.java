package com.meditrack.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHashGenerator {
    public static void main(String[] args) {
        String patient = "patient123";
        String medecin = "medecin123";

        String hashPatient = BCrypt.hashpw(patient, BCrypt.gensalt());
        String hashMedecin = BCrypt.hashpw(medecin, BCrypt.gensalt());

        System.out.println("Hash pour 'patient123': " + hashPatient);
        System.out.println("Hash pour 'medecin123': " + hashMedecin);

        System.out.println("\n--- Verification ---");
        System.out.println("Patient verify: " + BCrypt.checkpw(patient, hashPatient));
        System.out.println("Medecin verify: " + BCrypt.checkpw(medecin, hashMedecin));
        
        System.out.println("\nExecute this SQL:");
        System.out.println("UPDATE users SET mot_de_passe_hash = '" + hashPatient + "' WHERE email = 'patient@meditrack.fr';");
        System.out.println("UPDATE users SET mot_de_passe_hash = '" + hashMedecin + "' WHERE email = 'dr.martin@meditrack.fr';");
    }
}
