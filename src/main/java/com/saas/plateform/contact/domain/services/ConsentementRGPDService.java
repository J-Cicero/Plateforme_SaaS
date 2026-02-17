package com.saas.plateform.contact.domain.services;

import com.saas.plateform.contact.application.dtos.requests.ConsentementRGPDRequest;
import com.saas.plateform.contact.application.dtos.responses.ConsentementRGPDResponse;
import com.saas.plateform.contact.domain.enums.TypeConsentement;

import java.util.List;
import java.util.UUID;

public interface ConsentementRGPDService {
    
    ConsentementRGPDResponse createConsentement(ConsentementRGPDRequest request);
    
    ConsentementRGPDResponse getConsentementByTrackingId(UUID trackingId);
    
    List<ConsentementRGPDResponse> getConsentementsByContact(UUID contactTrackingId);
    
    List<ConsentementRGPDResponse> getAcceptedConsentementsByContact(UUID contactTrackingId);
    
    ConsentementRGPDResponse updateConsentement(UUID trackingId, ConsentementRGPDRequest request);
    
    void deleteConsentement(UUID trackingId);
    
    boolean hasConsentement(UUID contactTrackingId, TypeConsentement type);
}
