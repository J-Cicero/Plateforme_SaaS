package com.saas.plateform.mailling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour vérifier que les templates Thymeleaf pour les emails fonctionnent correctement.
 * Ces tests vérifient que les template files existent et contiennent les variables Thymeleaf attendues.
 */
@DisplayName("Tests des Templates Thymeleaf pour les Emails")
class TemplateThymeleafTest {

    /**
     * Vérifie que les trois fichiers HTML existent à la bonne location.
     */
    @Test
    @DisplayName("Tous les fichiers de templates doivent exister")
    void testTemplatesExist() {
        String[] templates = {"emails/welcome", "emails/campaign-notification", "emails/password-reset"};
        
        for (String template : templates) {
            Resource resource = new ClassPathResource("templates/" + template + ".html");
            assertTrue(resource.exists(), "Le template " + template + " n'existe pas");
        }
    }

    /**
     * Teste que le template welcome.html contient les variables Thymeleaf attendues.
     */
    @Test
    @DisplayName("Template welcome.html contient les variables requises")
    void testWelcomeTemplateVariables() throws IOException {
        Resource resource = new ClassPathResource("templates/emails/welcome.html");
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        assertTrue(content.contains("${firstName}"), "La variable firstName manque dans welcome.html");
        assertTrue(content.contains("${email}"), "La variable email manque dans welcome.html");
        assertTrue(content.contains("${platformUrl}"), "La variable platformUrl manque dans welcome.html");
        assertTrue(content.contains("${registrationDate}"), "La variable registrationDate manque dans welcome.html");
        assertTrue(content.contains("Bienvenue"), "Le contenu attendu manque dans welcome.html");
    }

    /**
     * Teste que le template campaign-notification.html contient les variables Thymeleaf attendues.
     */
    @Test
    @DisplayName("Template campaign-notification.html contient les variables requises")
    void testCampaignNotificationTemplateVariables() throws IOException {
        Resource resource = new ClassPathResource("templates/emails/campaign-notification.html");
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        assertTrue(content.contains("${campaignName}"), "La variable campaignName manque dans campaign-notification.html");
        assertTrue(content.contains("${campaignDescription}"), "La variable campaignDescription manque dans campaign-notification.html");
        assertTrue(content.contains("${campaignStartDate}"), "La variable campaignStartDate manque dans campaign-notification.html");
        assertTrue(content.contains("${recipientCount}"), "La variable recipientCount manque dans campaign-notification.html");
        assertTrue(content.contains("Campagne"), "Le contenu attendu manque dans campaign-notification.html");
    }

    /**
     * Teste que le template password-reset.html contient les variables Thymeleaf attendues.
     */
    @Test
    @DisplayName("Template password-reset.html contient les variables requises")
    void testPasswordResetTemplateVariables() throws IOException {
        Resource resource = new ClassPathResource("templates/emails/password-reset.html");
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        assertTrue(content.contains("${resetLink}"), "La variable resetLink manque dans password-reset.html");
        assertTrue(content.contains("${expiryMinutes}"), "La variable expiryMinutes manque dans password-reset.html");
        assertTrue(content.contains("${supportEmail}"), "La variable supportEmail manque dans password-reset.html");
        assertTrue(content.contains("Réinitialisation"), "Le contenu attendu manque dans password-reset.html");
    }

    /**
     * Teste que tous les templates sont du HTML valide.
     */
    @Test
    @DisplayName("Tous les templates contiennent du HTML valide")
    void testTemplatesAreValidHtml() throws IOException {
        String[] templates = {"emails/welcome", "emails/campaign-notification", "emails/password-reset"};
        
        for (String template : templates) {
            Resource resource = new ClassPathResource("templates/" + template + ".html");
            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            assertTrue(content.contains("<html"), template + " ne contient pas la balise HTML");
            assertTrue(content.contains("</html>"), template + " ne contient pas la balise de fermeture HTML");
            assertTrue(content.contains("<body"), template + " ne contient pas la balise BODY");
            assertTrue(content.contains("</body>"), template + " ne contient pas la balise de fermeture BODY");
            assertTrue(content.contains("xmlns:th="), template + " n'a pas l'attribut Thymeleaf");
        }
    }
}

