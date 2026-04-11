package com.saas.plateform.campaign.application.dtos.responses;

import com.saas.plateform.campaign.domain.enums.TypeCanal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResultatCampagneResponse {

    private Long id;
    private String trackingId;
    private Long campaignId;
    private TypeCanal canal;
    private Integer nombreEnvoyes;
    private Integer nombreOuverts;
    private Integer nombreCliques;
    private Integer nombreConversions;
    private Integer nombreEchecs;
    private LocalDateTime dateCalcul;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
