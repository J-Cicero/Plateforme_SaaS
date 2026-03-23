package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.requests.ABTestRequest;
import com.saas.plateform.campaign.application.dtos.responses.ABTestResponse;

import java.util.List;

public interface ABTestService {

    ABTestResponse create(Long campaignId, ABTestRequest request);

    ABTestResponse get(Long id);

    List<ABTestResponse> getByCampaign(Long campaignId);

    ABTestResponse update(Long id, ABTestRequest request);

    void delete(Long id);
}
