package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.requests.CampaignRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignResponse;
import com.saas.plateform.campaign.domain.enums.StatutCampaign;
import com.saas.plateform.campaign.domain.enums.TypeCampaign;

import java.util.List;
import java.util.UUID;

public interface CampaignService {

    CampaignResponse createCampaign(CampaignRequest request, UUID creatorTrackingId);

    CampaignResponse getCampaign(Long id);

    List<CampaignResponse> getAll();

    List<CampaignResponse> getByStatus(StatutCampaign status);

    List<CampaignResponse> getByType(TypeCampaign type);

    List<CampaignResponse> getByCreator(UUID creatorTrackingId);

    CampaignResponse updateCampaign(Long id, CampaignRequest request);

    void deleteCampaign(Long id);
}
