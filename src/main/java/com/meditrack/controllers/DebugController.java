package com.meditrack.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DebugController {

    /**
     * Endpoint pour générer des hashes BCrypt et tester l'authentication
     * Accès: http://localhost:8080/debug/bcrypt?password=monmotdepasse
     */
    @GetMapping("/debug/bcrypt")
    @ResponseBody
    public String generateBCryptHash(String password) {
        if (password == null || password.isEmpty()) {
            return "<h3>Usage: /debug/bcrypt?password=monmotdepasse</h3>" +
                   "<p>Utilise patient123 par défaut</p>";
        }

        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        boolean matches = BCrypt.checkpw(password, hash);

        return "<h2>BCrypt Hash Generator</h2>" +
               "<p><strong>Password:</strong> " + password + "</p>" +
               "<p><strong>Hash:</strong> <code>" + hash + "</code></p>" +
               "<p><strong>Verification:</strong> " + matches + "</p>" +
               "<hr>" +
               "<p><strong>SQL à copier dans phpMyAdmin:</strong></p>" +
               "<code>UPDATE users SET mot_de_passe_hash = '" + hash + "' WHERE email = 'patient@meditrack.fr';</code>";
    }

    /**
     * Endpoint pour tester si un email/password fonctionne
     * Accès: http://localhost:8080/debug/test?email=patient@meditrack.fr&password=patient123
     */
    @GetMapping("/debug/test")
    @ResponseBody
    public String testPassword(String email, String password) {
        if (email == null || password == null) {
            return "Usage: /debug/test?email=test@example.com&password=123456";
        }

        String testHash = "$2a$10$slYQmyNdGzin7olVxXOGrOjSJxCYBWcXx14vq6EV5.YG.zYSZfSK2"; // patient123
        String testHash2 = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhuG"; // medecin123

        boolean matches = BCrypt.checkpw(password, testHash) || BCrypt.checkpw(password, testHash2);

        return "<h2>Test Password</h2>" +
               "<p><strong>Email:</strong> " + email + "</p>" +
               "<p><strong>Password:</strong> " + password + "</p>" +
               "<p><strong>Matches test hash:</strong> " + matches + "</p>" +
               "<p><strong>Password is valid:</strong> " + (matches ? "✅ YES" : "❌ NO") + "</p>";
    }
}
