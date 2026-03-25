package com.saas.plateform.contact.application.controllers;

import com.saas.plateform.contact.domain.services.ContactService;
import com.saas.plateform.contact.application.dtos.requests.ContactRequest;
import com.saas.plateform.contact.application.dtos.responses.ContactResponse;
import com.saas.plateform.contact.domain.enums.StatutContact;
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
@RequestMapping("/contacts")
@RequiredArgsConstructor
@Tag(name = "Contacts", description = "Contact management APIs")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @Operation(summary = "Create a new contact")
    public ResponseEntity<ContactResponse> createContact(
            @Valid @RequestBody ContactRequest request,
            @RequestParam UUID ownerTrackingId) {
        ContactResponse response = contactService.createContact(request, ownerTrackingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Get contact by tracking ID")
    public ResponseEntity<ContactResponse> getContact(@PathVariable UUID trackingId) {
        ContactResponse response = contactService.getContactByTrackingId(trackingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/owner/{ownerTrackingId}")
    @Operation(summary = "Get all contacts by owner")
    public ResponseEntity<List<ContactResponse>> getContactsByOwner(@PathVariable UUID ownerTrackingId) {
        List<ContactResponse> responses = contactService.getAllContactsByOwner(ownerTrackingId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/owner/{ownerTrackingId}/active")
    @Operation(summary = "Get active contacts by owner")
    public ResponseEntity<List<ContactResponse>> getActiveContactsByOwner(@PathVariable UUID ownerTrackingId) {
        List<ContactResponse> responses = contactService.getActiveContactsByOwner(ownerTrackingId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get contacts by status")
    public ResponseEntity<List<ContactResponse>> getContactsByStatus(@PathVariable StatutContact status) {
        List<ContactResponse> responses = contactService.getContactsByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{trackingId}")
    @Operation(summary = "Update a contact")
    public ResponseEntity<ContactResponse> updateContact(
            @PathVariable UUID trackingId,
            @Valid @RequestBody ContactRequest request) {
        ContactResponse response = contactService.updateContact(trackingId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{trackingId}/archive")
    @Operation(summary = "Archive a contact")
    public ResponseEntity<Void> archiveContact(@PathVariable UUID trackingId) {
        contactService.archiveContact(trackingId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{trackingId}")
    @Operation(summary = "Delete a contact")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID trackingId) {
        contactService.deleteContact(trackingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{contactTrackingId}/segments/{segmentTrackingId}")
    @Operation(summary = "Add contact to segment")
    public ResponseEntity<Void> addContactToSegment(
            @PathVariable UUID contactTrackingId,
            @PathVariable UUID segmentTrackingId) {
        contactService.addContactToSegment(contactTrackingId, segmentTrackingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{contactTrackingId}/segments/{segmentTrackingId}")
    @Operation(summary = "Remove contact from segment")
    public ResponseEntity<Void> removeContactFromSegment(
            @PathVariable UUID contactTrackingId,
            @PathVariable UUID segmentTrackingId) {
        contactService.removeContactFromSegment(contactTrackingId, segmentTrackingId);
        return ResponseEntity.noContent().build();
    }
}
