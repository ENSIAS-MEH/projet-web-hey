package com.meditrack.dao;

import com.meditrack.models.User;
import com.meditrack.models.Patient;
import com.meditrack.models.Doctor;
import com.meditrack.config.DatabaseConnection;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class UserDAOImpl implements UserDAO {

    @Override
    public String authentifierEtGetRole(String email, String mdp) {
        String query = "SELECT id, mot_de_passe_hash, role FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashEnBase = rs.getString("mot_de_passe_hash");
                if (BCrypt.checkpw(mdp, hashEnBase)) {
                    int id = rs.getInt("id");
                    String role = rs.getString("role");
                    return role + ":" + id;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String creerCompte(User user) {
        String queryUser = "INSERT INTO users (email, mot_de_passe_hash, role) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtUser = conn.prepareStatement(queryUser, PreparedStatement.RETURN_GENERATED_KEYS)) {

                String hash = BCrypt.hashpw(user.getMotDePasseHash(), BCrypt.gensalt());
                pstmtUser.setString(1, user.getEmail());
                pstmtUser.setString(2, hash);
                pstmtUser.setString(3, user.getRole());

                int affectedRows = pstmtUser.executeUpdate();

                if (affectedRows > 0) {
                    ResultSet generatedKeys = pstmtUser.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        user.setId(userId);

                        boolean insertionDetailsReussie = false;

                        if (user instanceof Patient) {
                            insertionDetailsReussie = insererPatient((Patient) user, userId, conn);
                        } else if (user instanceof Doctor) {
                            insertionDetailsReussie = insererDoctor((Doctor) user, userId, conn);
                        }

                        if (insertionDetailsReussie) {
                            conn.commit();
                            return "SUCCESS";
                        } else {
                            conn.rollback();
                            return "Erreur : Impossible d'inserer les details du profil.";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            if (e.getMessage().contains("Duplicate entry")) {
                return "Erreur : Cette adresse email est deja utilisee.";
            }
            return "Erreur technique : " + e.getMessage();
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return "Erreur technique inconnue.";
    }

    private boolean insererPatient(Patient patient, int userId, Connection conn) throws SQLException {
        String query = "INSERT INTO patients (user_id, nom, prenom, sexe, date_naissance) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, patient.getNom());
            pstmt.setString(3, patient.getPrenom());
            pstmt.setString(4, patient.getSexe());
            pstmt.setDate(5, new java.sql.Date(patient.getDateNaissance().getTime()));
            return pstmt.executeUpdate() > 0;
        }
    }

    private boolean insererDoctor(Doctor doctor, int userId, Connection conn) throws SQLException {
        String query = "INSERT INTO doctors (user_id, nom, prenom, specialite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, doctor.getNom());
            pstmt.setString(3, doctor.getPrenom());
            pstmt.setString(4, doctor.getSpecialite());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public User findById(int id) {
        String query = "SELECT u.id, u.email, u.role FROM users u WHERE u.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if ("PATIENT".equals(role)) {
                    return trouverPatient(id, rs.getString("email"), conn);
                } else if ("MEDECIN".equals(role)) {
                    return trouverDoctor(id, rs.getString("email"), conn);
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return null;
    }

    private Patient trouverPatient(int userId, String email, Connection conn) throws SQLException {
        String q = "SELECT nom, prenom, sexe, date_naissance FROM patients WHERE user_id = ?";
        try (PreparedStatement p = conn.prepareStatement(q)) {
            p.setInt(1, userId);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return new Patient(userId, email, "", rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("sexe"), rs.getDate("date_naissance"));
            }
        }
        return null;
    }

    private Doctor trouverDoctor(int userId, String email, Connection conn) throws SQLException {
        String q = "SELECT nom, prenom, specialite FROM doctors WHERE user_id = ?";
        try (PreparedStatement p = conn.prepareStatement(q)) {
            p.setInt(1, userId);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return new Doctor(userId, email, "", rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("specialite"));
            }
        }
        return null;
    }
}
