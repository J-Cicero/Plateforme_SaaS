package com.saas.plateform.contact.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.contact.domain.enums.CanalSource;
import com.saas.plateform.contact.domain.enums.StatutContact;
import com.saas.plateform.contact.domain.enums.TypeInteraction;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.models.Interaction;
import com.saas.plateform.contact.domain.services.InteractionService;
import com.saas.plateform.contact.infrastructure.repositories.ContactRepository;
import com.saas.plateform.contact.infrastructure.repositories.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InteractionServiceImpl implements InteractionService {

    private final ContactRepository contactRepository;
    private final InteractionRepository interactionRepository;

    @Override
    public void recordInteraction(UUID contactTrackingId, TypeInteraction type, CanalSource source, String details) {
        Contact contact = contactRepository.findByTrackingId(contactTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contact not found with trackingId: " + contactTrackingId));

        Interaction interaction = Interaction.builder()
                .contact(contact)
                .type(type)
                .source(source)
                .details(details)
                .date(LocalDateTime.now())
                .build();
        interactionRepository.save(interaction);

        recalculateLeadScore(contact, type);
        contactRepository.save(contact);
    }

    private void recalculateLeadScore(Contact contact, TypeInteraction type) {
        int increment = switch (type) {
            case VISITE_SITE -> 1;
            case OUVERTURE_EMAIL -> 3;
            case CLIC -> 5;
            case RECEPTION_SMS -> 2;
            case ACHAT -> 20;
        };

        int newScore = (contact.getLeadScore() != null ? contact.getLeadScore() : 0) + increment;
        contact.setLeadScore(newScore);

        if (newScore < 10) {
            contact.setStatus(StatutContact.PROSPECT);
        } else if (newScore < 50) {
            contact.setStatus(StatutContact.QUALIFIED_LEAD);
        } else {
            contact.setStatus(StatutContact.CLIENT);
        }
    }
}
