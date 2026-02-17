package com.saas.plateform.contact.domain.services;

import com.saas.plateform.contact.application.dtos.requests.CritereSegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.CritereSegmentResponse;

import java.util.List;
import java.util.UUID;

public interface CritereSegmentService {
    
    CritereSegmentResponse createCritere(CritereSegmentRequest request);
    
    CritereSegmentResponse getCritereByTrackingId(UUID trackingId);
    
    List<CritereSegmentResponse> getCriteresBySegment(UUID segmentTrackingId);
    
    CritereSegmentResponse updateCritere(UUID trackingId, CritereSegmentRequest request);
    
    void deleteCritere(UUID trackingId);
}
