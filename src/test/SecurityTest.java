package com.meditrack.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SecurityTest {

    @Test
    public void testSQLInjectionProtection() {
        UserDAO userDAO = new UserDAOImpl();
        
        // On simule une attaque par injection SQL dans le champ email
        String tentativeInjection = "nizarelamrani21@gmail.com' OR '1'='1";
        
        // La méthode doit renvoyer null (échec de connexion) et non pas donner l'accès
        String resultat = userDAO.authentifierEtGetRole(tentativeInjection, "nimportequoi");
        
        assertNull(resultat, "La faille d'injection SQL doit être bloquée par le PreparedStatement");
    }
}