package com.saas.plateform.campaign.application.controllers;

import com.saas.plateform.campaign.application.dtos.requests.CampaignRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignResponse;
import com.saas.plateform.campaign.domain.enums.StatutCampaign;
import com.saas.plateform.campaign.domain.enums.TypeCampaign;
import com.saas.plateform.campaign.domain.services.CampaignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
@Tag(name = "Campaigns", description = "Campaign management APIs")
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    @Operation(summary = "Create a new campaign")
    public ResponseEntity<CampaignResponse> createCampaign(
            @Valid @RequestBody CampaignRequest request,
            @RequestParam UUID creatorTrackingId) {
        CampaignResponse response = campaignService.createCampaign(request, creatorTrackingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get campaign by id")
    public ResponseEntity<CampaignResponse> getCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaign(id));
    }

    @GetMapping
    @Operation(summary = "Get all campaigns")
    public ResponseEntity<List<CampaignResponse>> getAll() {
        return ResponseEntity.ok(campaignService.getAll());
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get campaigns by status")
    public ResponseEntity<List<CampaignResponse>> getByStatus(@PathVariable StatutCampaign status) {
        return ResponseEntity.ok(campaignService.getByStatus(status));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get campaigns by type")
    public ResponseEntity<List<CampaignResponse>> getByType(@PathVariable TypeCampaign type) {
        return ResponseEntity.ok(campaignService.getByType(type));
    }

    @GetMapping("/creator/{creatorTrackingId}")
    @Operation(summary = "Get campaigns by creator")
    public ResponseEntity<List<CampaignResponse>> getByCreator(@PathVariable UUID creatorTrackingId) {
        return ResponseEntity.ok(campaignService.getByCreator(creatorTrackingId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a campaign")
    public ResponseEntity<CampaignResponse> updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody CampaignRequest request) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a campaign")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }
}
