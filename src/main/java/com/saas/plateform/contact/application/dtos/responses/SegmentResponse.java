package com.saas.plateform.contact.application.dtos.responses;

import com.saas.plateform.contact.domain.enums.TypeSegment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SegmentResponse {

    private UUID trackingId;
    private String lastName;
    private String description;
    private TypeSegment type;
    private Boolean dynamic;
    private UUID creatorTrackingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
