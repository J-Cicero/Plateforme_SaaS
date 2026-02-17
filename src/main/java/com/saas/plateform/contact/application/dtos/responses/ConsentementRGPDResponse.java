package com.saas.plateform.contact.application.dtos.responses;

import com.saas.plateform.contact.domain.enums.TypeConsentement;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentementRGPDResponse {

    private UUID trackingId;
    private UUID contactTrackingId;
    private TypeConsentement type;
    private Boolean accepted;
    private LocalDateTime consentDate;
    private String ipAddress;
}
