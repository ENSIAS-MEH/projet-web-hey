DROP DATABASE IF EXISTS meditrack_db;
CREATE DATABASE meditrack_db;
USE meditrack_db;

-- =============================================
-- TABLE 1 : Comptes utilisateurs (base commune)
-- =============================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    mot_de_passe_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL  -- 'PATIENT' ou 'MEDECIN'
);

-- =============================================
-- TABLE 2 : Profils Patients
-- =============================================
CREATE TABLE IF NOT EXISTS patients (
    user_id INT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    sexe VARCHAR(1) NOT NULL,
    date_naissance DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =============================================
-- TABLE 3 : Profils Médecins 
-- =============================================
CREATE TABLE IF NOT EXISTS doctors (
    user_id INT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    specialite VARCHAR(150) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =============================================
-- TABLE 4 : Constantes Vitales
-- =============================================
CREATE TABLE IF NOT EXISTS vitals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_mesure DATETIME NOT NULL,
    tension VARCHAR(20) NOT NULL,    -- ex: "12/8"
    poids DOUBLE NOT NULL,
    glycemie DOUBLE NOT NULL,
    patient_id INT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =============================================
-- TABLE 5 : Rendez-vous 
-- =============================================
CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_heure DATETIME NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'PLANIFIE',
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =============================================
-- TABLE 6 : Alertes Médecin
-- =============================================
CREATE TABLE IF NOT EXISTS alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(500) NOT NULL,
    date_creation DATETIME NOT NULL,
    lue BOOLEAN NOT NULL DEFAULT FALSE,
    doctor_id INT NOT NULL,
    patient_id INT,
    FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =============================================
-- DONNÉES DE TEST : Médecin et Patient
-- =============================================
-- Patient de test (Mot de passe: patient123)
INSERT INTO users (id, email, mot_de_passe_hash, role)
VALUES (2, 'patient@meditrack.fr', '$2a$10$slYQmyNdGzin7olVxXOGrOjSJxCYBWcXx14vq6EV5.YG.zYSZfSK2', 'PATIENT');

INSERT INTO patients (user_id, nom, prenom, sexe, date_naissance)
VALUES (2, 'Dupont', 'Alice', 'F', '1990-05-15');

-- Médecin de test (Mot de passe: medecin123)
INSERT INTO users (id, email, mot_de_passe_hash, role)
VALUES (1, 'dr.martin@meditrack.fr', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhuG', 'MEDECIN');

INSERT INTO doctors (user_id, nom, prenom, specialite)
VALUES (1, 'Martin', 'Jean', 'Médecine Générale');