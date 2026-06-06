package com.meditrack.dao;

import com.meditrack.models.Vitals;
import java.util.List;

public interface VitalsDAO {
    // Méthodes issues du diagramme UML
    void save(Vitals v);
    List<Vitals> findByPatient(int patientId);
}