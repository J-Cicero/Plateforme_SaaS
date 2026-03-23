package com.saas.plateform.campaign.application.dtos.responses;

import com.saas.plateform.campaign.domain.enums.TypeCanal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CampaignChannelResponse {

    private Long id;
    private Long campaignId;
    private TypeCanal typeCanal;
    private String templateName;
    private String sujet;
    private String senderEmail;
    private String configJson;
    private Boolean actif;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
