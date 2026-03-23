package com.saas.plateform.campaign.application.dtos.responses;

import com.saas.plateform.campaign.domain.enums.StatutCampaign;
import com.saas.plateform.campaign.domain.enums.TypeCampaign;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CampaignResponse {

    private Long id;
    private String name;
    private String description;
    private TypeCampaign type;
    private StatutCampaign status;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID createdByTrackingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
