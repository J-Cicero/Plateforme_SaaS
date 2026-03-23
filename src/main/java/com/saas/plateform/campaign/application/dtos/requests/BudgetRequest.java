package com.saas.plateform.campaign.application.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BudgetRequest {

    @NotNull
    @Positive
    private BigDecimal montantTotal;

    @Positive
    private BigDecimal montantDepense;

    @NotNull
    @Size(min = 1, max = 10)
    private String devise;

    @Positive
    private BigDecimal alerteSeuil;
}
