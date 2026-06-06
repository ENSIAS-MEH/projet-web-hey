package com.meditrack.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Identifiants par défaut de XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/meditrack_db";
    private static final String USER = "root";
    private static final String PASS = ""; 

    private static Connection connection = null;

    // Pattern Singleton : garantit qu'une seule connexion est ouverte
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Charge le driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("✅ Connexion à la base de données réussie !");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Erreur : Driver MySQL introuvable.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}