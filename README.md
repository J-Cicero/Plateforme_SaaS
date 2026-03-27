# Plateforme SaaS

Une plateforme SaaS complète et modulaire construite avec Spring Boot, conçue pour gérer les campagnes, les contacts et les intégrations de réseaux sociaux.

## 📋 Table des matières

- [Vue d'ensemble](#vue-densemble)
- [Architecture](#architecture)
- [Modules principaux](#modules-principaux)
- [Technologies utilisées](#technologies-utilisées)
- [Installation et configuration](#installation-et-configuration)
- [Lancer le projet](#lancer-le-projet)
- [Structure du projet](#structure-du-projet)
- [API endpoints](#api-endpoints)

## Vue d'ensemble

Cette plateforme est une solution complète pour gérer:
- **Campagnes**: Création, gestion et suivi des campagnes marketing
- **Contacts**: Gestion centralisée des contacts et listes de diffusion
- **Réseaux sociaux**: Intégration et gestion des comptes de réseaux sociaux
- **Authentification**: Système de sécurité robuste avec JWT
- **Gestion des utilisateurs**: Gestion complète des rôles et permissions

## Architecture

Le projet suit une **architecture en couches** avec une organisation modulaire par domaine:

```
src/main/java/com/saas/plateform/
├── campaign/              # Module de campagnes
│   ├── application/       # Contrôleurs, DTOs, mappers
│   ├── domain/           # Modèles métier, services, enums
│   └── infrastructure/   # Repositories, persistence
├── contact/              # Module de contacts
│   ├── application/      # Contrôleurs, DTOs, mappers
│   ├── domain/          # Modèles métier, services, enums
│   └── infrastructure/  # Repositories, persistence
├── social/               # Module de réseaux sociaux
│   ├── application/      # Contrôleurs, DTOs, mappers
│   ├── domain/          # Modèles métier, services, enums
│   └── infrastructure/  # Repositories, persistence
└── Shared/              # Éléments partagés
    ├── advice/          # Gestion globale des exceptions
    ├── config/          # Configuration JPA, OpenAPI, etc.
    ├── security/        # Authentification, JWT, autorisation
    └── utils/           # Entités de base, utilitaires

```

## Modules principaux

### 📊 Campaign (Campagnes)
Gestion complète des campagnes marketing:
- Création et modification de campagnes
- Suivi des performances
- Gestion des audiences cibles
- Planification et exécution

### 👥 Contact
Gestion des contacts et listes de diffusion:
- Import et export de contacts
- Segmentation des audiences
- Historique d'interactions
- Gestion des préférences

### 📱 Social
Intégration avec les réseaux sociaux:
- Connexion des comptes sociaux
- Publication et planification
- Suivi des mentions et publications
- Analytics social media

### 🔐 Shared
Fonctionnalités transversales:
- **Security**: Authentification JWT, gestion des rôles, contrôle d'accès
- **Exception Handling**: Gestion centralisée des erreurs
- **Configuration**: Configuration JPA Auditing, OpenAPI/Swagger
- **Utils**: Entité de base (`BaseEntity`) avec audit automatique

## Technologies utilisées

- **Framework**: Spring Boot
- **Langage**: Java
- **Base de données**: JPA/Hibernate
- **Sécurité**: JWT (JSON Web Tokens)
- **Documentation API**: OpenAPI 3.0 (Swagger)
- **Build**: Gradle
- **Gestion de version**: Git

## Installation et configuration

### Prérequis

- Java 11+ (ou version supportée par votre projet)
- Gradle 7+
- Une base de données (MySQL, PostgreSQL, etc.)

### Étapes d'installation

1. **Cloner le repository**
   ```bash
   git clone <repository-url>
   cd plateform
   ```

2. **Configurer les variables d'environnement**
   
   Il existe trois profils de configuration:
   - `application.properties` (par défaut)
   - `application-dev.properties` (développement)
   - `application-prod.properties` (production)

3. **Installer les dépendances**
   ```bash
   ./gradlew build
   ```

## Lancer le projet

### Mode développement
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Mode production
```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

### Construire le JAR
```bash
./gradlew bootJar
```

## Structure du projet

```
/home/jude/Documents/code/plateform/
├── src/
│   ├── main/
│   │   ├── java/              # Code source Java
│   │   └── resources/         # Fichiers de configuration, templates, static
│   └── test/                  # Tests unitaires et d'intégration
├── build/                     # Répertoire de build
├── gradle/                    # Configuration Gradle
├── build.gradle              # Fichier de configuration Gradle
├── settings.gradle           # Configuration des sous-projets
├── gradlew                   # Wrapper Gradle (Linux/Mac)
├── gradlew.bat              # Wrapper Gradle (Windows)
├── HELP.md                  # Documentation supplémentaire
└── README.md                # Ce fichier
```

## API Endpoints

L'API est documentée avec Swagger/OpenAPI. Accédez à la documentation interactive:

```
http://localhost:8080/swagger-ui.html
```

### Modules d'API

- **Campagnes**: `/api/campaigns/*`
- **Contacts**: `/api/contacts/*`
- **Réseaux sociaux**: `/api/social/*`
- **Authentification**: `/api/auth/*`

## Gestion des erreurs

La plateforme utilise un système centralisé de gestion des exceptions via `GlobalExceptionHandler`, qui fournit des réponses d'erreur standardisées et informatives.

## Sécurité

- **Authentification**: JWT avec tokens sécurisés
- **Autorisation**: Gestion des rôles et permissions basée sur les rôles (RBAC)
- **Configuration**: Classe `JpaAuditingConfig` pour l'audit automatique des modifications

## Audit et traçabilité

Grâce à `BaseEntity` et `JpaAuditingConfig`, toutes les entités bénéficient automatiquement de:
- Date de création (`createdDate`)
- Date de dernière modification (`lastModifiedDate`)
- Utilisateur ayant créé l'entité
- Utilisateur ayant modifié l'entité

---

**Documentation supplémentaire**: Consultez [HELP.md](HELP.md) pour plus de détails techniques.
