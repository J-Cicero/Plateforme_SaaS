package com.saas.plateform.campaign.application.dtos.responses;

import com.saas.plateform.campaign.domain.enums.CritereGagnant;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ABTestResponse {

    private Long id;
    private Long campaignId;
    private String nomVarianteA;
    private String nomVarianteB;
    private String templateNameA;
    private String templateNameB;
    private Integer pourcentageA;
    private Integer pourcentageB;
    private CritereGagnant critereGagnant;
    private String gagnant;
    private String statut;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
