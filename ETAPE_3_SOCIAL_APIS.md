# ÉTAPE 3: Connecter Social aux APIs Externes

## 🎯 Objectif
Connecter le module Social réel à Facebook, Instagram, LinkedIn pour publier et récupérer données.

---

## 📝 Ce que tu dois exactement faire

### Étendre le module Social

**Package existant:** `com.saas.plateform.social`

**Packages à créer:**
```
com.saas.plateform.social
├── domain/
│   ├── models/ (déjà existe)
│   ├── services/ (déjà existe)
│   └── enums/ (déjà existe)
├── application/
│   ├── controllers/ (déjà existe)
│   └── dtos/ (déjà existe)
├── infrastructure/
│   ├── repositories/ (déjà existe)
│   ├── clients/ ← À créer (pour API calls)
│   └── integrations/ ← À créer (pour chaque réseau)
```

---

## 🔗 Créer les intégrations réseaux sociaux

### Créer 4 classes dans `infrastructure/integrations/`

#### **1. FacebookIntegrationClient**

**Attributs:**
- `facebookApiUrl` — URL base de l'API Facebook (https://graph.facebook.com/v18.0)
- `restTemplate` — pour appels HTTP
- `accessToken` — stocké depuis CompteSocial.credentials

**Méthodes à implémenter:**
- `publishPost(pageId, message, imageUrl)` — publier un post
- `getPagePosts(pageId)` — récupérer posts d'une page
- `getPageInsights(pageId)` — récupérer analytics (likes, reach, etc)
- `validateToken(accessToken)` — vérifier que le token est valide

**Dépendances:**
- RestTemplate (déjà disponible)
- Rien d'autre de nouveau

---

#### **2. InstagramIntegrationClient**

**Attributs:**
- `instagramApiUrl` — URL base (via Facebook Graph API v18.0)
- `restTemplate`
- `accessToken`

**Méthodes:**
- `publishPost(instagramBusinessAccountId, caption, imageUrl)`
- `getRecentPosts(instagramBusinessAccountId)`
- `getFollowerCount(instagramBusinessAccountId)`

**Note:** Instagram utilise Facebook Graph API en backend

---

#### **3. LinkedInIntegrationClient**

**Attributs:**
- `linkedinApiUrl` — https://api.linkedin.com/v2
- `restTemplate`
- `accessToken`
- `organizationId` — ID de l'organisation LinkedIn

**Méthodes:**
- `shareOrganizationPost(text, imageUrl)`
- `getOrganizationPosts()`
- `getFollowerCount()`

---

#### **4. XTwitterIntegrationClient**

**Attributs:**
- `twitterApiUrl` — https://api.twitter.com/2
- `restTemplate`
- `bearerToken` — Different de accessToken
- `userId` — Twitter user ID

**Méthodes:**
- `postTweet(text, imageUrl)`
- `getTweets()`
- `getFollowerCount()`

---

## 🔐 Setup OAuth nécessaire

### Modifier `CompteSocial` entity

**Attributs à ajouter:**
- `oauthCode` — code reçu du provider (temporaire)
- `accessToken` — token actuel (stocker de manière sécurisée!)
- `refreshToken` — pour renouveler le token
- `tokenExpiry` — quand le token expire
- `accessGrantedAt` — quand accès a été accordé

---

### Créer contrôleur oauth

**Package:** `com.saas.plateform.social.application.controllers`

**Nouvelle classe:** `OAuthCallbackController`

**Endpoints:**
- `GET /oauth/facebook/callback` — reçoit code de Facebook
- `GET /oauth/instagram/callback` — reçoit code d'Instagram
- `GET /oauth/linkedin/callback` — reçoit code de LinkedIn
- `GET /oauth/twitter/callback` — reçoit code de Twitter

**Chaque endpoint doit:**
1. Récupérer le `code` depuis queryParam
2. Échanger le code pour un `accessToken` (appel API)
3. Stocker le token dans CompteSocial
4. Rediriger vers un success page

---

## 🔧 Modifier PublicationSocialeService

**Méthodes à ajouter:**

- `publishToFacebook(PublicationSociale, CompteSocial)` — publier via FacebookIntegrationClient
- `publishToInstagram(PublicationSociale, CompteSocial)` — publier via InstagramIntegrationClient
- `publishToLinkedIn(PublicationSociale, CompteSocial)` — publier via LinkedInIntegrationClient
- `publishToTwitter(PublicationSociale, CompteSocial)` — publier via XTwitterIntegrationClient

**Logique générale:**
- Vérifier que CompteSocial a un token valide
- Vérifier que token n'a pas expiré
- Appeler le client d'intégration approprié
- Mettre à jour PublicationSociale avec le status ("published", "failed", etc)

---

## 📦 Dépendances à ajouter

**Ajouter dans `build.gradle`:**

```gradle
// HTTP Client (RestTemplate déjà présent)
// Rien de nouveau requis - RestTemplate suffit

// Optionnel - pour meilleur support JSON
implementation 'com.fasterxml.jackson.core:jackson-databind'

// Optionnel - pour logging
implementation 'org.slf4j:slf4j-api'
```

---

## 🚀 Étapes précises pour commencer

### Semaine 1:
1. Créer les 4 classes IntegrationClient
2. Implémenter les méthodes de base (publish, get posts)

### Semaine 2:
3. Créer OAuthCallbackController
4. Implémenter OAuth flows
5. Modifier PublicationSocialeService pour utiliser les clients

### Testing:
6. Tester avec des comptes de test Facebook/LinkedIn/Twitter
7. Vérifier que posts s'affichent réellement sur les réseaux

---

## ⚠️ Important avant de passer à l'étape 4

**Configuration à préparer (avant d'écrire le code):**

Pour chaque réseau social, tu besoin de:
1. **App Creator ID** — créer une app développeur
2. **Client ID** — fourni par la plateforme
3. **Client Secret** — à stocker en sécurité
4. **Redirect URI** — exemple: `http://localhost:8080/oauth/facebook/callback`
5. **Permissions** — configurer dans la console développeur

**Ressources:**
- Facebook: https://developers.facebook.com
- LinkedIn: https://www.linkedin.com/developers
- Twitter: https://developer.twitter.com
- Instagram: Via Facebook (même plateforme)

---

## 📝 Résumé

**Créer:**
- 4 classes IntegrationClient (Facebook, Instagram, LinkedIn, Twitter)
- 1 classe OAuthCallbackController
- Modifier CompteSocial (ajouter tokens)
- Modifier PublicationSocialeService (ajouter publish methods)

**Ne pas coder en dur:**
- Mettre Client IDs/Secrets en `application.properties` ou variables d'env

**Next: ÉTAPE 4 - Alimenter ResultatCampagne Après Envoi**
