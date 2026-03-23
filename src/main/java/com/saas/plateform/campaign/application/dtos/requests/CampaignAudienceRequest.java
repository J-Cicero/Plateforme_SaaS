package com.saas.plateform.campaign.application.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignAudienceRequest {

    @NotNull
    private Long segmentId;

    private String filtresJson;
}
