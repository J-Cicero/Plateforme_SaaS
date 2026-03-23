package com.saas.plateform.campaign.application.dtos.requests;

import com.saas.plateform.campaign.domain.enums.CritereGagnant;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ABTestRequest {

    @NotBlank
    private String nomVarianteA;

    @NotBlank
    private String nomVarianteB;

    @NotBlank
    private String templateNameA;

    @NotBlank
    private String templateNameB;

    @Min(1)
    @Max(99)
    private Integer pourcentageA;

    @Min(1)
    @Max(99)
    private Integer pourcentageB;

    @NotNull
    private CritereGagnant critereGagnant;

    private String gagnant;

    private String statut;
}
