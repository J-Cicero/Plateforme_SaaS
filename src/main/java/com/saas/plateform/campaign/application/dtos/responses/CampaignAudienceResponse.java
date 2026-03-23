package com.saas.plateform.campaign.application.dtos.responses;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CampaignAudienceResponse {

    private Long id;
    private Long campaignId;
    private Long segmentId;
    private String filtresJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
