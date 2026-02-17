package com.saas.plateform.contact.application.controllers;

import com.saas.plateform.contact.domain.services.ConsentementRGPDService;
import com.saas.plateform.contact.application.dtos.requests.ConsentementRGPDRequest;
import com.saas.plateform.contact.application.dtos.responses.ConsentementRGPDResponse;
import com.saas.plateform.contact.domain.enums.TypeConsentement;
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
@RequestMapping("/consentements")
@RequiredArgsConstructor
@Tag(name = "Consentements RGPD", description = "GDPR consent management APIs")
public class ConsentementRGPDController {

    private final ConsentementRGPDService consentementService;

    @PostMapping
    @Operation(summary = "Create a new GDPR consent")
    public ResponseEntity<ConsentementRGPDResponse> createConsentement(@Valid @RequestBody ConsentementRGPDRequest request) {
        ConsentementRGPDResponse response = consentementService.createConsentement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Get consent by tracking ID")
    public ResponseEntity<ConsentementRGPDResponse> getConsentement(@PathVariable UUID trackingId) {
        ConsentementRGPDResponse response = consentementService.getConsentementByTrackingId(trackingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contact/{contactTrackingId}")
    @Operation(summary = "Get all consents for a contact")
    public ResponseEntity<List<ConsentementRGPDResponse>> getConsentementsByContact(@PathVariable UUID contactTrackingId) {
        List<ConsentementRGPDResponse> responses = consentementService.getConsentementsByContact(contactTrackingId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/contact/{contactTrackingId}/accepted")
    @Operation(summary = "Get accepted consents for a contact")
    public ResponseEntity<List<ConsentementRGPDResponse>> getAcceptedConsentementsByContact(@PathVariable UUID contactTrackingId) {
        List<ConsentementRGPDResponse> responses = consentementService.getAcceptedConsentementsByContact(contactTrackingId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/contact/{contactTrackingId}/check/{type}")
    @Operation(summary = "Check if contact has accepted a specific consent type")
    public ResponseEntity<Boolean> hasConsentement(
            @PathVariable UUID contactTrackingId,
            @PathVariable TypeConsentement type) {
        boolean hasConsent = consentementService.hasConsentement(contactTrackingId, type);
        return ResponseEntity.ok(hasConsent);
    }

    @PutMapping("/{trackingId}")
    @Operation(summary = "Update a consent (change acceptance status)")
    public ResponseEntity<ConsentementRGPDResponse> updateConsentement(
            @PathVariable UUID trackingId,
            @Valid @RequestBody ConsentementRGPDRequest request) {
        ConsentementRGPDResponse response = consentementService.updateConsentement(trackingId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trackingId}")
    @Operation(summary = "Delete a consent")
    public ResponseEntity<Void> deleteConsentement(@PathVariable UUID trackingId) {
        consentementService.deleteConsentement(trackingId);
        return ResponseEntity.noContent().build();
    }
}
