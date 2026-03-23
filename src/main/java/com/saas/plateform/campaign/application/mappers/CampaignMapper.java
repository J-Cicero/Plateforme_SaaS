package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.requests.CampaignRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignResponse;
import com.saas.plateform.campaign.domain.enums.StatutCampaign;
import com.saas.plateform.campaign.domain.models.Campaign;
import org.springframework.stereotype.Component;

@Component
public class CampaignMapper {

    public Campaign toEntity(CampaignRequest request) {
        return Campaign.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .status(request.getStatus() != null ? request.getStatus() : StatutCampaign.BROUILLON)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    public CampaignResponse toResponse(Campaign campaign) {
        return CampaignResponse.builder()
                .id(campaign.getId())
                .name(campaign.getName())
                .description(campaign.getDescription())
                .type(campaign.getType())
                .status(campaign.getStatus())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .createdByTrackingId(
                        campaign.getCreatedBy() != null ? campaign.getCreatedBy().getTrackingId() : null)
                .createdAt(campaign.getCreatedAt())
                .updatedAt(campaign.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(CampaignRequest request, Campaign campaign) {
        if (request.getName() != null) campaign.setName(request.getName());
        if (request.getDescription() != null) campaign.setDescription(request.getDescription());
        if (request.getType() != null) campaign.setType(request.getType());
        if (request.getStatus() != null) campaign.setStatus(request.getStatus());
        if (request.getStartDate() != null) campaign.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) campaign.setEndDate(request.getEndDate());
    }
}
