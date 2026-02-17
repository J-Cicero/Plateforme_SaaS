package com.saas.plateform.contact.application.controllers;

import com.saas.plateform.contact.domain.services.TagService;
import com.saas.plateform.contact.application.dtos.requests.TagRequest;
import com.saas.plateform.contact.application.dtos.responses.TagResponse;
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
@RequestMapping("/tags")
@RequiredArgsConstructor
@Tag(name = "Tags", description = "Tag management APIs")
public class TagController {

    private final TagService tagService;

    @PostMapping
    @Operation(summary = "Create a new tag")
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Get tag by tracking ID")
    public ResponseEntity<TagResponse> getTag(@PathVariable UUID trackingId) {
        TagResponse response = tagService.getTagByTrackingId(trackingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all tags")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<TagResponse> responses = tagService.getAllTags();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{trackingId}")
    @Operation(summary = "Update a tag")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable UUID trackingId,
            @Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.updateTag(trackingId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trackingId}")
    @Operation(summary = "Delete a tag")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID trackingId) {
        tagService.deleteTag(trackingId);
        return ResponseEntity.noContent().build();
    }
}
