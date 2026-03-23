package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.requests.CampaignChannelRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignChannelResponse;
import com.saas.plateform.campaign.domain.models.CampaignChannel;
import org.springframework.stereotype.Component;

@Component
public class CampaignChannelMapper {

    public CampaignChannel toEntity(CampaignChannelRequest request) {
        return CampaignChannel.builder()
                .typeCanal(request.getTypeCanal())
                .templateName(request.getTemplateName())
                .sujet(request.getSujet())
                .senderEmail(request.getSenderEmail())
                .configJson(request.getConfigJson())
                .actif(request.getActif() != null ? request.getActif() : true)
                .build();
    }

    public CampaignChannelResponse toResponse(CampaignChannel channel) {
        return CampaignChannelResponse.builder()
                .id(channel.getId())
                .campaignId(channel.getCampaign() != null ? channel.getCampaign().getId() : null)
                .typeCanal(channel.getTypeCanal())
                .templateName(channel.getTemplateName())
                .sujet(channel.getSujet())
                .senderEmail(channel.getSenderEmail())
                .configJson(channel.getConfigJson())
                .actif(channel.getActif())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(CampaignChannelRequest request, CampaignChannel channel) {
        if (request.getTypeCanal() != null) channel.setTypeCanal(request.getTypeCanal());
        if (request.getTemplateName() != null) channel.setTemplateName(request.getTemplateName());
        if (request.getSujet() != null) channel.setSujet(request.getSujet());
        if (request.getSenderEmail() != null) channel.setSenderEmail(request.getSenderEmail());
        if (request.getConfigJson() != null) channel.setConfigJson(request.getConfigJson());
        if (request.getActif() != null) channel.setActif(request.getActif());
    }
}
