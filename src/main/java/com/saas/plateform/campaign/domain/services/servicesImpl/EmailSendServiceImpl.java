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
import com.saas.plateform.campaign.domain.services.ResultatCampagneService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final ResultatCampagneService resultatCampagneService;

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

            // Incrémenter les envoyes dans ResultatCampagne
            resultatCampagneService.incrementerEnvoyes(campaign.getId(), TypeCanal.EMAIL);

        } catch (Exception ex) {
            log.error("Error while sending email for campaign {} to contact {}",
                    campaign.getId(), contact.getId(), ex);
            emailSend.setStatut(StatutEnvoi.ECHEC);
            emailSend.setMessageErreur(ex.getMessage());

            // Incrémenter les echecs dans ResultatCampagne
            resultatCampagneService.incrementerEchecs(campaign.getId(), TypeCanal.EMAIL);
        }

        EmailSend saved = emailSendRepository.save(emailSend);
        return emailSendMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailSendResponse> getEmailSendById(Long id) {
        return emailSendRepository.findById(id)
                .map(emailSendMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailSendResponse> getEmailSendsByCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Campaign not found with id: " + campaignId));
        return emailSendRepository.findByCampaign(campaign).stream()
                .map(emailSendMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailSendResponse> getEmailSendsByStatut(StatutEnvoi statut) {
        return emailSendRepository.findByStatut(statut).stream()
                .map(emailSendMapper::toResponse)
                .collect(Collectors.toList());
    }
}
