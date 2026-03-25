package com.saas.plateform.contact.domain.services;

import com.saas.plateform.contact.application.dtos.requests.ContactRequest;
import com.saas.plateform.contact.application.dtos.responses.ContactResponse;
import com.saas.plateform.contact.domain.enums.StatutContact;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    
    ContactResponse createContact(ContactRequest request, UUID ownerTrackingId);
    
    ContactResponse getContactByTrackingId(UUID trackingId);
    
    List<ContactResponse> getAllContactsByOwner(UUID ownerTrackingId);
    
    List<ContactResponse> getContactsByStatus(StatutContact status);
    
    List<ContactResponse> getActiveContactsByOwner(UUID ownerTrackingId);
    
    ContactResponse updateContact(UUID trackingId, ContactRequest request);
    
    void archiveContact(UUID trackingId);
    
    void deleteContact(UUID trackingId);

    void addContactToSegment(UUID contactTrackingId, UUID segmentTrackingId);
    
    void removeContactFromSegment(UUID contactTrackingId, UUID segmentTrackingId);
}
