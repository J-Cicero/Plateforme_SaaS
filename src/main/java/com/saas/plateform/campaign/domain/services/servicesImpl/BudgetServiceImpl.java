package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.campaign.application.dtos.requests.BudgetRequest;
import com.saas.plateform.campaign.application.dtos.responses.BudgetResponse;
import com.saas.plateform.campaign.application.mappers.BudgetMapper;
import com.saas.plateform.campaign.domain.models.Budget;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.services.BudgetService;
import com.saas.plateform.campaign.infrastructure.repositories.BudgetRepository;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetServiceImpl implements BudgetService {

    private final CampaignRepository campaignRepository;
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    @Override
    public BudgetResponse createOrUpdate(Long campaignId, BudgetRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        Budget budget = budgetRepository.findByCampaign(campaign)
                .orElseGet(() -> {
                    Budget b = budgetMapper.toEntity(request);
                    b.setCampaign(campaign);
                    return b;
                });

        if (budget.getId() != null) {
            budgetMapper.updateEntityFromRequest(request, budget);
        }

        Budget saved = budgetRepository.save(budget);
        return budgetMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BudgetResponse getByCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        Budget budget = budgetRepository.findByCampaign(campaign)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found for campaign: " + campaignId));

        return budgetMapper.toResponse(budget);
    }

    @Override
    public void deleteByCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        budgetRepository.findByCampaign(campaign).ifPresent(budgetRepository::delete);
    }
}
