package com.saas.plateform.contact.application.dtos.requests;

import com.saas.plateform.contact.domain.enums.TypeSegment;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SegmentRequest {

    @NotBlank(message = "Segment name is required")
    private String lastName;

    private String description;
    private TypeSegment type;
    @Builder.Default
    private Boolean dynamic = false;
}
