package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.responses.ResultatCampagneResponse;
import com.saas.plateform.campaign.domain.models.ResultatCampagne;
import org.springframework.stereotype.Component;

@Component
public class ResultatCampagneMapper {

    public ResultatCampagneResponse toResponse(ResultatCampagne resultat) {
        return ResultatCampagneResponse.builder()
                .id(resultat.getId())
                .trackingId(resultat.getTrackingId())
                .campaignId(resultat.getCampaign() != null ? resultat.getCampaign().getId() : null)
                .canal(resultat.getCanal())
                .nombreEnvoyes(resultat.getNombreEnvoyes())
                .nombreOuverts(resultat.getNombreOuverts())
                .nombreCliques(resultat.getNombreCliques())
                .nombreConversions(resultat.getNombreConversions())
                .nombreEchecs(resultat.getNombreEchecs())
                .dateCalcul(resultat.getDateCalcul())
                .createdAt(resultat.getCreatedAt())
                .updatedAt(resultat.getUpdatedAt())
                .build();
    }
}
