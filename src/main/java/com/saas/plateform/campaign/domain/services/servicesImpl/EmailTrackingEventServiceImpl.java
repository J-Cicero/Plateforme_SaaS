package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.campaign.domain.enums.TypeEvenement;
import com.saas.plateform.campaign.domain.models.EmailSend;
import com.saas.plateform.campaign.domain.models.EmailTrackingEvent;
import com.saas.plateform.campaign.domain.services.EmailTrackingEventService;
import com.saas.plateform.campaign.infrastructure.repositories.EmailSendRepository;
import com.saas.plateform.campaign.infrastructure.repositories.EmailTrackingEventRepository;
import com.saas.plateform.contact.domain.enums.CanalSource;
import com.saas.plateform.contact.domain.enums.TypeInteraction;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.services.InteractionService;
import com.saas.plateform.contact.infrastructure.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailTrackingEventServiceImpl implements EmailTrackingEventService {

    private final EmailTrackingEventRepository emailTrackingEventRepository;
    private final EmailSendRepository emailSendRepository;
    private final ContactRepository contactRepository;
    private final InteractionService interactionService;

    @Override
    public EmailTrackingEvent recordEvent(Long emailSendId,
                                          Long contactId,
                                          TypeEvenement type,
                                          String urlCliquee,
                                          String ipAdresse,
                                          String userAgent) {

        EmailSend emailSend = emailSendRepository.findById(emailSendId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailSend not found with id: " + emailSendId));

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + contactId));

        EmailTrackingEvent event = EmailTrackingEvent.builder()
                .emailSend(emailSend)
                .contact(contact)
                .type(type)
                .urlCliquee(urlCliquee)
                .ipAdresse(ipAdresse)
                .userAgent(userAgent)
                .timestamp(LocalDateTime.now())
                .build();

        EmailTrackingEvent saved = emailTrackingEventRepository.save(event);
        forwardToInteraction(contact, type);
        return saved;
    }

    private void forwardToInteraction(Contact contact, TypeEvenement type) {
        switch (type) {
            case OUVERTURE ->
                    interactionService.recordInteraction(contact.getTrackingId(), TypeInteraction.OUVERTURE_EMAIL, CanalSource.EMAIL, null);
            case CLIC ->
                    interactionService.recordInteraction(contact.getTrackingId(), TypeInteraction.CLIC, CanalSource.EMAIL, null);
            default -> {
            }
        }
    }
}
