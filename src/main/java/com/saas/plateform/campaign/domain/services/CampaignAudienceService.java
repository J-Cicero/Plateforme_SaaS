package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.requests.CampaignAudienceRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignAudienceResponse;

import java.util.List;

public interface CampaignAudienceService {

    CampaignAudienceResponse addAudience(Long campaignId, CampaignAudienceRequest request);

    CampaignAudienceResponse get(Long id);

    List<CampaignAudienceResponse> getByCampaign(Long campaignId);

    CampaignAudienceResponse update(Long id, CampaignAudienceRequest request);

    void delete(Long id);
}
