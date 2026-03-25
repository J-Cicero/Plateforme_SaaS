package com.saas.plateform.contact.application.dtos.responses;

import com.saas.plateform.contact.domain.enums.StatutContact;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponse {

    private UUID trackingId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String company;
    private String position;
    private StatutContact status;
    private String city;
    private String country;
    private Integer leadScore;
    private Boolean archived;
    private UUID ownerTrackingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
