package com.saas.plateform.campaign.application.controllers;

import com.saas.plateform.campaign.application.dtos.requests.CampaignAudienceRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignAudienceResponse;
import com.saas.plateform.campaign.domain.services.CampaignAudienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns/{campaignId}/audiences")
@RequiredArgsConstructor
@Tag(name = "Campaign Audiences", description = "Audience management for campaigns")
public class CampaignAudienceController {

    private final CampaignAudienceService campaignAudienceService;

    @PostMapping
    @Operation(summary = "Add an audience to a campaign")
    public ResponseEntity<CampaignAudienceResponse> addAudience(
            @PathVariable Long campaignId,
            @Valid @RequestBody CampaignAudienceRequest request) {
        CampaignAudienceResponse response = campaignAudienceService.addAudience(campaignId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "List audiences for a campaign")
    public ResponseEntity<List<CampaignAudienceResponse>> getByCampaign(@PathVariable Long campaignId) {
        return ResponseEntity.ok(campaignAudienceService.getByCampaign(campaignId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an audience by id")
    public ResponseEntity<CampaignAudienceResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(campaignAudienceService.get(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an audience")
    public ResponseEntity<CampaignAudienceResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CampaignAudienceRequest request) {
        return ResponseEntity.ok(campaignAudienceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an audience")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        campaignAudienceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
