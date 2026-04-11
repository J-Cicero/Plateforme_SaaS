package com.saas.plateform.Shared.security.mailling.config;

import com.saas.plateform.Shared.security.mailling.entity.EmailConfiguration;
import com.saas.plateform.Shared.security.mailling.repository.EmailConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MailConfigInitializer {
    private final EmailConfigurationRepository emailConfigurationRepository;
    private static final Logger logger = LoggerFactory.getLogger(MailConfigInitializer.class);

    @Value("${spring.mail.host:mail.nitchcorp.tech}")
    private String mailHost;

    @Value("${spring.mail.port:1025}")
    private Integer mailPort;

    @Value("${spring.mail.username:contact@nitchcorp.tech}")
    private String mailUsername;

    @Value("${spring.mail.password:Titanmail22@}")
    private String mailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean mailAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:true}")
    private boolean mailStartTls;


    public MailConfigInitializer(EmailConfigurationRepository emailConfigurationRepository) {
        this.emailConfigurationRepository = emailConfigurationRepository;
    }

    @Bean
    public CommandLineRunner initEmailConfig() {
        return args -> {
            try {
                if (emailConfigurationRepository.count() == 0) {
                    logger.info(
                            "Initialisation de la configuration email (depuis propriétés Spring ou défauts NitchCorp)...");
                    EmailConfiguration emailConfig = new EmailConfiguration();
                    emailConfig.setTrackingId(UUID.randomUUID());
                    emailConfig.setHost(mailHost);
                    emailConfig.setPort(mailPort);
                    emailConfig.setUsername(mailUsername);
                    emailConfig.setPassword(mailPassword);
                    emailConfig.setSmtpAuth(mailAuth);
                    emailConfig.setStarttlsEnable(mailStartTls);

                    emailConfigurationRepository.save(emailConfig);
                    logger.info(
                            "Configuration email initialisée avec succès pour: {}", emailConfig.getUsername());
                } else {

                    EmailConfiguration existingConfig =
                            emailConfigurationRepository.findAll().stream().findFirst().orElse(null);
                    if (existingConfig != null) {
                        existingConfig.setHost(mailHost);
                        existingConfig.setPort(mailPort);
                        existingConfig.setUsername(mailUsername);
                        existingConfig.setPassword(mailPassword);
                        existingConfig.setSmtpAuth(mailAuth);
                        existingConfig.setStarttlsEnable(mailStartTls);
                        emailConfigurationRepository.save(existingConfig);
                        logger.info(
                                "Configuration email synchronisée avec les propriétés Spring pour: {}",
                                existingConfig.getUsername());
                    }
                }
            } catch (Exception e) {
                logger.error("Erreur lors de l'initialisation de la configuration email", e);
                throw e;
            }
        };
    }
}
