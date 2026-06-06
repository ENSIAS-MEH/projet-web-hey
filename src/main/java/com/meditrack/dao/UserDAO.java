package com.meditrack.dao;

import com.meditrack.models.User;

public interface UserDAO {
    // Authentifie et retourne le role avec l'ID: "PATIENT:3" ou "MEDECIN:1"
    String authentifierEtGetRole(String email, String mdp);
    
    // Recupere l'utilisateur par son ID
    User findById(int id);

    // Cree un compte utilisateur, retourne "SUCCESS" ou un message d'erreur
    String creerCompte(User user);
}
