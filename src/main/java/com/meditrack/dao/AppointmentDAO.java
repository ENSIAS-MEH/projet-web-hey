package com.meditrack.dao;

import com.meditrack.models.Appointment;
import java.util.Date;
import java.util.List;

public interface AppointmentDAO {
    // Vérifier si le médecin est libre à cette date (Diagramme de séquence 2)
    boolean checkDisponibilite(int doctorId, Date dateHeure);
    
    // Planifier un nouveau rendez-vous
    boolean saveAppointment(int patientId, int doctorId, Date dateHeure, String statut);
    
    // Lister les rendez-vous pour les tableaux de bord
    List<Appointment> getRendezVousMedecin(int doctorId);
    List<Appointment> getRendezVousPatient(int patientId);
    List<Integer> getDoctorIdsForPatient(int patientId);

    
    // Mettre à jour le statut (ex: Le médecin valide ou annule)
    boolean updateStatut(int appointmentId, String nouveauStatut);

    


}