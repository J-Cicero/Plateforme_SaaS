package com.saas.plateform.contact.domain.services.servicesImpl;

import com.saas.plateform.contact.domain.services.ConsentementRGPDService;
import com.saas.plateform.contact.domain.models.ConsentementRGPD;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.infrastructure.repositories.ConsentementRGPDRepository;
import com.saas.plateform.contact.infrastructure.repositories.ContactRepository;
import com.saas.plateform.contact.application.dtos.requests.ConsentementRGPDRequest;
import com.saas.plateform.contact.application.dtos.responses.ConsentementRGPDResponse;
import com.saas.plateform.contact.application.mappers.ConsentementRGPDMapper;
import com.saas.plateform.contact.domain.enums.TypeConsentement;
import com.saas.plateform.Shared.security.exceptions.AlreadyExistException;
import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsentementRGPDServiceImpl implements ConsentementRGPDService {

    private final ConsentementRGPDRepository consentementRepository;
    private final ContactRepository contactRepository;
    private final ConsentementRGPDMapper consentementMapper;

    @Override
    public ConsentementRGPDResponse createConsentement(ConsentementRGPDRequest request) {
        Contact contact = contactRepository.findByTrackingId(request.getContactTrackingId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + request.getContactTrackingId()));

        // Vérifier si un consentement du même type existe déjà pour ce contact
        if (consentementRepository.findByContactAndType(contact, request.getType()).isPresent()) {
            throw new AlreadyExistException("Consent of type '" + request.getType() + "' already exists for this contact");
        }

        ConsentementRGPD consentement = consentementMapper.toEntity(request);
        consentement.setContact(contact);
        ConsentementRGPD savedConsentement = consentementRepository.save(consentement);
        return consentementMapper.toResponse(savedConsentement);
    }

    @Override
    @Transactional(readOnly = true)
    public ConsentementRGPDResponse getConsentementByTrackingId(UUID trackingId) {
        ConsentementRGPD consentement = consentementRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Consent not found with trackingId: " + trackingId));
        return consentementMapper.toResponse(consentement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsentementRGPDResponse> getConsentementsByContact(UUID contactTrackingId) {
        Contact contact = contactRepository.findByTrackingId(contactTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + contactTrackingId));
        
        return consentementRepository.findByContact(contact).stream()
                .map(consentementMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsentementRGPDResponse> getAcceptedConsentementsByContact(UUID contactTrackingId) {
        Contact contact = contactRepository.findByTrackingId(contactTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + contactTrackingId));
        
        return consentementRepository.findByContactAndAccepted(contact, true).stream()
                .map(consentementMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ConsentementRGPDResponse updateConsentement(UUID trackingId, ConsentementRGPDRequest request) {
        ConsentementRGPD consentement = consentementRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Consent not found with trackingId: " + trackingId));

        // Mise à jour uniquement du statut d'acceptation et de l'IP
        consentement.setAccepted(request.getAccepted());
        if (request.getIpAddress() != null) {
            consentement.setIpAddress(request.getIpAddress());
        }

        ConsentementRGPD updatedConsentement = consentementRepository.save(consentement);
        return consentementMapper.toResponse(updatedConsentement);
    }

    @Override
    public void deleteConsentement(UUID trackingId) {
        ConsentementRGPD consentement = consentementRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Consent not found with trackingId: " + trackingId));
        consentementRepository.delete(consentement);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasConsentement(UUID contactTrackingId, TypeConsentement type) {
        Contact contact = contactRepository.findByTrackingId(contactTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + contactTrackingId));
        
        return consentementRepository.findByContactAndType(contact, type)
                .map(ConsentementRGPD::getAccepted)
                .orElse(false);
    }
}
