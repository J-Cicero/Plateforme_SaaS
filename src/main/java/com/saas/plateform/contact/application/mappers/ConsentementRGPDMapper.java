package com.saas.plateform.contact.application.mappers;

import com.saas.plateform.contact.domain.models.ConsentementRGPD;
import com.saas.plateform.contact.application.dtos.requests.ConsentementRGPDRequest;
import com.saas.plateform.contact.application.dtos.responses.ConsentementRGPDResponse;
import org.springframework.stereotype.Component;

@Component
public class ConsentementRGPDMapper {

    public ConsentementRGPD toEntity(ConsentementRGPDRequest request) {
        return ConsentementRGPD.builder()
                .type(request.getType())
                .accepted(request.getAccepted())
                .ipAddress(request.getIpAddress())
                .build();
    }

    public ConsentementRGPDResponse toResponse(ConsentementRGPD consentement) {
        return ConsentementRGPDResponse.builder()
                .trackingId(consentement.getTrackingId())
                .contactTrackingId(consentement.getContact() != null ? consentement.getContact().getTrackingId() : null)
                .type(consentement.getType())
                .accepted(consentement.getAccepted())
                .consentDate(consentement.getConsentDate())
                .ipAddress(consentement.getIpAddress())
                .build();
    }
}
