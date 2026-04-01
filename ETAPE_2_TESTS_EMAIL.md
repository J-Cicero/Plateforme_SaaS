# ÉTAPE 2: Tester Envoi Email

## 🎯 Objectif
Vérifier que l'envoi d'email fonctionne correctement de bout en bout.

---

## 📝 Ce que tu dois exactement faire

### Créer une classe de test

**Package:** `com.saas.plateform.Shared.security.mailling.service`

**Classe à créer:** `EmailServiceTest` (classe de test)

**Attributs de la classe:**
- `emailService` — injecté via @Mock ou @Autowired
- `mailSender` — mock JavaMailSender
- `templateEngine` — mock Thymeleaf (ou réel)

---

## 📋 Les tests à écrire

### Test 1: Vérifier que l'email se charge correctement
**Nom:** `testEmailTemplateLoads()`

**Doit vérifier:**
- Que le fichier `welcome.html` existe
- Que Thymeleaf charge le template sans erreur
- Retour: Pas d'exception

---

### Test 2: Vérifier que les variables se replacent
**Nom:** `testEmailVariablesReplaced()`

**Doit vérifier:**
- Créer un email avec `firstName = "John"`, `email = "john@example.com"`
- Charger template et remplacer variables
- Vérifier que le HTML final contient "John" et "john@example.com"
- Retour: Variables correctement replacées

---

### Test 3: Vérifier que l'email s'envoie avec succès
**Nom:** `testEmailSendSuccess()`

**Doit vérifier:**
- Appeler `emailService.send(emailRequest, "welcome")`
- Mock `mailSender` pour simuler succès
- Vérifier que MailSender.send() a été appelé
- Vérifier que la réponse dit `success = true`

---

### Test 4: Vérifier que les erreurs sont gérées
**Nom:** `testEmailSendFailure()`

**Doit vérifier:**
- Mock mailSender pour lancer une exception
- Appeler `emailService.send(emailRequest, "welcome")`
- Vérifier que la réponse dit `success = false`
- Vérifier que l'erreur est loggée

---

## 🛠️ Configuration de test nécessaire

### Dépendances dans `build.gradle`

**AVANT de commencer, ajoute:**
```gradle
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.mockito:mockito-core:5.2.0'
testImplementation 'org.mockito:mockito-junit-jupiter:5.2.0'
```

---

## 📁 Structure du fichier test

```
com.saas.plateform.Shared.security.mailling.service
└── EmailServiceTest.java

Attributs:
├── @Mock private JavaMailSender mockMailSender;
├── @Mock private TemplateEngine mockTemplateEngine;
├── @InjectMocks private EmailServiceImpl emailService;
└── Private EmailRequest testRequest;

setUp():
└── Initialiser testRequest avec données de test

testEmailTemplateLoads():
testEmailVariablesReplaced():
testEmailSendSuccess():
testEmailSendFailure():
```

---

## 🧪 Comment exécuter les tests

```bash
# Depuis projet root
./gradlew test --tests EmailServiceTest

# Ou depuis VS Code
# Click sur "Test" button au-dessus de chaque test
```

---

## ✅ Critères de succès

- [ ] Les 4 tests existent
- [ ] Les 4 tests passent (green)
- [ ] Couverture > 80% du EmailServiceImpl
- [ ] Aucune exception levée

---

## ⚠️ Important avant de passer à l'étape 3

Une fois les tests écrits et passants:
- Tu sais que l'email charge le template correctement
- Tu sais que les variables se replacent
- Tu sais que l'envoi fonctionne
- Tu as une base stable pour la suite

**Next: ÉTAPE 3 - Connecter Social aux APIs Externes**
