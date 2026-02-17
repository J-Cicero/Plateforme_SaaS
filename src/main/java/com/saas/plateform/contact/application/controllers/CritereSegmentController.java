package com.saas.plateform.contact.application.controllers;

import com.saas.plateform.contact.domain.services.CritereSegmentService;
import com.saas.plateform.contact.application.dtos.requests.CritereSegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.CritereSegmentResponse;
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
@RequestMapping("/criteres")
@RequiredArgsConstructor
@Tag(name = "Critères Segment", description = "Segment criteria management APIs for dynamic segmentation")
public class CritereSegmentController {

    private final CritereSegmentService critereService;

    @PostMapping
    @Operation(summary = "Create a new segment criterion")
    public ResponseEntity<CritereSegmentResponse> createCritere(@Valid @RequestBody CritereSegmentRequest request) {
        CritereSegmentResponse response = critereService.createCritere(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Get criterion by tracking ID")
    public ResponseEntity<CritereSegmentResponse> getCritere(@PathVariable UUID trackingId) {
        CritereSegmentResponse response = critereService.getCritereByTrackingId(trackingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/segment/{segmentTrackingId}")
    @Operation(summary = "Get all criteria for a segment (ordered)")
    public ResponseEntity<List<CritereSegmentResponse>> getCriteresBySegment(@PathVariable UUID segmentTrackingId) {
        List<CritereSegmentResponse> responses = critereService.getCriteresBySegment(segmentTrackingId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{trackingId}")
    @Operation(summary = "Update a segment criterion")
    public ResponseEntity<CritereSegmentResponse> updateCritere(
            @PathVariable UUID trackingId,
            @Valid @RequestBody CritereSegmentRequest request) {
        CritereSegmentResponse response = critereService.updateCritere(trackingId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trackingId}")
    @Operation(summary = "Delete a segment criterion")
    public ResponseEntity<Void> deleteCritere(@PathVariable UUID trackingId) {
        critereService.deleteCritere(trackingId);
        return ResponseEntity.noContent().build();
    }
}
