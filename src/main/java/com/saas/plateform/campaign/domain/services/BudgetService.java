package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.requests.BudgetRequest;
import com.saas.plateform.campaign.application.dtos.responses.BudgetResponse;

public interface BudgetService {

    BudgetResponse createOrUpdate(Long campaignId, BudgetRequest request);

    BudgetResponse getByCampaign(Long campaignId);

    void deleteByCampaign(Long campaignId);
}
