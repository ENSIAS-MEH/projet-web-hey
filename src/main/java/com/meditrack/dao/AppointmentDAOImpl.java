package com.meditrack.dao;

import com.meditrack.config.DatabaseConnection;
import com.meditrack.models.Appointment;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public boolean checkDisponibilite(int doctorId, Date dateHeure) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND date_heure = ? AND statut != 'ANNULE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, doctorId);
            pstmt.setTimestamp(2, new Timestamp(dateHeure.getTime()));
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) == 0; // Retourne true si le créneau est libre (0 rendez-vous)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveAppointment(int patientId, int doctorId, Date dateHeure, String statut) {
        String query = "INSERT INTO appointments (date_heure, statut, patient_id, doctor_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setTimestamp(1, new Timestamp(dateHeure.getTime()));
            pstmt.setString(2, statut);
            pstmt.setInt(3, patientId);
            pstmt.setInt(4, doctorId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
public List<Appointment> getRendezVousMedecin(int doctorId) {
    List<Appointment> liste = new ArrayList<>();
    // ✅ CORRECTION : JOIN avec 'patients' et non 'users'
    String query = "SELECT a.*, p.nom as patient_nom, p.prenom as patient_prenom " +
                   "FROM appointments a " +
                   "JOIN patients p ON a.patient_id = p.user_id " +  // ← user_id, pas id
                   "WHERE a.doctor_id = ? AND a.statut != 'ANNULE' " +
                   "ORDER BY a.date_heure ASC";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, doctorId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            liste.add(new Appointment(
                rs.getInt("id"),
                rs.getTimestamp("date_heure"),
                rs.getString("statut"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id"),
                rs.getString("patient_nom"),
                rs.getString("patient_prenom")
            ));
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return liste;
}
    @Override
    public List<Appointment> getRendezVousPatient(int patientId) {
        List<Appointment> liste = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE patient_id = ? ORDER BY date_heure ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                liste.add(new Appointment(rs.getInt("id"), rs.getTimestamp("date_heure"), 
                                          rs.getString("statut"), rs.getInt("patient_id"), rs.getInt("doctor_id")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    @Override
        public List<Integer> getDoctorIdsForPatient(int patientId) {
            List<Integer> ids = new ArrayList<>();
            String query = "SELECT DISTINCT doctor_id FROM appointments WHERE patient_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    ids.add(rs.getInt("doctor_id"));
                }
            } catch (SQLException e) { e.printStackTrace(); }
            return ids;
        }

   @Override
    public boolean updateStatut(int appointmentId, String nouveauStatut) {
        String query = "UPDATE appointments SET statut = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nouveauStatut);
            pstmt.setInt(2, appointmentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}