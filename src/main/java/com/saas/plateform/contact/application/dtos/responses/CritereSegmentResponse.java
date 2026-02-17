package com.saas.plateform.contact.application.dtos.responses;

import com.saas.plateform.contact.domain.enums.OperateurComparaison;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CritereSegmentResponse {

    private UUID trackingId;
    private UUID segmentTrackingId;
    private String field;
    private OperateurComparaison operator;
    private String value;
    private Integer orderIndex;
}
