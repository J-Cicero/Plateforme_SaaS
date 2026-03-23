package com.saas.plateform.campaign.application.dtos.requests;

import com.saas.plateform.campaign.domain.enums.StatutCampaign;
import com.saas.plateform.campaign.domain.enums.TypeCampaign;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CampaignRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private TypeCampaign type;

    private StatutCampaign status;

    @FutureOrPresent
    private LocalDate startDate;

    private LocalDate endDate;
}
