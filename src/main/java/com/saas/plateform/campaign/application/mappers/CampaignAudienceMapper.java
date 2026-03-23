package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.requests.CampaignAudienceRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignAudienceResponse;
import com.saas.plateform.campaign.domain.models.CampaignAudience;
import org.springframework.stereotype.Component;

@Component
public class CampaignAudienceMapper {

    public CampaignAudience toEntity(CampaignAudienceRequest request) {
        return CampaignAudience.builder()
                .filtresJson(request.getFiltresJson())
                .build();
    }

    public CampaignAudienceResponse toResponse(CampaignAudience audience) {
        return CampaignAudienceResponse.builder()
                .id(audience.getId())
                .campaignId(audience.getCampaign() != null ? audience.getCampaign().getId() : null)
                .segmentId(audience.getSegment() != null ? audience.getSegment().getId() : null)
                .filtresJson(audience.getFiltresJson())
                .createdAt(audience.getCreatedAt())
                .updatedAt(audience.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(CampaignAudienceRequest request, CampaignAudience audience) {
        if (request.getFiltresJson() != null) audience.setFiltresJson(request.getFiltresJson());
    }
}
