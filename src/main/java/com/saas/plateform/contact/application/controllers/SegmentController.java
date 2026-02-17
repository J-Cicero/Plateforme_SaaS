package com.saas.plateform.contact.application.controllers;

import com.saas.plateform.contact.domain.services.SegmentService;
import com.saas.plateform.contact.application.dtos.requests.SegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.SegmentResponse;
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
@RequestMapping("/segments")
@RequiredArgsConstructor
@Tag(name = "Segments", description = "Segment management APIs")
public class SegmentController {

    private final SegmentService segmentService;

    @PostMapping
    @Operation(summary = "Create a new segment")
    public ResponseEntity<SegmentResponse> createSegment(
            @Valid @RequestBody SegmentRequest request,
            @RequestParam UUID creatorTrackingId) {
        SegmentResponse response = segmentService.createSegment(request, creatorTrackingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Get segment by tracking ID")
    public ResponseEntity<SegmentResponse> getSegment(@PathVariable UUID trackingId) {
        SegmentResponse response = segmentService.getSegmentByTrackingId(trackingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all segments")
    public ResponseEntity<List<SegmentResponse>> getAllSegments() {
        List<SegmentResponse> responses = segmentService.getAllSegments();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/creator/{creatorTrackingId}")
    @Operation(summary = "Get segments by creator")
    public ResponseEntity<List<SegmentResponse>> getSegmentsByCreator(@PathVariable UUID creatorTrackingId) {
        List<SegmentResponse> responses = segmentService.getSegmentsByCreator(creatorTrackingId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/dynamic")
    @Operation(summary = "Get all dynamic segments")
    public ResponseEntity<List<SegmentResponse>> getDynamicSegments() {
        List<SegmentResponse> responses = segmentService.getDynamicSegments();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{trackingId}")
    @Operation(summary = "Update a segment")
    public ResponseEntity<SegmentResponse> updateSegment(
            @PathVariable UUID trackingId,
            @Valid @RequestBody SegmentRequest request) {
        SegmentResponse response = segmentService.updateSegment(trackingId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trackingId}")
    @Operation(summary = "Delete a segment")
    public ResponseEntity<Void> deleteSegment(@PathVariable UUID trackingId) {
        segmentService.deleteSegment(trackingId);
        return ResponseEntity.noContent().build();
    }
}
