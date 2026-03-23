package com.saas.plateform.campaign.application.controllers;

import com.saas.plateform.campaign.application.dtos.requests.CampaignChannelRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignChannelResponse;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.services.CampaignChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns/{campaignId}/channels")
@RequiredArgsConstructor
@Tag(name = "Campaign Channels", description = "Channels configuration for campaigns")
public class CampaignChannelController {

    private final CampaignChannelService campaignChannelService;

    @PostMapping
    @Operation(summary = "Create a channel for a campaign")
    public ResponseEntity<CampaignChannelResponse> create(
            @PathVariable Long campaignId, @Valid @RequestBody CampaignChannelRequest request) {
        CampaignChannelResponse response = campaignChannelService.create(campaignId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "List channels for a campaign")
    public ResponseEntity<List<CampaignChannelResponse>> getByCampaign(@PathVariable Long campaignId) {
        return ResponseEntity.ok(campaignChannelService.getByCampaign(campaignId));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "List channels for a campaign filtered by type")
    public ResponseEntity<List<CampaignChannelResponse>> getByCampaignAndType(
            @PathVariable Long campaignId, @PathVariable TypeCanal type) {
        return ResponseEntity.ok(campaignChannelService.getByCampaignAndType(campaignId, type));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a channel by id")
    public ResponseEntity<CampaignChannelResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(campaignChannelService.get(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a channel")
    public ResponseEntity<CampaignChannelResponse> update(
            @PathVariable Long id, @Valid @RequestBody CampaignChannelRequest request) {
        return ResponseEntity.ok(campaignChannelService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a channel")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        campaignChannelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
