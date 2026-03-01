package com.saas.plateform.contact.application.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagResponse {

    private UUID trackingId;
    private String color;
    private String description;
    private LocalDateTime createdAt;
}
