package com.saas.plateform.contact.application.mappers;

import com.saas.plateform.contact.domain.models.Segment;
import com.saas.plateform.contact.application.dtos.requests.SegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.SegmentResponse;
import org.springframework.stereotype.Component;

@Component
public class SegmentMapper {

    public Segment toEntity(SegmentRequest request) {
        return Segment.builder()
                .lastName(request.getLastName())
                .description(request.getDescription())
                .type(request.getType())
                .dynamic(request.getDynamic() != null ? request.getDynamic() : false)
                .build();
    }

    public SegmentResponse toResponse(Segment segment) {
        return SegmentResponse.builder()
                .trackingId(segment.getTrackingId())
                .lastName(segment.getLastName())
                .description(segment.getDescription())
                .type(segment.getType())
                .dynamic(segment.getDynamic())
                .creatorTrackingId(segment.getCreator() != null ? segment.getCreator().getTrackingId() : null)
                .createdAt(segment.getCreatedAt())
                .updatedAt(segment.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(SegmentRequest request, Segment segment) {
        if (request.getLastName() != null) segment.setLastName(request.getLastName());
        if (request.getDescription() != null) segment.setDescription(request.getDescription());
        if (request.getType() != null) segment.setType(request.getType());
        if (request.getDynamic() != null) segment.setDynamic(request.getDynamic());
    }
}
