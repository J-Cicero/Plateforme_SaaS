package com.saas.plateform.contact.domain.services.servicesImpl;

import com.saas.plateform.contact.application.dtos.requests.ContactRequest;
import com.saas.plateform.contact.application.dtos.responses.ContactResponse;
import com.saas.plateform.contact.application.mappers.ContactMapper;
import com.saas.plateform.contact.domain.enums.StatutContact;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.models.ContactSegment;
import com.saas.plateform.contact.domain.models.Segment;
import com.saas.plateform.contact.domain.services.ContactService;
import com.saas.plateform.contact.infrastructure.repositories.ContactRepository;
import com.saas.plateform.contact.infrastructure.repositories.ContactSegmentRepository;
import com.saas.plateform.contact.infrastructure.repositories.SegmentRepository;
import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.Shared.security.user.infrastructure.repositories.UserRepository;
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
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final SegmentRepository segmentRepository;
    private final ContactSegmentRepository contactSegmentRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactResponse createContact(ContactRequest request, UUID ownerTrackingId) {
        if (contactRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("Contact with email '" + request.getEmail() + "' already exists");
        }

        User owner = userRepository.findByTrackingId(ownerTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with trackingId: " + ownerTrackingId));

        Contact contact = contactMapper.toEntity(request);
        contact.setOwner(owner);
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toResponse(savedContact);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactResponse getContactByTrackingId(UUID trackingId) {
        Contact contact = contactRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + trackingId));
        return contactMapper.toResponse(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContactsByOwner(UUID ownerTrackingId) {
        User owner = userRepository.findByTrackingId(ownerTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with trackingId: " + ownerTrackingId));
        
        return contactRepository.findByOwner(owner).stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getContactsByStatus(StatutContact status) {
        return contactRepository.findByStatus(status).stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getActiveContactsByOwner(UUID ownerTrackingId) {
        User owner = userRepository.findByTrackingId(ownerTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with trackingId: " + ownerTrackingId));
        
        return contactRepository.findByOwnerAndArchived(owner, false).stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ContactResponse updateContact(UUID trackingId, ContactRequest request) {
        Contact contact = contactRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + trackingId));

        if (!contact.getEmail().equals(request.getEmail()) && 
            contactRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("Contact with email '" + request.getEmail() + "' already exists");
        }

        contactMapper.updateEntityFromRequest(request, contact);
        Contact updatedContact = contactRepository.save(contact);
        return contactMapper.toResponse(updatedContact);
    }

    @Override
    public void archiveContact(UUID trackingId) {
        Contact contact = contactRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + trackingId));
        contact.setArchived(true);
        contactRepository.save(contact);
    }

    @Override
    public void deleteContact(UUID trackingId) {
        Contact contact = contactRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + trackingId));
        contactRepository.delete(contact);
    }

    @Override
    public void addContactToSegment(UUID contactTrackingId, UUID segmentTrackingId) {
        Contact contact = contactRepository.findByTrackingId(contactTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + contactTrackingId));
        
        Segment segment = segmentRepository.findByTrackingId(segmentTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Segment not found with trackingId: " + segmentTrackingId));

        if (contactSegmentRepository.existsByContactAndSegment(contact, segment)) {
            throw new AlreadyExistException("Contact already in this segment");
        }

        ContactSegment contactSegment = ContactSegment.builder()
                .contact(contact)
                .segment(segment)
                .build();
        contactSegmentRepository.save(contactSegment);
    }

    @Override
    public void removeContactFromSegment(UUID contactTrackingId, UUID segmentTrackingId) {
        Contact contact = contactRepository.findByTrackingId(contactTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with trackingId: " + contactTrackingId));
        
        Segment segment = segmentRepository.findByTrackingId(segmentTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Segment not found with trackingId: " + segmentTrackingId));

        contactSegmentRepository.deleteByContactAndSegment(contact, segment);
    }
}
