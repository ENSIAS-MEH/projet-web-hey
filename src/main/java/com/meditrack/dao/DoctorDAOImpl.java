package com.meditrack.dao;

import com.meditrack.models.Doctor;
import com.meditrack.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT u.id, u.email, d.nom, d.prenom, d.specialite FROM users u " +
                       "INNER JOIN doctors d ON u.id = d.user_id WHERE u.role = 'MEDECIN'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getInt("id"),
                    rs.getString("email"),
                    "", // Le mot de passe n'est pas nécessaire
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("specialite")
                );
                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    @Override
    public Doctor getDoctorById(int userId) {
        String query = "SELECT u.id, u.email, d.nom, d.prenom, d.specialite FROM users u " +
                       "INNER JOIN doctors d ON u.id = d.user_id WHERE u.id = ? AND u.role = 'MEDECIN'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Doctor(
                    rs.getInt("id"),
                    rs.getString("email"),
                    "",
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("specialite")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
