# ÉTAPE 4: Alimenter ResultatCampagne Après Envoi

## 🎯 Objectif
Chaque fois qu'on envoie une campagne (emails, messages réseaux sociaux), enregistrer les résultats pour suivre la performance.

---

## 📝 Ce que tu dois exactement faire

### Comprendre ResultatCampagne

**Entity existante:** `com.saas.plateform.campaign.domain.models.ResultatCampagne`

**Attributs existants (vérifie):**
- `id` — PK
- `campaign` — FK vers Campaign
- `emailSent` — nombre d'emails envoyés
- `emailFailed` — nombre d'emails échoués
- `openCount` — nombre d'ouvertures (tracking)
- `clickCount` — nombre de clics (tracking)
- `createdAt` — timestamp création

---

## 🔧 Modifier CampaignService

### Ajouter une méthode: `executeCampaign(Long campaignId)`

**Logique générale:**
```
1. Récupérer la Campaign
2. Récupérer les Contacts ciblés (via CampaignAudience)
3. Pour chaque Contact:
   - Récupérer son email
   - Envoyer l'email (appel EmailService.send())
   - Incrémenter counter "emails envoyés" ou "échecs"
4. Une fois tous les emails envoyés, créer ResultatCampagne
5. Sauvegarder les résultats
```

---

## 📋 Entités impliquées

### Vérifier que tu as

**1. Campaign entity:**
- `id`, `name`, `type`, `status`, `createdBy`

**2. CampaignAudience entity:**
- Lie Campaign aux Contacts ciblés
- Contient liste des Contacts qui reçoivent la campagne

**3. ResultatCampagne entity:**
- Stores les résultats d'exécution

**4. EmailTrackingEvent entity:**
- Tracking des opens/clicks (via webhook SendGrid - plus tard)

---

## 🔨 Créer une nouvelle classe

**Package:** `com.saas.plateform.campaign.domain.services`

**Nouvelle interface:** `CampaignExecutionService`

**Nouvelles méthodes:**
- `executeCampaignEmails(Campaign campaign)` — envoyer tous les emails
- `executeCampaignSocial(Campaign campaign)` — publier sur réseaux sociaux
- `createCampaignResult(Campaign, sentCount, failedCount)` — créer ResultatCampagne

---

## 📋 Étapes précises pour implémenter

### Étape 1: Créer CampaignExecutionService (interface)

**Signature des méthodes:**
```
- ResultatCampagne executeMailing(Campaign campaign)
- void trackEmailOpen(Long emailSendId)
- void trackEmailClick(Long emailSendId)
- void updateCampaignMetrics(Campaign campaign)
```

---

### Étape 2: Créer CampaignExecutionServiceImpl

**Injecter ces dépendances:**
- `CampaignRepository` — récupérer campaign
- `CampaignAudienceRepository` — récupérer contacts ciblés
- `EmailService` — envoyer emails
- `ResultatCampagneRepository` — sauvegarder résultats
- `EmailSendRepository` — tracer chaque envoi
- `EmailTrackingEventRepository` — sauvegarder les opens/clicks

**Implémenter `executeMailing(Campaign)`:**
1. Récupérer tous les CampaignAudience pour cette Campaign
2. Pour chaque audience (contact):
   - Créer un EmailSend entity
   - Appeler `emailService.send(contact.email, campaign.template)`
   - Si succès: incrémenter sentCount, sauvegarder EmailSend
   - Si échec: incrémenter failedCount, logger erreur
3. Créer ResultatCampagne avec les counts
4. Retourner les résultats

---

### Étape 3: Appeler depuis CampaignController

**Modifier le controller existant:**

Ajouter un endpoint (si pas déjà):
```
POST /campaigns/{id}/execute
```

**Logique:**
1. Récupérer la Campaign par ID
2. Appeler `campaignExecutionService.executeMailing(campaign)`
3. Retourner les résultats en JSON

---

## 🔄 Tracking des opens/clicks

### Créer WebhookController pour emails

**Package:** `com.saas.plateform.Shared.security.mailling.controller`

**Nouvelle classe:** `EmailTrackingWebhookController`

**Endpoint:**
```
POST /webhook/email/events
```

**Reçoit from SendGrid (plus tard):**
- `event` — "open", "click"
- `email` — destinataire
- `timestamp` — quand ça s'est passé
- `messageId` — ID du message

**Logique:**
1. Récupérer l'event
2. Trouver l'EmailSend correspondant
3. Trouver la Campaign correspondante
4. Créer un EmailTrackingEvent
5. Incrémenter openCount ou clickCount dans ResultatCampagne

---

## 📦 Dépendances nécessaires

**Vérifier dans `build.gradle`:**
```gradle
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-web'
```

Ces dépendances existent déjà ✅

---

## 🗂️ Repositories à créer/modifier

**Repositories existants (vérifie):**
- `CampaignRepository` ✓
- `ResultatCampagneRepository` ✓
- `EmailTrackingEventRepository` ✓

**À créer (si pas existe):**
- `EmailSendRepository` — si pas encore créé
- `CampaignAudienceRepository` — lien Campaign-Contacts

**Methods à ajouter:**
- `findByCampaignId(Long)` — pour ResultatCampagne
- `findByEmailAndMessageId(String)` — pour webhook tracking

---

## 🔐 Sécurité du webhook

**Important:**
- Le webhook SendGrid enverra les events via HTTP POST
- Vérifier que le webhook est sécurisé (signature SendGrid, HTTPS)
- Ne pas accepter n'importe quel event

**À implémenter:**
- Vérifier la signature du webhook (clé secrète SendGrid)
- Valider que le messageId existe dans ta BD
- Logger tous les events pour debug

---

## ✅ Critères de succès

Une fois cette étape complétée:
- [ ] CampaignExecutionService créé
- [ ] CampaignExecutionServiceImpl implémenté
- [ ] Endpoint POST /campaigns/{id}/execute fonctionne
- [ ] ResultatCampagne est créé et rempli après envoi
- [ ] EmailTrackingWebhookController existe
- [ ] Opens/clicks sont trackés

---

## ⚠️ Important avant de passer à l'étape 5

**Test this end-to-end:**
1. Crée une Campaign
2. Ajoute des Contacts via CampaignAudience
3. Appelle le endpoint execute
4. Vérifie que ResultatCampagne est créé
5. Vérifie que les counts sont corrects

**Next: ÉTAPE 5 - Tests JUnit sur Services Critiques**
