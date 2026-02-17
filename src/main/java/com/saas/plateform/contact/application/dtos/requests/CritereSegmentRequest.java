package com.saas.plateform.contact.application.dtos.requests;

import com.saas.plateform.contact.domain.enums.OperateurComparaison;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CritereSegmentRequest {

    @NotNull(message = "Segment tracking ID is required")
    private UUID segmentTrackingId;

    @NotBlank(message = "Field name is required")
    private String field;

    @NotNull(message = "Operator is required")
    private OperateurComparaison operator;

    private String value;

    @Builder.Default
    private Integer orderIndex = 0;
}
