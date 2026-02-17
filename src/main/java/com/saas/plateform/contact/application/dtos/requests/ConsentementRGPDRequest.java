package com.saas.plateform.contact.application.dtos.requests;

import com.saas.plateform.contact.domain.enums.TypeConsentement;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentementRGPDRequest {

    @NotNull(message = "Contact tracking ID is required")
    private UUID contactTrackingId;

    @NotNull(message = "Consent type is required")
    private TypeConsentement type;

    @NotNull(message = "Accepted status is required")
    private Boolean accepted;

    private String ipAddress;
}
