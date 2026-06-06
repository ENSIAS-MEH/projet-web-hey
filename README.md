# Rapport du Projet MediTrack

## Noms et coordonnées des membres de l'équipe

- **Développeur 1 (Backend)** : [Nom], [Email], [Téléphone] - Responsable de l'architecture Spring Boot, logique métier, gestion des sessions et sécurité.
- **Développeur 2 (Frontend + Base de données)** : [Nom], [Email], [Téléphone] - Responsable du frontend, des templates Thymeleaf et de la gestion de la base de données.

## Table des matières

1. [Introduction et description du projet](#introduction-et-description-du-projet)
2. [Instructions d'installation et d'utilisation](#instructions-dinstallation-et-dutilisation)
3. [Description de l'architecture et des choix de conception](#description-de-larchitecture-et-des-choix-de-conception)
4. [Difficultés rencontrées et solutions apportées](#difficultés-rencontrées-et-solutions-apportées)
5. [Conclusion](#conclusion)

## Introduction et description du projet

MediTrack est une application web de suivi médical développée en Java utilisant le framework Spring Boot. L'objectif principal de cette application est de permettre la gestion des patients, des médecins, des rendez-vous, des signes vitaux et des alertes médicales. L'application offre une interface utilisateur pour les patients et les médecins, avec des fonctionnalités d'authentification, de gestion des profils et de suivi des données médicales.

Le projet a été réalisé dans le cadre d'un travail d'équipe pour démontrer les compétences en développement d'applications web avec Spring Boot, intégration de base de données MySQL et utilisation de templates Thymeleaf pour le frontend.

## Instructions d'installation et d'utilisation

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- MySQL 8.0+

### Installation

1. Clonez le repository :
   ```bash
   git clone https://github.com/ENSIAS-MEH/appoo-betateam.git
   cd appoo-betateam
   ```

2. Démarrez MySQL avec le panneau de contrôle XAMPP :
   - Ouvrez XAMPP Control Panel
   - Démarrez le service `MySQL`
   - Ouvrez `phpMyAdmin` à l'adresse `http://localhost/phpmyadmin` pour vérifier que la base est accessible

3. Configurez la base de données MySQL :
   - Créez une base de données nommée `meditrack`
   - Exécutez les scripts SQL dans `src/main/resources/sql/` :
     - `schema.sql` pour créer les tables
     - `init-users.sql` pour initialiser les utilisateurs

4. Modifiez la configuration de la base de données dans `src/main/java/com/meditrack/config/DatabaseConnection.java` si nécessaire.

4. Compilez et exécutez l'application :
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Utilisation

1. Ouvrez votre navigateur et allez sur `http://localhost:8080/login`
2. Connectez-vous avec un compte patient ou médecin
3. Naviguez dans l'application pour gérer les rendez-vous, consulter les signes vitaux, etc.

## Description de l'architecture et des choix de conception

### Architecture générale

L'application suit le pattern MVC (Modèle-Vue-Contrôleur) avec Spring Boot :

- **Modèles (Models)** : Classes Java représentant les entités (Patient, Doctor, Appointment, etc.)
- **Vues (Views)** : Templates Thymeleaf pour l'interface utilisateur
- **Contrôleurs (Controllers)** : Gestion des requêtes HTTP et logique de présentation

### Choix technologiques

- **Spring Boot** : Framework pour simplifier le développement d'applications Java
- **Thymeleaf** : Moteur de templates pour le rendu côté serveur
- **MySQL** : Base de données relationnelle
- **jBCrypt** : Pour le hachage des mots de passe
- **JUnit** : Pour les tests unitaires

### Structure des packages

- `controllers/` : Contrôleurs web
- `models/` : Classes de modèle
- `dao/` : Couche d'accès aux données
- `config/` : Configuration de l'application
- `utils/` : Utilitaires (hachage des mots de passe)

## Difficultés rencontrées et solutions apportées

### Difficulté 1 : Résolution des imports JUnit

**Problème** : Les imports `org.junit` ne pouvaient pas être résolus dans les classes de test.

**Solution** : Nous avons déplacé les classes de test de `src/main/java` vers `src/test/java` et ajouté la dépendance JUnit Jupiter dans le `pom.xml` avec le scope `test`.

### Difficulté 2 : Gestion des sessions utilisateur

**Problème** : Implémentation de l'authentification et de la gestion des rôles (patient/médecin).

**Solution** : Utilisation des sessions HTTP de Spring Boot avec vérification des rôles dans les contrôleurs.

### Difficulté 3 : Intégration base de données

**Problème** : Configuration de la connexion MySQL et exécution des scripts SQL.

**Solution** : Création d'une classe `DatabaseConnection` pour gérer la connexion et organisation des scripts SQL dans `resources/sql/`.

## Conclusion

Le projet MediTrack a permis de développer une application web complète de suivi médical en utilisant les technologies modernes du développement Java. Malgré quelques difficultés techniques, l'équipe a réussi à implémenter toutes les fonctionnalités requises avec une architecture propre et maintenable.

Les points forts du projet incluent :
- Utilisation efficace de Spring Boot pour le développement rapide
- Séparation claire des responsabilités (MVC)
- Sécurité avec hachage des mots de passe
- Tests unitaires pour valider la logique métier

Ce projet démontre les compétences acquises en développement d'applications web Java et prépare à des projets plus complexes dans le domaine de la santé numérique.