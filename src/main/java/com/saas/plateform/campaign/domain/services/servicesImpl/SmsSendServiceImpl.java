package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.campaign.application.dtos.responses.SmsSendResponse;
import com.saas.plateform.campaign.application.mappers.SmsSendMapper;
import com.saas.plateform.campaign.domain.enums.StatutEnvoi;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.enums.TypeEvenement;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignChannel;
import com.saas.plateform.campaign.domain.models.ResultatCampagne;
import com.saas.plateform.campaign.domain.models.SmsSend;
import com.saas.plateform.campaign.domain.services.SmsSendService;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignChannelRepository;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import com.saas.plateform.campaign.infrastructure.repositories.ResultatCampagneRepository;
import com.saas.plateform.campaign.infrastructure.repositories.SmsSendRepository;
import com.saas.plateform.contact.domain.enums.CanalSource;
import com.saas.plateform.contact.domain.enums.TypeInteraction;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.services.InteractionService;
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
public class SmsSendServiceImpl implements SmsSendService {

    private static final Logger log = LoggerFactory.getLogger(SmsSendServiceImpl.class);

    private final CampaignRepository campaignRepository;
    private final CampaignChannelRepository channelRepository;
    private final ContactRepository contactRepository;
    private final SmsSendRepository smsSendRepository;
    private final ResultatCampagneRepository resultatCampagneRepository;
    private final InteractionService interactionService;
    private final SmsSendMapper smsSendMapper;

    @Override
    public SmsSendResponse sendSms(Long campaignId, Long channelId, Long contactId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Campaign not found with id: " + campaignId));

        CampaignChannel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CampaignChannel not found with id: " + channelId));

        if (channel.getTypeCanal() != TypeCanal.SMS) {
            throw new IllegalArgumentException("Channel type must be SMS for SMS sending");
        }

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contact not found with id: " + contactId));

        SmsSend smsSend = SmsSend.builder()
                .campaign(campaign)
                .channel(channel)
                .contact(contact)
                .phoneNumber(contact.getPhone())
                .content(channel.getTemplateName())
                .statut(StatutEnvoi.EN_ATTENTE)
                .build();

        try {
            // TODO: Intégrer un vrai service SMS (Twilio, Nexmo, etc.) ici
            // Pour le moment, on simule l'envoi réussi
            log.info("Sending SMS to contact {} via channel {}", contactId, channelId);

            smsSend.setStatut(StatutEnvoi.ENVOYE);
            smsSend.setSentAt(LocalDateTime.now());

            // Créer une interaction pour le contact
            interactionService.recordInteraction(contact.getTrackingId(), TypeInteraction.RECEPTION_SMS, CanalSource.SMS, null);

            // Mettre à jour les statistiques de la campagne
            updateCampagneResult(campaign);

            log.info("✅ SMS sent successfully to contact {} for campaign {}", contactId, campaignId);

        } catch (Exception ex) {
            log.error("Error while sending SMS for campaign {} to contact {}",
                    campaignId, contactId, ex);
            smsSend.setStatut(StatutEnvoi.ECHEC);
            smsSend.setMessageErreur(ex.getMessage());

            // Aussi mettre à jour les résultats en cas d'échec
            updateCampagneResultError(campaign);
        }

        SmsSend saved = smsSendRepository.save(smsSend);
        return smsSendMapper.toResponse(saved);
    }

    @Override
    public SmsSend getSmsSend(Long smsSendId) {
        return smsSendRepository.findById(smsSendId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "SmsSend not found with id: " + smsSendId));
    }

    private void updateCampagneResult(Campaign campaign) {
        ResultatCampagne resultat = resultatCampagneRepository.findByCampaignAndCanal(campaign, TypeCanal.SMS)
                .orElseGet(() -> {
                    ResultatCampagne newResultat = ResultatCampagne.builder()
                            .campaign(campaign)
                            .canal(TypeCanal.SMS)
                            .nombreEnvoyes(0)
                            .nombreOuverts(0)
                            .nombreCliques(0)
                            .nombreConversions(0)
                            .nombreEchecs(0)
                            .dateCalcul(LocalDateTime.now())
                            .build();
                    return resultatCampagneRepository.save(newResultat);
                });

        resultat.setNombreEnvoyes(resultat.getNombreEnvoyes() + 1);
        resultat.setDateCalcul(LocalDateTime.now());
        resultatCampagneRepository.save(resultat);
    }

    private void updateCampagneResultError(Campaign campaign) {
        ResultatCampagne resultat = resultatCampagneRepository.findByCampaignAndCanal(campaign, TypeCanal.SMS)
                .orElseGet(() -> {
                    ResultatCampagne newResultat = ResultatCampagne.builder()
                            .campaign(campaign)
                            .canal(TypeCanal.SMS)
                            .nombreEnvoyes(0)
                            .nombreOuverts(0)
                            .nombreCliques(0)
                            .nombreConversions(0)
                            .nombreEchecs(0)
                            .dateCalcul(LocalDateTime.now())
                            .build();
                    return resultatCampagneRepository.save(newResultat);
                });

        resultat.setNombreEchecs(resultat.getNombreEchecs() + 1);
        resultat.setDateCalcul(LocalDateTime.now());
        resultatCampagneRepository.save(resultat);
    }
}
