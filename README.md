## 1. Description du projet
•⁠  ⁠*Objectifs :* Développer une application web robuste permettant aux utilisateurs de gérer, transférer et versionner leurs fichiers de manière totalement sécurisée.
•⁠  ⁠*Contexte :* Projet académique réalisé dans le cadre de la première année du cycle d'ingénieur (filières INE1 et ICCN) à l'INPT. Il vise à mettre en pratique les concepts avancés de développement full-stack, d'administration système et de cybersécurité.
•⁠  ⁠*Fonctionnalités principales :*
  * Authentification et gestion des sessions utilisateurs.
  * Téléversement, téléchargement et suppression de fichiers.
  * Suivi et gestion des différentes versions d'un même fichier.
  * Tableau de bord analytique pour la gestion de l'espace de stockage.
•⁠  ⁠*Technologies utilisées :* * *Front-end :* React.js, Tailwind CSS.
  * *Back-end :* Python (Flask).
  * *Base de données :* PostgreSQL / SQLite.
  * *Infrastructure :* Linux, Nginx, Docker.
•⁠  ⁠*Cas d'utilisation :* Un utilisateur s'inscrit, s'authentifie, crée un répertoire de travail, dépose des fichiers sensibles, met à jour un fichier (création d'une version ⁠ v2 ⁠), et gère les droits d'accès.

---

## 2. Membres du projet
•⁠  ⁠*NIZAR EL AMRANI* * *Rôles & Responsabilités :* Lead Back-end, Administration Système & Sécurité.
  * *Contributions principales :* Architecture de l'API Flask, implémentation de l'authentification JWT, configuration du serveur Linux (gestion des ACL, stockage LVM), et sécurisation globale de l'application contre les vulnérabilités courantes.
•⁠  ⁠*WALID ERRAHOUI*
  * *Rôles & Responsabilités :* Lead Front-end, Qualité & UI/UX.
  * *Contributions principales :* Développement de l'interface utilisateur en React, gestion des états dynamiques, validation des formulaires côté client, et conception de l'expérience utilisateur globale.
•⁠  ⁠*SAID NAAMI*
  * *Rôles & Responsabilités :* Développeur Full-Stack & Intégration.
  * *Contributions principales :* Participation au développement des composants React, création d'endpoints Flask pour la gestion des fichiers, et mise en place des tests unitaires.

---

## 3. Architecture
L'architecture du système repose sur une *séparation stricte des préoccupations* (Decoupled Architecture) :
•⁠  ⁠*Front-end (Client-Side) :* Développé en React (SPA - Single Page Application). Justification : Permet une navigation fluide sans rechargement de page, une gestion complexe des états (avec Redux ou Context API) et une réactivité optimale pour la gestion des fichiers.
•⁠  ⁠*Back-end (Server-Side) :* Développé avec Flask (Python). Justification : Flask offre la légèreté nécessaire pour concevoir une API RESTful performante, tout en s'intégrant parfaitement avec les scripts d'administration système Linux et les librairies de sécurité Python.
•⁠  ⁠*Communication :* Les deux entités communiquent exclusivement via des requêtes HTTP/HTTPS au format JSON.

---

## 4. Code source
•⁠  ⁠*Organisation et Structure des dossiers :*
  ⁠ text
  ├── frontend/
  │   ├── src/ (components, hooks, pages, services)
  │   └── package.json
  ├── backend/
  │   ├── app/ (routes, models, controllers, utils)
  │   └── requirements.txt
  └── docs/
   ⁠
•⁠  ⁠*Conventions de nommage :* ⁠ camelCase ⁠ pour les variables/fonctions JavaScript, ⁠ PascalCase ⁠ pour les composants React, ⁠ snake_case ⁠ pour Python.
•⁠  ⁠*Qualité du code :* Utilisation de ESLint/Prettier pour le front-end et de Flake8 pour le back-end.
•⁠  ⁠*Stratégie Git :* Utilisation de *Git Flow* (⁠ main ⁠ pour la production, ⁠ develop ⁠ pour l'intégration, et des branches ⁠ feature/nom-fonctionnalite ⁠).
•⁠  ⁠*Intégration continue (CI) :* Workflows GitHub Actions configurés pour exécuter le linting et les tests à chaque Push/Pull Request.
•⁠  ⁠*Tests :* * Unitaires : Jest pour React, ⁠ pytest ⁠ pour Flask.
  * Intégration : Tests des endpoints API avec Postman/Newman.

---

## 5. Gestion des erreurs et Expérience Utilisateur (UI/UX)
•⁠  ⁠*Gestion côté client :* Implémentation d'un "Global Error Boundary" en React pour éviter les crashs de l'application.
•⁠  ⁠*Feedback visuel :* Utilisation de composants "Toast" (ex: React Toastify) pour afficher des messages explicites (succès, avertissements, erreurs HTTP 400/500).
•⁠  ⁠*États de chargement :* Intégration de "Skeleton Loaders" et de spinners lors des requêtes asynchrones pour indiquer à l'utilisateur qu'une action est en cours.
•⁠  ⁠*Amélioration de l'accessibilité :* Messages d'erreurs colorimétriques associés à des icônes pour être compréhensibles sans dépendre uniquement de la couleur.

---

## 6. Validation des données
•⁠  ⁠*Côté Front-end :* Utilisation de ⁠ Yup ⁠ et ⁠ Formik ⁠ pour vérifier la validité des champs (format d'email, longueur des mots de passe, types de fichiers) avant l'envoi de la requête afin de réduire la charge serveur.
•⁠  ⁠*Côté Back-end :* Validation stricte via Pydantic ou Marshmallow. Aucun input n'est "trusted".
•⁠  ⁠*Contraintes métier :* Contrôle des tailles maximales de téléversement et des extensions de fichiers autorisées côté serveur pour prévenir le stockage d'exécutables malveillants.

---

## 7. Sécurité
L'application intègre les principes de "Security by Design" :
•⁠  ⁠*Injections SQL :* Utilisation exclusive de requêtes paramétrées via l'ORM (SQLAlchemy).
•⁠  ⁠*XSS (Cross-Site Scripting) :* Échappement automatique des données affichées par React et assainissement (sanitization) des entrées côté serveur.
•⁠  ⁠*CSRF :* Configuration stricte des cookies (attributs ⁠ HttpOnly ⁠, ⁠ Secure ⁠, ⁠ SameSite=Strict ⁠).
•⁠  ⁠*Authentification et Autorisation :* Implémentation de JWT (JSON Web Tokens) avec des délais d'expiration courts et gestion du rafraîchissement des tokens. Contrôle des accès basé sur les rôles (RBAC).
•⁠  ⁠*Stockage des mots de passe :* Hachage systématique avec ⁠ bcrypt ⁠ et un "salt" unique par utilisateur.
•⁠  ⁠*Variables sensibles :* Les secrets d'API et clés de chiffrement ne sont jamais commités et sont gérés via des fichiers ⁠ .env ⁠ ou un gestionnaire de secrets.

---

## 8. Compatibilité multiplateforme
•⁠  ⁠*Responsive Design :* Utilisation d'une approche "Mobile-First" avec Tailwind CSS.
•⁠  ⁠*Tests de compatibilité :* Validation du fonctionnement de l'interface et du téléversement sur :
  * *Navigateurs :* Chrome, Firefox, Safari, Edge.
  * *Appareils :* Ordinateurs de bureau (1080p, 4K), tablettes (iPad) et smartphones (iOS, Android).

---

## 9. Documentation
•⁠  ⁠*Code source :* Les fonctions back-end complexes (algorithmes de versionnement, configuration des ACL Linux) sont documentées via des "Docstrings" standards.
•⁠  ⁠*Guide de déploiement :* Les instructions détaillées pour installer les dépendances, configurer la base de données et lancer les serveurs sont incluses dans un fichier ⁠ INSTALL.md ⁠ annexe.
•⁠  ⁠*API Reference :* Génération automatique de la documentation des endpoints REST avec Swagger/OpenAPI.

---

## 10. Accessibilité (A11y)
•⁠  ⁠*Contrastes :* Respect des ratios de contraste WCAG 2.1 (minimum 4.5:1 pour le texte normal).
•⁠  ⁠*Navigation clavier :* Tous les éléments interactifs (boutons, formulaires de téléversement) sont atteignables via la touche ⁠ Tab ⁠ avec un indicateur de focus visible (⁠ :focus-visible ⁠).
•⁠  ⁠*Lecteurs d'écran :* Ajout des balises ARIA (⁠ aria-label ⁠, ⁠ aria-hidden ⁠) pour les icônes et les éléments visuels non textuels.

---

## 11. Gestion de la configuration
•⁠  ⁠*Variables d'environnement :* Séparation stricte entre les environnements de ⁠ development ⁠, ⁠ testing ⁠, et ⁠ production ⁠.
•⁠  ⁠*Fichiers de config :* Un fichier ⁠ .env.example ⁠ est fourni dans le dépôt, listant les clés nécessaires sans révéler leurs valeurs réelles.

---

## 12. Démo vidéo de l'application
(https://drive.google.com/drive/folders/1xvujw5q4sMhMrbKQQWIZLNgecKkxbLdN?usp=sharing)
