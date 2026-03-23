package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.requests.ABTestRequest;
import com.saas.plateform.campaign.application.dtos.responses.ABTestResponse;
import com.saas.plateform.campaign.domain.models.ABTest;
import org.springframework.stereotype.Component;

@Component
public class ABTestMapper {

    public ABTest toEntity(ABTestRequest request) {
        return ABTest.builder()
                .nomVarianteA(request.getNomVarianteA())
                .nomVarianteB(request.getNomVarianteB())
                .templateNameA(request.getTemplateNameA())
                .templateNameB(request.getTemplateNameB())
                .pourcentageA(request.getPourcentageA())
                .pourcentageB(request.getPourcentageB())
                .critereGagnant(request.getCritereGagnant())
                .gagnant(request.getGagnant())
                .statut(request.getStatut())
                .build();
    }

    public ABTestResponse toResponse(ABTest abTest) {
        return ABTestResponse.builder()
                .id(abTest.getId())
                .campaignId(abTest.getCampaign() != null ? abTest.getCampaign().getId() : null)
                .nomVarianteA(abTest.getNomVarianteA())
                .nomVarianteB(abTest.getNomVarianteB())
                .templateNameA(abTest.getTemplateNameA())
                .templateNameB(abTest.getTemplateNameB())
                .pourcentageA(abTest.getPourcentageA())
                .pourcentageB(abTest.getPourcentageB())
                .critereGagnant(abTest.getCritereGagnant())
                .gagnant(abTest.getGagnant())
                .statut(abTest.getStatut())
                .createdAt(abTest.getCreatedAt())
                .updatedAt(abTest.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(ABTestRequest request, ABTest abTest) {
        if (request.getNomVarianteA() != null) abTest.setNomVarianteA(request.getNomVarianteA());
        if (request.getNomVarianteB() != null) abTest.setNomVarianteB(request.getNomVarianteB());
        if (request.getTemplateNameA() != null) abTest.setTemplateNameA(request.getTemplateNameA());
        if (request.getTemplateNameB() != null) abTest.setTemplateNameB(request.getTemplateNameB());
        if (request.getPourcentageA() != null) abTest.setPourcentageA(request.getPourcentageA());
        if (request.getPourcentageB() != null) abTest.setPourcentageB(request.getPourcentageB());
        if (request.getCritereGagnant() != null) abTest.setCritereGagnant(request.getCritereGagnant());
        if (request.getGagnant() != null) abTest.setGagnant(request.getGagnant());
        if (request.getStatut() != null) abTest.setStatut(request.getStatut());
    }
}
