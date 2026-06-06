# ORGANISATION ÉQUITABLE DU PROJET MEDITRACK
# Répartition des tâches entre 2 développeurs pour GitHub

================================================================================
                            STRATÉGIE DE RÉPARTITION
================================================================================

Le projet MediTrack est divisé en 2 grandes parties équilibrées :

🔵 DÉVELOPPEUR 1 (Backend Java) - ~55% du travail
🔴 DÉVELOPPEUR 2 (Frontend + Base de données) - ~45% du travail

Cette répartition assure :
- Équilibre en termes de complexité technique
- Complémentarité des compétences
- Responsabilités claires
- Possibilité de travailler en parallèle

================================================================================
                        DÉVELOPPEUR 1 - BACKEND JAVA
================================================================================

📁 RESPONSABILITÉS PRINCIPALES :
├── Architecture Spring Boot
├── Logique métier
├── Gestion des sessions
└── Sécurité (authentification)

📂 FICHIERS À GÉRER :

1. **Couche Contrôleur** (WebController.java)
   ├── Gestion des requêtes HTTP
   ├── Validation des données
   ├── Gestion des sessions utilisateur
   └── Redirections selon les rôles

2. **Modèles de données** (package models/)
   ├── User.java (classe abstraite)
   ├── Patient.java
   ├── Doctor.java
   ├── Appointment.java
   ├── Vitals.java
   └── Alert.java

3. **Configuration** (package config/)
   ├── DatabaseConnection.java
   └── Configuration Spring Boot

4. **Application principale**
   └── MediTrackApplication.java

5. **Utilitaires** (package utils/)
   └── BCryptHashGenerator.java

================================================================================
                        DÉVELOPPEUR 2 - FRONTEND + BASE DE DONNÉES
================================================================================

📁 RESPONSABILITÉS PRINCIPALES :
├── Interface utilisateur
├── Schéma de base de données
├── Tests et validation
└── Documentation

📂 FICHIERS À GÉRER :

1. **Templates HTML** (resources/templates/)
   ├── login.html
   ├── register.html
   ├── dashboard.html
   └── doctor-dashboard.html

2. **Scripts SQL** (resources/sql/)
   ├── schema.sql (structure BD)
   ├── data.sql (données de test)
   └── cleanup.sql

3. **Couche DAO - Partie Base de données** (package dao/)
   ├── UserDAO.java + UserDAOImpl.java
   ├── DoctorDAO.java + DoctorDAOImpl.java
   └── AlertDAO.java + AlertDAOImpl.java

4. **Tests** (package test/)
   └── TestBCryptHash.java

5. **Documentation et guides**
   ├── README.md
   ├── GUIDE_INSCRIPTION.txt
   ├── UPDATE_DOCTORS_LIST.txt
   └── INSTRUCTIONS_LOGIN.txt

================================================================================
                            WORKFLOW GITHUB ÉQUITABLE
================================================================================

PHASE 1: INITIALISATION (Jour 1)
─────────────────────────────────

1. **Création du repository GitHub**
   - Un des deux développeurs crée le repo
   - L'autre est ajouté comme collaborateur

2. **Structure initiale**
   - Développeur 1: Push la structure Maven + classes de base
   - Développeur 2: Push les templates HTML de base + schéma SQL

PHASE 2: DÉVELOPPEMENT PARALLÈLE (Jours 2-5)
────────────────────────────────────────────

**Développeur 1 travaille sur :**
- Implémentation des contrôleurs
- Création des modèles
- Configuration Spring Boot
- Logique d'authentification

**Développeur 2 travaille sur :**
- Design des interfaces HTML
- Schéma de base de données
- Implémentation des DAO
- Tests unitaires

**Points de synchronisation quotidiens :**
- Commit des changements à 17h
- Review du code de l'autre
- Résolution des conflits

PHASE 3: INTÉGRATION (Jours 6-7)
─────────────────────────────────

1. **Tests d'intégration**
   - Vérification que le backend communique avec le frontend
   - Tests des DAO avec la base de données
   - Validation des formulaires

2. **Corrections de bugs**
   - Résolution des problèmes découverts
   - Optimisations de performance

PHASE 4: FINALISATION (Jour 8)
───────────────────────────────

1. **Documentation finale**
2. **Tests de bout en bout**
3. **Déploiement**

================================================================================
                            RÈGLES DE COMMIT ÉQUITABLES
================================================================================

**Format des commits :**
```
feat: [composant] description courte

Exemples:
feat: controller - ajout authentification utilisateur
feat: template - formulaire inscription patient/docteur
feat: dao - implémentation UserDAO
feat: sql - schéma base de données
```

**Fréquence :**
- Commits réguliers (toutes les 2-3 heures)
- Pas de commits monstrueux
- Messages descriptifs

**Branches :**
- `main` : code stable
- `dev-backend` : branche du développeur 1
- `dev-frontend` : branche du développeur 2
- Merge requests pour intégrer les changements

================================================================================
                            COMMANDES GIT DÉTAILLÉES
================================================================================

**INSTRUCTIONS GÉNÉRALES :**
- Utilisez toujours `git status` avant de commiter pour voir les changements
- Commitez fréquemment avec des messages descriptifs
- Poussez régulièrement vers votre branche
- Tirez les changements de main quotidiennement
- Créez des Pull Requests pour merger vers main

**Pour Développeur 1 (Backend Java) - Branche: dev-backend**

1. **Configuration initiale :**
   ```bash
   # Cloner le repository
   git clone https://github.com/ENSIAS-MEH/appoo-betateam.git
   cd appoo-betateam

   # Créer et basculer vers votre branche
   git checkout -b dev-backend
   ```

2. **Travail sur les contrôleurs (WebController.java) :**
   ```bash
   # Vérifier les changements
   git status

   # Ajouter le fichier contrôleur
   git add src/main/java/com/meditrack/controllers/WebController.java

   # Commiter
   git commit -m "feat: controller - ajout logique authentification et sessions"

   # Pousser
   git push origin dev-backend
   ```

3. **Travail sur les modèles (package models/) :**
   ```bash
   # Ajouter tous les modèles
   git add src/main/java/com/meditrack/models/*.java

   # Commiter
   git commit -m "feat: models - implémentation classes User, Patient, Doctor, etc."

   # Pousser
   git push origin dev-backend
   ```

4. **Travail sur la configuration (package config/) :**
   ```bash
   # Ajouter la configuration
   git add src/main/java/com/meditrack/config/*.java

   # Commiter
   git commit -m "feat: config - configuration base de données et Spring Boot"

   # Pousser
   git push origin dev-backend
   ```

5. **Travail sur l'application principale :**
   ```bash
   # Ajouter MediTrackApplication.java
   git add src/main/java/com/meditrack/MediTrackApplication.java

   # Commiter
   git commit -m "feat: app - configuration application Spring Boot"

   # Pousser
   git push origin dev-backend
   ```

6. **Travail sur les utilitaires :**
   ```bash
   # Ajouter BCryptHashGenerator.java
   git add src/main/java/com/meditrack/utils/BCryptHashGenerator.java

   # Commiter
   git commit -m "feat: utils - utilitaire génération hash BCrypt"

   # Pousser
   git push origin dev-backend
   ```

7. **Synchronisation quotidienne :**
   ```bash
   # Tirer les changements de main
   git checkout main
   git pull origin main

   # Rebaser votre branche
   git checkout dev-backend
   git rebase main

   # Résoudre les conflits si nécessaire, puis
   git add <fichiers_conflits>
   git rebase --continue

   # Pousser après rebase
   git push origin dev-backend --force-with-lease
   ```

**Pour Développeur 2 (Frontend + Base de données) - Branche: dev-frontend**

1. **Configuration initiale :**
   ```bash
   # Cloner le repository
   git clone https://github.com/ENSIAS-MEH/appoo-betateam.git
   cd appoo-betateam

   # Créer et basculer vers votre branche
   git checkout -b dev-frontend
   ```

2. **Travail sur les templates HTML :**
   ```bash
   # Ajouter tous les templates
   git add src/main/resources/templates/*.html

   # Commiter
   git commit -m "feat: template - mise à jour interface login/register/dashboard"

   # Pousser
   git push origin dev-frontend
   ```

3. **Travail sur les scripts SQL :**
   ```bash
   # Ajouter le schéma et données
   git add src/main/resources/sql/*.sql

   # Commiter
   git commit -m "feat: sql - création schéma base de données et données test"

   # Pousser
   git push origin dev-frontend
   ```

4. **Travail sur les DAO (partie base de données) :**
   ```bash
   # Ajouter les DAO que vous gérez
   git add src/main/java/com/meditrack/dao/UserDAO.java
   git add src/main/java/com/meditrack/dao/UserDAOImpl.java
   git add src/main/java/com/meditrack/dao/DoctorDAO.java
   git add src/main/java/com/meditrack/dao/DoctorDAOImpl.java
   git add src/main/java/com/meditrack/dao/AlertDAO.java
   git add src/main/java/com/meditrack/dao/AlertDAOImpl.java

   # Commiter
   git commit -m "feat: dao - implémentation UserDAO, DoctorDAO et AlertDAO"

   # Pousser
   git push origin dev-frontend
   ```

5. **Travail sur les tests :**
   ```bash
   # Ajouter les tests
   git add src/test/java/com/meditrack/test/*.java

   # Commiter
   git commit -m "feat: test - ajout tests BCrypt et validation"

   # Pousser
   git push origin dev-frontend
   ```

6. **Travail sur la documentation :**
   ```bash
   # Ajouter les fichiers de doc
   git add README.md
   git add *.txt

   # Commiter
   git commit -m "docs: mise à jour documentation et guides utilisateur"

   # Pousser
   git push origin dev-frontend
   ```

7. **Synchronisation quotidienne :**
   ```bash
   # Tirer les changements de main
   git checkout main
   git pull origin main

   # Rebaser votre branche
   git checkout dev-frontend
   git rebase main

   # Résoudre les conflits si nécessaire
   git add <fichiers_conflits>
   git rebase --continue

   # Pousser après rebase
   git push origin dev-frontend --force-with-lease
   ```

**Gestion des fichiers partagés :**
- Pour les fichiers comme `pom.xml` ou modèles partagés, communiquez avant de modifier
- Si conflit, discutez de la résolution
- Utilisez `git log --oneline` pour voir l'historique

**Création de Pull Request :**
```bash
# Après avoir poussé vos changements
# Allez sur GitHub et créez une PR de dev-backend/dev-frontend vers main
# Attendez la review de l'autre développeur
# Après approbation, mergez
```

================================================================================
                            RESPONSABILITÉS CROISÉES
================================================================================

**Les deux développeurs doivent :**
✅ Connaître l'architecture globale
✅ Pouvoir déboguer les parties de l'autre
✅ Participer aux tests d'intégration
✅ Documenter leur code
✅ Respecter les conventions de nommage

**Points de rencontre obligatoires :**
🔄 Authentification (session + rôles)
🔄 Modèles de données (User, Patient, Doctor)
🔄 DAO (interfaces communes)
🔄 Base de données (schémas partagés)

================================================================================
                            MÉTRIQUES D'ÉQUILIBRE
================================================================================

**Développeur 1 (Backend) :**
- Complexité : Élevée (logique métier, sécurité)
- Fichiers : ~15 fichiers Java
- Lignes de code : ~800-1000 lignes
- Technologies : Java, Spring Boot, BCrypt

**Développeur 2 (Frontend + BD) :**
- Complexité : Moyenne-Élevée (UI/UX + SQL)
- Fichiers : ~10 fichiers (HTML + SQL + Java)
- Lignes de code : ~600-800 lignes
- Technologies : HTML, Thymeleaf, MySQL, CSS

**Ratio équilibré : 55% / 45%**

================================================================================
                            PLAN DE CONTINGENCE
================================================================================

**Si un développeur est en retard :**
1. Notification immédiate à l'autre
2. Réévaluation des tâches restantes
3. Redistribution si nécessaire
4. Documentation des changements

**Si conflits Git :**
1. Communication avant résolution
2. Revue du code en conflit
3. Décision collégiale sur la solution

================================================================================
                            LIVRABLES ATTENDUS
================================================================================

**À la fin du projet :**
✅ Application fonctionnelle MediTrack
✅ Code documenté et testé
✅ Base de données opérationnelle
✅ Interface utilisateur intuitive
✅ Authentification sécurisée
✅ Gestion des rôles (Patient/Docteur)

**Métriques de succès :**
- ✅ Compilation sans erreur
- ✅ Tests d'authentification réussis
- ✅ Interface responsive
- ✅ Base de données cohérente
- ✅ Code review positif des deux côtés

================================================================================
                            SIGNATURE DU CONTRAT
================================================================================

Les deux développeurs s'engagent à :
1. Respecter cette répartition équitable
2. Communiquer régulièrement sur l'avancement
3. Aider l'autre en cas de difficulté
4. Livrer un produit de qualité

Date: ____________________
Développeur 1: ____________________
Développeur 2: ____________________

================================================================================
