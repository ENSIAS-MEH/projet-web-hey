package com.meditrack.dao;

import com.meditrack.config.DatabaseConnection;
import com.meditrack.models.Alert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Repository
public class AlertDAOImpl implements AlertDAO {

    @Override
    public void createAlert(int doctorId, String message, int patientId) {
        String query = "INSERT INTO alerts (message, date_creation, lue, doctor_id, patient_id) VALUES (?, ?, false, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, message);
            pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
            pstmt.setInt(3, doctorId);
            pstmt.setInt(4, patientId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void createAlert(int doctorId, String message) {
        createAlert(doctorId, message, 0);
    }

    @Override
    public List<Alert> getAlertesNonLues(int doctorId) {
        List<Alert> alertes = new ArrayList<>();
        String query = "SELECT * FROM alerts WHERE doctor_id = ? AND lue = false ORDER BY date_creation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int patientId = rs.getInt("patient_id");
                alertes.add(new Alert(rs.getInt("id"), rs.getString("message"),
                        rs.getTimestamp("date_creation"), rs.getBoolean("lue"), rs.getInt("doctor_id"), patientId));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return alertes;
    }

    @Override
    public List<Alert> getAlertesPourPatient(int patientId) {
        List<Alert> alertes = new ArrayList<>();
        String query = "SELECT * FROM alerts WHERE patient_id = ? ORDER BY date_creation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                alertes.add(new Alert(rs.getInt("id"), rs.getString("message"),
                        rs.getTimestamp("date_creation"), rs.getBoolean("lue"), rs.getInt("doctor_id"), patientId));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return alertes;
    }

    @Override
    public void marquerCommeLue(int alertId) {
        String query = "UPDATE alerts SET lue = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, alertId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}