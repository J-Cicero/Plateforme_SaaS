package com.saas.plateform.campaign.application.dtos.responses;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class BudgetResponse {

    private Long id;
    private Long campaignId;
    private BigDecimal montantTotal;
    private BigDecimal montantDepense;
    private String devise;
    private BigDecimal alerteSeuil;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
