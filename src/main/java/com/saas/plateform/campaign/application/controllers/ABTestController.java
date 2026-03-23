package com.saas.plateform.campaign.application.controllers;

import com.saas.plateform.campaign.application.dtos.requests.ABTestRequest;
import com.saas.plateform.campaign.application.dtos.responses.ABTestResponse;
import com.saas.plateform.campaign.domain.services.ABTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns/{campaignId}/ab-tests")
@RequiredArgsConstructor
@Tag(name = "AB Tests", description = "A/B testing for campaigns")
public class ABTestController {

    private final ABTestService abTestService;

    @PostMapping
    @Operation(summary = "Create an A/B test for a campaign")
    public ResponseEntity<ABTestResponse> create(
            @PathVariable Long campaignId, @Valid @RequestBody ABTestRequest request) {
        ABTestResponse response = abTestService.create(campaignId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "List A/B tests for a campaign")
    public ResponseEntity<List<ABTestResponse>> getByCampaign(@PathVariable Long campaignId) {
        return ResponseEntity.ok(abTestService.getByCampaign(campaignId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an A/B test by id")
    public ResponseEntity<ABTestResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(abTestService.get(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an A/B test")
    public ResponseEntity<ABTestResponse> update(
            @PathVariable Long id, @Valid @RequestBody ABTestRequest request) {
        return ResponseEntity.ok(abTestService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an A/B test")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        abTestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
