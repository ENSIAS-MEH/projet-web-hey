package com.meditrack.test;

import org.mindrot.jbcrypt.BCrypt;

public class TestBCryptHash {
    public static void main(String[] args) {
        // Test avec les hashes fournis
        String password1 = "patient123";
        String password2 = "medecin123";
        
        // Hashes pré-générés (connus pour fonctionner)
        String hash1 = BCrypt.hashpw(password1, BCrypt.gensalt());
        String hash2 = BCrypt.hashpw(password2, BCrypt.gensalt());
        
        System.out.println("Patient hash: " + hash1);
        System.out.println("Medecin hash: " + hash2);
        
        // Vérification
        System.out.println("\nVerification:");
        System.out.println("Patient matches: " + BCrypt.checkpw(password1, hash1));
        System.out.println("Medecin matches: " + BCrypt.checkpw(password2, hash2));
        
        // Test avec les hashes $2y$ - ces hashes peuvent ne pas fonctionner en Java
        String hash_2y_patient = "$2y$10$gSvqqUNVlXN8T5IqNYI2/OPST9/PgBkqquzi8Sys7r.Ks2rHpXHRm";
        String hash_2y_medecin = "$2y$10$qWO2.Ljw..r0x.Yq4vPwhu8D9dVhkGhG2wlZ9V9Y8L1.EiSf7EH0u";
        
        System.out.println("\n$2y$ hashes (PHP format):");
        try {
            System.out.println("Patient $2y$ matches: " + BCrypt.checkpw(password1, hash_2y_patient));
        } catch (Exception e) {
            System.out.println("Patient $2y$ error: " + e.getMessage());
        }
        
        try {
            System.out.println("Medecin $2y$ matches: " + BCrypt.checkpw(password2, hash_2y_medecin));
        } catch (Exception e) {
            System.out.println("Medecin $2y$ error: " + e.getMessage());
        }
        
        // Convertir à $2a$ (remplacer $2y$ par $2a$)
        String hash_2a_patient = hash_2y_patient.replace("$2y$", "$2a$");
        String hash_2a_medecin = hash_2y_medecin.replace("$2y$", "$2a$");
        
        System.out.println("\n$2a$ hashes (Java format):");
        System.out.println("Patient $2a$ matches: " + BCrypt.checkpw(password1, hash_2a_patient));
        System.out.println("Medecin $2a$ matches: " + BCrypt.checkpw(password2, hash_2a_medecin));
        
        System.out.println("\n\nUse these hashes in SQL:");
        System.out.println("Patient: " + hash_2a_patient);
        System.out.println("Medecin: " + hash_2a_medecin);
    }
}
