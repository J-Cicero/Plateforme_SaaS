package com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.service.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.dto.request.EmailRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.dto.response.EmailResponse;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.mapper.EmailMapper;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.repository.EmailRepository;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.repository.EmailSendRepository;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.service.EmailService;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailRepository emailRepository;

    private final EmailSendRepository repository;

    private final SpringTemplateEngine templateEngine;

    private final EmailMapper emailMapper;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(
            EmailRepository emailRepository,
            EmailSendRepository repository,
            SpringTemplateEngine templateEngine,
            EmailMapper emailMapper,
            JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.repository = repository;
        this.templateEngine = templateEngine;
        this.emailMapper = emailMapper;
        this.mailSender = mailSender;
    }

    @Override
    public EmailResponse send(EmailRequest request, String template) {
        return Optional.of(request).stream()
                .peek(req -> sendMimeMessage(req, template))
                .map(emailMapper::toEntity)
                .peek(repository::save)
                .map(emailMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Erreur lors de la sauvegarde"));
    }

    private boolean templateExists(String templateName) {
        Resource resource = new ClassPathResource("templates/" + templateName + ".html");
        return resource.exists();
    }

    public void sendMimeMessage(EmailRequest request, String template) {
        logger.info(
                "Début de l'envoi d'email vers: {} avec le template: {}", request.getMailTo(), template);

        if (!templateExists(template)) {
            logger.error("Le template {} n'existe pas", template);
            throw new IllegalArgumentException("Le template " + template + " n'existe pas.");
        }

        try {
            logger.debug("Création du contexte Thymeleaf pour le template {}", template);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();

            model.put("nom", request.getNom());
            model.put("entreprise", request.getEntreprise());
            model.put("plan", request.getPlan());
            model.put("username", request.getUsername());
            model.put("password", request.getPassword());
            model.put("startDate", request.getStartDate());
            model.put("lien", request.getLien());
            model.put("lienCompte", request.getLien());
            model.put("endDate", request.getEndDate());
            model.put("montant", request.getMontant());
            model.put("mailTo", request.getMailTo());
            model.put("contact", request.getContact());
            model.put("contactEmail", request.getContact());

            context.setVariables(model);

            logger.debug("Création du message MIME");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                            StandardCharsets.UTF_8.name());

            String html = templateEngine.process(template, context);

            helper.setText(html, true);
            helper.setTo(request.getMailTo());
            helper.setSubject(request.getMailSubject());

            try {
                helper.setFrom("contact@nitchcorp.tech", "Paiya");

                logger.info(
                        "Tentative d'envoi d'email vers {} avec le sujet: {}",
                        request.getMailTo(),
                        request.getMailSubject());

                long startTime = System.currentTimeMillis();
                mailSender.send(message);
                long endTime = System.currentTimeMillis();

                logger.info(
                        "✅ Email envoyé avec succès vers {} en {}ms",
                        request.getMailTo(),
                        (endTime - startTime));

            } catch (java.io.UnsupportedEncodingException e) {
                logger.error(
                        "❌ Erreur d'encodage lors de l'envoi de l'email vers {}: {}",
                        request.getMailTo(),
                        e.getMessage(),
                        e);
                throw new jakarta.mail.MessagingException("Erreur d'encodage de l'email", e);
            }

        } catch (jakarta.mail.MessagingException e) {
            logger.error(
                    "❌ Erreur MessagingException lors de l'envoi de l'email vers {}", request.getMailTo(), e);
            logger.error(
                    "Détails de l'erreur: Type={}, Message={}", e.getClass().getName(), e.getMessage());

            Throwable cause = e.getCause();
            int level = 1;
            while (cause != null && level <= 3) {
                logger.error(
                        "  Cause niveau {}: {} - {}", level, cause.getClass().getName(), cause.getMessage());
                cause = cause.getCause();
                level++;
            }

            throw new RuntimeException("Impossible d'envoyer l'email: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error(
                    "❌ Erreur inattendue lors de l'envoi de l'email vers {}", request.getMailTo(), e);
            throw new RuntimeException("Erreur inattendue lors de l'envoi de l'email", e);
        }
    }
}
