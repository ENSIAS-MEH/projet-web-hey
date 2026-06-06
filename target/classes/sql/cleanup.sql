-- =====================================================
-- NETTOYAGE DE LA BASE DE DONNÉES
-- Supprime les utilisateurs de test
-- =====================================================

-- Supprimer tous les patients et médecins pour recommencer
DELETE FROM patients;
DELETE FROM doctors;
DELETE FROM users;

-- Supprimer les données de test pour les tables connexes
DELETE FROM appointments;
DELETE FROM vitals;
DELETE FROM alerts;

-- Réinitialiser les auto-increment
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE patients AUTO_INCREMENT = 1;
ALTER TABLE doctors AUTO_INCREMENT = 1;

-- =====================================================
-- VERIFICATION
-- =====================================================
SELECT COUNT(*) as "Utilisateurs" FROM users;
SELECT COUNT(*) as "Patients" FROM patients;
SELECT COUNT(*) as "Docteurs" FROM doctors;
