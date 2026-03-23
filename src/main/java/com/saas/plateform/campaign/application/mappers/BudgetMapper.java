package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.requests.BudgetRequest;
import com.saas.plateform.campaign.application.dtos.responses.BudgetResponse;
import com.saas.plateform.campaign.domain.models.Budget;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BudgetMapper {

    public Budget toEntity(BudgetRequest request) {
        return Budget.builder()
                .montantTotal(request.getMontantTotal())
                .montantDepense(request.getMontantDepense() != null
                        ? request.getMontantDepense()
                        : BigDecimal.ZERO)
                .devise(request.getDevise())
                .alerteSeuil(request.getAlerteSeuil())
                .build();
    }

    public BudgetResponse toResponse(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .campaignId(budget.getCampaign() != null ? budget.getCampaign().getId() : null)
                .montantTotal(budget.getMontantTotal())
                .montantDepense(budget.getMontantDepense())
                .devise(budget.getDevise())
                .alerteSeuil(budget.getAlerteSeuil())
                .createdAt(budget.getCreatedAt())
                .updatedAt(budget.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(BudgetRequest request, Budget budget) {
        if (request.getMontantTotal() != null) budget.setMontantTotal(request.getMontantTotal());
        if (request.getMontantDepense() != null) budget.setMontantDepense(request.getMontantDepense());
        if (request.getDevise() != null) budget.setDevise(request.getDevise());
        if (request.getAlerteSeuil() != null) budget.setAlerteSeuil(request.getAlerteSeuil());
    }
}
