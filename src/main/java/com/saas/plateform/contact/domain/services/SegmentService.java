package com.saas.plateform.contact.domain.services;

import com.saas.plateform.contact.application.dtos.requests.SegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.SegmentResponse;

import java.util.List;
import java.util.UUID;

public interface SegmentService {
    
    SegmentResponse createSegment(SegmentRequest request, UUID creatorTrackingId);
    
    SegmentResponse getSegmentByTrackingId(UUID trackingId);
    
    List<SegmentResponse> getAllSegments();
    
    List<SegmentResponse> getSegmentsByCreator(UUID creatorTrackingId);
    
    List<SegmentResponse> getDynamicSegments();
    
    SegmentResponse updateSegment(UUID trackingId, SegmentRequest request);
    
    void deleteSegment(UUID trackingId);
}
