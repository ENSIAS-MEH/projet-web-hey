package com.meditrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediTrackApplication {
    public static void main(String[] args) {
        // Lance le serveur Web intégré (Tomcat)
        SpringApplication.run(MediTrackApplication.class, args);
        System.out.println("\n🚀 LE SERVEUR EST EN LIGNE ! Ouvrez votre navigateur sur : http://localhost:8080/login\n");
    }
}