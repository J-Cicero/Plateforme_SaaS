package com.saas.plateform.campaign.application.controllers;

import com.saas.plateform.campaign.application.dtos.responses.ResultatCampagneResponse;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.ResultatCampagne;
import com.saas.plateform.campaign.domain.services.ResultatCampagneService;
import com.saas.plateform.campaign.application.mappers.ResultatCampagneMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/campaigns/{campaignId}/resultats")
@RequiredArgsConstructor
@Tag(name = "Campaign Results", description = "Results tracking APIs for campaigns")
public class ResultatCampagneController {

    private final ResultatCampagneService resultatCampagneService;
    private final ResultatCampagneMapper resultatCampagneMapper;

    @GetMapping
    @Operation(summary = "Get all results for a campaign")
    public ResponseEntity<List<ResultatCampagneResponse>> getAllResults(@PathVariable Long campaignId) {
        List<ResultatCampagne> resultats = resultatCampagneService.trouverParCampagne(campaignId);
        List<ResultatCampagneResponse> responses = resultats.stream()
                .map(resultatCampagneMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{canal}")
    @Operation(summary = "Get result for a campaign and a specific channel")
    public ResponseEntity<ResultatCampagneResponse> getResultByCanal(
            @PathVariable Long campaignId,
            @PathVariable TypeCanal canal) {
        return resultatCampagneService.trouverParCampagneEtCanal(campaignId, canal)
                .map(resultat -> ResponseEntity.ok(resultatCampagneMapper.toResponse(resultat)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
