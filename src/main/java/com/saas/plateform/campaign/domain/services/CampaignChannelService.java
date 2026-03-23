package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.requests.CampaignChannelRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignChannelResponse;
import com.saas.plateform.campaign.domain.enums.TypeCanal;

import java.util.List;

public interface CampaignChannelService {

    CampaignChannelResponse create(Long campaignId, CampaignChannelRequest request);

    CampaignChannelResponse get(Long id);

    List<CampaignChannelResponse> getByCampaign(Long campaignId);

    List<CampaignChannelResponse> getByCampaignAndType(Long campaignId, TypeCanal type);

    CampaignChannelResponse update(Long id, CampaignChannelRequest request);

    void delete(Long id);
}
