# ÉTAPE 5: Tests JUnit sur Services Critiques

## 🎯 Objectif
Écrire des tests automatisés pour les services importants (Contact, Campaign, Email, Social).

---

## 📝 Ce que tu dois exactement faire

### Créer des classes de test

**Package:** `src/test/java/com/saas/plateform/`

**Structure à créer:**
```
src/test/java/com/saas/plateform/
├── contact/
│   └── domain/services/
│       └── ContactServiceTest.java
├── campaign/
│   └── domain/services/
│       ├── CampaignServiceTest.java
│       └── CampaignExecutionServiceTest.java
├── Shared/
│   └── security/mailling/
│       └── service/
│           └── EmailServiceTest.java
└── social/
    └── domain/services/
        └── PublicationSocialeServiceTest.java
```

---

## 🔨 Pour CHAQUE classe de test

### Structure générale

**Annotations:**
```
@ExtendWith(MockitoExtension.class)
class [ServiceName]Test
```

**Attributs:**
- `@Mock` — pour les repositories (pas de DB durant test)
- `@Mock` — pour les autres services appelés
- `@InjectMocks` — l'instance du service à tester

**Méthodes:**
```
@BeforeEach
void setUp() { }
    └─ Initialiser données de test

@Test
void test[Case]() { }
    └─ Un test par cas d'usage
```

---

## 📋 ContactServiceTest

**Ce qu'il faut tester:**

### Test 1: `testCreateContact_Success()`
- Vérifier qu'on peut créer un contact
- Mock: contactRepository.save()
- Vérifier: Contact bien créé avec email unique

### Test 2: `testCreateContact_DuplicateEmail()`
- Vérifier qu'on ne peut pas créer 2 contacts avec même email
- Mock: contactRepository.existsByEmail() = true
- Vérifier: Exception levée

### Test 3: `testGetContactByTrackingId_Success()`
- Vérifier récupération d'un contact
- Mock: repository retourne un contact
- Vérifier: ContactResponse contient bon email/nom

### Test 4: `testGetContactByTrackingId_NotFound()`
- Vérifier qu'erreur si contact inexistant
- Mock: repository retourne empty
- Vérifier: ResourceNotFoundException levée

### Test 5: `testSegmentContact()`
- Vérifier qu'on peut assigner un contact à un segment
- Mock: contactSegmentRepository.save()
- Vérifier: Lien créé

---

## 📋 CampaignServiceTest

**Ce qu'il faut tester:**

### Test 1: `testCreateCampaign_Success()`
- Vérifier création d'une campagne
- Mock: campaignRepository.save()
- Vérifier: Campaign créée avec status, type, etc

### Test 2: `testCreateCampaign_InvalidStatus()`
- Vérifier qu'on ne peut pas créer avec statut invalide
- Mock: validation fails
- Vérifier: Exception levée

### Test 3: `testGetCampaignById_Success()`
- Vérifier récupération campaign
- Mock: repository retourne campaign
- Vérifier: Bonne campaign retournée

### Test 4: `testGetCampaignsByStatus()`
- Vérifier filtrage par status
- Mock: repository retourne liste
- Vérifier: Bon nombre de campaigns, bon status

---

## 📋 CampaignExecutionServiceTest

**Ce qu'il faut tester:**

### Test 1: `testExecuteMailing_Success()`
- Vérifier que mailing s'exécute correctement
- Mock: contactRepository, emailService, resultatRepository
- Vérifier: ResultatCampagne créé avec sentCount > 0

### Test 2: `testExecuteMailing_PartialFailure()`
- Vérifier que s'il y a erreurs elles sont loggées
- Mock: emailService lève exception pour un contact
- Vérifier: failedCount incremeneted, autres contacts traités

### Test 3: `testTrackEmailOpen()`
- Vérifier tracking des opens
- Mock: trackingEventRepository.save()
- Vérifier: EmailTrackingEvent créé, openCount incremeneted

### Test 4: `testTrackEmailClick()`
- Vérifier tracking des clics
- Mock: trackingEventRepository.save()
- Vérifier: EmailTrackingEvent créé, clickCount incremeneted

---

## 📋 EmailServiceTest

**Ce qu'il faut tester:**

### Test 1: `testSendEmail_WithTemplate_Success()`
- Vérifier que l'email s'envoie avec template Thymeleaf
- Mock: mailSender.send()
- Vérifier: EmailResponse.success = true

### Test 2: `testSendEmail_TemplateVariablesReplaced()`
- Vérifier que {{variables}} sont bien replacées
- Mock: templateEngine
- Vérifier: HTML final contient vraies valeurs

### Test 3: `testSendEmail_InvalidEmail()`
- Vérifier que format email est validé
- Input: email invalide
- Vérifier: Exception levée ou success = false

### Test 4: `testSendEmail_MaxRetriesExceeded()`
- Vérifier qu'après N tentatives on abandonne
- Mock: mailSender lève exception
- Vérifier: Après 3 tentatives, on arrête

---

## 📋 PublicationSocialeServiceTest

**Ce qu'il faut tester:**

### Test 1: `testPublishToFacebook_Success()`
- Vérifier qu'on peut publier sur Facebook
- Mock: facebookClient.publishPost()
- Vérifier: PublicationSociale.status = "published"

### Test 2: `testPublishToFacebook_InvalidToken()`
- Vérifier erreur si token invalide
- Mock: facebookClient lève exception
- Vérifier: Publication marée "failed"

### Test 3: `testPublishToLinkedIn_Success()`
- Idem Facebook mais pour LinkedIn

### Test 4: `testPublishToMultiplePlatforms()`
- Vérifier qu'on peut publier sur plusieurs réseaux en même temps
- Mock: plusieurs clients
- Vérifier: Tous les posts créés

---

## 🧪 Anatomie d'un test minimal

**Exemple structure:**
```
@ExtendWith(MockitoExtension.class)
class ExampleServiceTest {
    
    @Mock
    private ExampleRepository mockRepository;
    
    @InjectMocks
    private ExampleService exampleService;
    
    @BeforeEach
    void setUp() {
        // Initialiser données de test
    }
    
    @Test
    void testCaseSuccessful() {
        // ARRANGE - préparer données
        
        // ACT - faire l'appel
        
        // ASSERT - vérifier résultats
    }
}
```

---

## 📦 Dépendances nécessaires

**Ajouter dans `build.gradle`:**

```gradle
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.mockito:mockito-core:5.2.0'
testImplementation 'org.mockito:mockito-junit-jupiter:5.2.0'
testImplementation 'org.junit.jupiter:junit-jupiter:5.9.2'
```

---

## 🚀 Checker avant de commencer

**Vérifie que tu as:**
- [ ] `@Test` imports depuis `org.junit.jupiter.api.Test`
- [ ] `@Mock` imports depuis `org.mockito.Mock`
- [ ] `@InjectMocks` imports depuis `org.mockito.InjectMocks`
- [ ] `@ExtendWith` imports depuis `org.junit.jupiter.api.extension.ExtendWith`

---

## ✅ Comment lancer les tests

```bash
# Tous les tests
./gradlew test

# Tests d'une seule classe
./gradlew test --tests ContactServiceTest

# Tests d'une seule méthode
./gradlew test --tests ContactServiceTest.testCreateContact_Success

# Avec coverage report
./gradlew test jacoco
```

---

## 📊 Objectif coverage

**Targets:**
- ContactService: > 85%
- CampaignService: > 85%
- CampaignExecutionService: > 80%
- EmailService: > 90%
- PublicationSocialeService: > 80%

**Overall: > 80% coverage**

---

## ⚠️ Important avant de passer à l'étape 6

**Vérifier que:**
- [ ] Tous les tests passent (vert)
- [ ] Pas de "Skip" ou "Ignore" tests
- [ ] Coverage report généré
- [ ] CI/CD pipeline picks up les tests (later)

**Next: ÉTAPE 6 - Docker-Compose Basique**
