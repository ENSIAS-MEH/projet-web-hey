package com.meditrack.dao;

import com.meditrack.models.Alert;
import java.util.List;

public interface AlertDAO {
    // Créer une alerte pour un médecin spécifique
    void createAlert(int doctorId, String message, int patientId);
    void createAlert(int doctorId, String message); // méthode overloadée pour backward compatibility
    
    // Récupérer les alertes du tableau de bord du médecin
    List<Alert> getAlertesNonLues(int doctorId);
    
    // Récupérer les alertes concernant un patient spécifique
    List<Alert> getAlertesPourPatient(int patientId);
    
    // Marquer l'alerte comme traitée
    void marquerCommeLue(int alertId);
}