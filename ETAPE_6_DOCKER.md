# ÉTAPE 6: Docker-Compose Basique

## 🎯 Objectif
Créer une façon simple de lancer toute l'application localement avec tous les services (BD, Mail, etc.).

---

## 📝 Ce que tu dois exactement faire

### Créer 2 fichiers à la racine du projet

```
/plateform/
├── Dockerfile
├── docker-compose.yml
└── ... (autres fichiers)
```

---

## 🐳 Fichier 1: Dockerfile

**Location:** À la racine `/plateform/Dockerfile`

**Pas de dépendances spéciales**
- Docker doit être installé sur ta machine

---

## 📋 Structure du Dockerfile

### Multistage build - 2 stages

**Stage 1: Build**
- Utiliser JDK (Java Development Kit) 21
- Copier le projet
- Compiler avec Gradle
- Résultat: fichier JAR

**Stage 2: Runtime**
- Utiliser JRE (Java Runtime Environment) 21
- Copier le JAR depuis stage 1
- Exposer port 8080
- Lancer l'application

**Attributs à définir:**
- `FROM` — image de base pour chaque stage
- `WORKDIR` — dossier de travail dans le container
- `COPY` — copier fichiers
- `RUN` — exécuter commandes (gradle build)
- `EXPOSE` — exposer le port
- `ENTRYPOINT` — commande de lancement

---

## 📋 Structure du docker-compose.yml

### Services à configurer

**Service 1: app (ton Spring Boot)**
- Build depuis Dockerfile
- Port: 8080:8080
- Variables d'environment pour DB, email, etc
- Depends on: postgres, mailhog

**Service 2: postgres (Base de données)**
- Image: postgres:15
- Port: 5432:5432
- Variables: POSTGRES_DB, POSTGRES_PASSWORD
- Volume: pour persister les données

**Service 3: mailhog (Fake mail server)**
- Image: mailhog/mailhog
- Port: 1025:1025 (SMTP)
- Port: 8025:8025 (Web UI pour voir les mails)

---

## 🔧 Variables d'environment

### Pour le service `app`, passer ces env vars:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/plateform
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_MAIL_HOST=mailhog
SPRING_MAIL_PORT=1025
SPRING_MAIL_USERNAME=
SPRING_MAIL_PASSWORD=
SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=false
SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=false
```

---

## 📁 Volumes à créer

**Pour postgres:**
- Volume nommé `postgres_data` pour ne pas perdre DB quand container redémarre

---

## 🌐 Networks

**Créer un network nommé `plateform-network`** pour que les containers parlent entre eux:
- `app` peut appeler `postgres` via hostname `postgres`
- `app` peut appeler `mailhog` via hostname `mailhog`

---

## 📝 application.properties à adapter

**Créer un profil local:** `src/main/resources/application-docker.properties`

```properties
spring.profiles.active=docker

spring.datasource.url=jdbc:postgresql://postgres:5432/plateform
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

spring.mail.host=mailhog
spring.mail.port=1025
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth=false
```

---

## 🏗️ Build Gradle à adapter

### Ajouter task pour générer JAR

**Dans `build.gradle`, vérifier qu'existe:**
```gradle
tasks.named('bootJar') {
    mainClass = 'com.saas.plateform.PlateformApplication'
}
```

Cela assure que `./gradlew build` génère le bon JAR.

---

## 🔐 Sécurité - fichiers à pas commit

**Ajouter à `.gitignore`:**
```
.env
.env.local
```

Ces fichiers ne doivent pas être en git (pour secrets).

---

## ✅ Commandes à lancer

### Pour démarrer complètement:

```bash
# Du dossier racine /plateform

# 1. Build l'image Docker
docker build -t plateform-api:latest .

# 2. Lancer tous les services
docker-compose up -d

# 3. Vérifier les logs
docker-compose logs -f app

# 4. Vérifier que tout est running
docker-compose ps

# 5. Accéder à l'app
# - API: http://localhost:8080
# - Swagger: http://localhost:8080/swagger-ui.html
# - Mailhog (voir les mails): http://localhost:8025
```

### Pour arrêter:

```bash
docker-compose down

# ou pour arrêter et supprimer volumes
docker-compose down -v
```

---

## 🧪 Tester localement avant Docker

### Avant de créer Dockerfile, tester sa build:

```bash
# Du dossier racine
./gradlew build

# Vérifier que le JAR est généré
ls build/libs/

# Résultat attendu: plateform-0.0.1-SNAPSHOT.jar ou similaire
```

---

## 📋 Résumé des fichiers à créer

### À racine:
- [ ] `Dockerfile` — Comment construire l'image
- [ ] `docker-compose.yml` — Comment lancer tous les services

### À src/main/resources:
- [ ] `application-docker.properties` — Config pour Docker

### Modifier:
- [ ] `.gitignore` — Ajouter .env

---

## 🔍 Vérifier avant de commencer

**Tu as besoin:**
- [ ] Docker installé (`docker --version`)
- [ ] Docker Compose installé (`docker-compose --version`)
- [ ] Au moins 4GB RAM disponible sur ta machine

---

## ⚠️ Important avant de terminer

**Tester end-to-end:**
1. `docker-compose up`
2. Attendre 30-60 secondes que tout démarre
3. Vérifier `http://localhost:8080/swagger-ui.html` accessible
4. Créer un contact via API
5. Vérifier que le email s'affiche dans MailHog
6. Vérifier que la DB contient le contact
7. Tout OK? ✅

---

## 🚀 Une fois Docker fonctionnel

Prochaines étapes (non dans ce plan MVP):
- Ajouter Redis container
- Ajouter MongoDB container
- Ajouter RabbitMQ container
- Ajouter Elasticsearch container

Pour MVP: postgres + mailhog suffit.

---

**C'est les 6 étapes! Une fois complétées, tu as un MVP solide.** 🎉
