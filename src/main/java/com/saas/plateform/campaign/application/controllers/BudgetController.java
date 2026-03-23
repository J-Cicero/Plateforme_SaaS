package com.saas.plateform.campaign.application.controllers;

import com.saas.plateform.campaign.application.dtos.requests.BudgetRequest;
import com.saas.plateform.campaign.application.dtos.responses.BudgetResponse;
import com.saas.plateform.campaign.domain.services.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaigns/{campaignId}/budget")
@RequiredArgsConstructor
@Tag(name = "Campaign Budget", description = "Budget APIs for campaigns")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "Create or update budget for a campaign")
    public ResponseEntity<BudgetResponse> createOrUpdate(
            @PathVariable Long campaignId, @Valid @RequestBody BudgetRequest request) {
        BudgetResponse response = budgetService.createOrUpdate(campaignId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get budget for a campaign")
    public ResponseEntity<BudgetResponse> get(@PathVariable Long campaignId) {
        return ResponseEntity.ok(budgetService.getByCampaign(campaignId));
    }

    @DeleteMapping
    @Operation(summary = "Delete budget for a campaign")
    public ResponseEntity<Void> delete(@PathVariable Long campaignId) {
        budgetService.deleteByCampaign(campaignId);
        return ResponseEntity.noContent().build();
    }
}
