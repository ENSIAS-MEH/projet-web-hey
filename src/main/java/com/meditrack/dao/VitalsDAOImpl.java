package com.meditrack.dao;

import com.meditrack.config.DatabaseConnection;
import com.meditrack.models.Vitals;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VitalsDAOImpl implements VitalsDAO {

    @Override
    public void save(Vitals v) {
        String query = "INSERT INTO vitals (date_mesure, tension, poids, glycemie, patient_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setTimestamp(1, new Timestamp(v.getDateMesure().getTime()));
            pstmt.setString(2, v.getTension());
            pstmt.setDouble(3, v.getPoids());
            pstmt.setDouble(4, v.getGlycemie());
            pstmt.setInt(5, v.getPatientId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vitals> findByPatient(int patientId) {
        List<Vitals> historique = new ArrayList<>();
        String query = "SELECT * FROM vitals WHERE patient_id = ? ORDER BY date_mesure DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Vitals v = new Vitals(
                    rs.getInt("id"),
                    rs.getTimestamp("date_mesure"),
                    rs.getString("tension"),
                    rs.getDouble("poids"),
                    rs.getDouble("glycemie"),
                    rs.getInt("patient_id")
                );
                historique.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historique;
    }
}