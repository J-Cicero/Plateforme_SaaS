package com.saas.plateform.contact.application.mappers;

import com.saas.plateform.contact.domain.models.CritereSegment;
import com.saas.plateform.contact.application.dtos.requests.CritereSegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.CritereSegmentResponse;
import org.springframework.stereotype.Component;

@Component
public class CritereSegmentMapper {

    public CritereSegment toEntity(CritereSegmentRequest request) {
        return CritereSegment.builder()
                .field(request.getField())
                .operator(request.getOperator())
                .value(request.getValue())
                .orderIndex(request.getOrderIndex() != null ? request.getOrderIndex() : 0)
                .build();
    }

    public CritereSegmentResponse toResponse(CritereSegment critere) {
        return CritereSegmentResponse.builder()
                .trackingId(critere.getTrackingId())
                .segmentTrackingId(critere.getSegment() != null ? critere.getSegment().getTrackingId() : null)
                .field(critere.getField())
                .operator(critere.getOperator())
                .value(critere.getValue())
                .orderIndex(critere.getOrderIndex())
                .build();
    }

    public void updateEntityFromRequest(CritereSegmentRequest request, CritereSegment critere) {
        if (request.getField() != null) critere.setField(request.getField());
        if (request.getOperator() != null) critere.setOperator(request.getOperator());
        if (request.getValue() != null) critere.setValue(request.getValue());
        if (request.getOrderIndex() != null) critere.setOrderIndex(request.getOrderIndex());
    }
}
