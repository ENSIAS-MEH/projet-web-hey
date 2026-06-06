package com.meditrack.dao;

import com.meditrack.models.Doctor;
import java.util.List;

public interface DoctorDAO {
    // Récupérer la liste de tous les médecins
    List<Doctor> getAllDoctors();
    
    // Récupérer un médecin par son ID
    Doctor getDoctorById(int userId);
}
