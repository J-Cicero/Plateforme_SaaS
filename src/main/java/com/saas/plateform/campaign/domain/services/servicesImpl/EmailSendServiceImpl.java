package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.Shared.security.mailling.dto.request.EmailRequest;
import com.saas.plateform.Shared.security.mailling.dto.response.EmailResponse;
import com.saas.plateform.Shared.security.mailling.service.EmailService;
import com.saas.plateform.campaign.application.dtos.requests.EmailSendRequest;
import com.saas.plateform.campaign.application.dtos.responses.EmailSendResponse;
import com.saas.plateform.campaign.application.mappers.EmailSendMapper;
import com.saas.plateform.campaign.domain.enums.StatutEnvoi;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignChannel;
import com.saas.plateform.campaign.domain.models.EmailSend;
import com.saas.plateform.campaign.domain.services.EmailSendService;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignChannelRepository;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import com.saas.plateform.campaign.infrastructure.repositories.EmailSendRepository;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.infrastructure.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailSendServiceImpl implements EmailSendService {

    private static final Logger log = LoggerFactory.getLogger(EmailSendServiceImpl.class);

    private final CampaignRepository campaignRepository;
    private final CampaignChannelRepository channelRepository;
    private final ContactRepository contactRepository;
    private final EmailSendRepository emailSendRepository;
    private final EmailService emailService;
    private final EmailSendMapper emailSendMapper;

    @Override
    public EmailSendResponse sendEmail(EmailSendRequest request) {
        Campaign campaign = campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Campaign not found with id: " + request.getCampaignId()));

        CampaignChannel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CampaignChannel not found with id: " + request.getChannelId()));

        if (channel.getTypeCanal() != TypeCanal.EMAIL) {
            throw new IllegalArgumentException("Channel type must be EMAIL for email sending");
        }

        Contact contact = contactRepository.findById(request.getContactId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contact not found with id: " + request.getContactId()));

        EmailSend emailSend = EmailSend.builder()
                .campaign(campaign)
                .channel(channel)
                .contact(contact)
                .statut(StatutEnvoi.EN_ATTENTE)
                .build();

        try {
            EmailRequest emailRequest = EmailRequest.builder()
                    .mailTo(contact.getEmail())
                    .mailSubject(channel.getSujet())
                    .lastName(contact.getLastName())
                    .contact(contact.getFirstName())
                    .build();

            EmailResponse response = emailService.send(emailRequest, channel.getTemplateName());
            log.info("Email sent via shared mailing service. Response: {}", response);

            emailSend.setStatut(StatutEnvoi.ENVOYE);
            emailSend.setSentAt(LocalDateTime.now());
        } catch (Exception ex) {
            log.error("Error while sending email for campaign {} to contact {}",
                    campaign.getId(), contact.getId(), ex);
            emailSend.setStatut(StatutEnvoi.ECHOUE);
            emailSend.setMessageErreur(ex.getMessage());
        }

        EmailSend saved = emailSendRepository.save(emailSend);
        return emailSendMapper.toResponse(saved);
    }
}
