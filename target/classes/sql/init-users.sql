-- Supprime les anciens utilisateurs
DELETE FROM patients WHERE user_id = 2;
DELETE FROM doctors WHERE user_id = 1;
DELETE FROM users WHERE id IN (1, 2);

-- Patient de test - Mot de passe: patient123
-- Hash: $2y$10$gSvqqUNVlXN8T5IqNYI2/OPST9/PgBkqquzi8Sys7r.Ks2rHpXHRm
INSERT INTO users (id, email, mot_de_passe_hash, role)
VALUES (2, 'patient@meditrack.fr', '$2y$10$gSvqqUNVlXN8T5IqNYI2/OPST9/PgBkqquzi8Sys7r.Ks2rHpXHRm', 'PATIENT');

INSERT INTO patients (user_id, nom, prenom, sexe, date_naissance)
VALUES (2, 'Dupont', 'Alice', 'F', '1990-05-15');

-- Medecin de test - Mot de passe: medecin123  
-- Hash: $2y$10$qWO2.Ljw..r0x.Yq4vPwhu8D9dVhkGhG2wlZ9V9Y8L1.EiSf7EH0u
INSERT INTO users (id, email, mot_de_passe_hash, role)
VALUES (1, 'dr.martin@meditrack.fr', '$2y$10$qWO2.Ljw..r0x.Yq4vPwhu8D9dVhkGhG2wlZ9V9Y8L1.EiSf7EH0u', 'MEDECIN');

INSERT INTO doctors (user_id, nom, prenom, specialite)
VALUES (1, 'Martin', 'Jean', 'Médecine Générale');
