package com.saas.plateform.campaign.application.controllers;

import com.saas.plateform.campaign.application.dtos.requests.EmailSendRequest;
import com.saas.plateform.campaign.application.dtos.responses.EmailSendResponse;
import com.saas.plateform.campaign.domain.enums.StatutEnvoi;
import com.saas.plateform.campaign.domain.services.EmailSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns/email-sends")
@RequiredArgsConstructor
@Tag(name = "Email Sends", description = "Email sending APIs for campaigns")
public class EmailSendController {

    private final EmailSendService emailSendService;

    @PostMapping
    @Operation(summary = "Send an email for a campaign channel to a contact")
    public ResponseEntity<EmailSendResponse> sendEmail(@Valid @RequestBody EmailSendRequest request) {
        EmailSendResponse response = emailSendService.sendEmail(request);
        HttpStatus status = (response.getStatut() == StatutEnvoi.ECHOUE
                || response.getStatut() == StatutEnvoi.ECHEC)
                ? HttpStatus.BAD_REQUEST
                : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of an email send")
    public ResponseEntity<EmailSendResponse> getEmailSend(@PathVariable Long id) {
        return emailSendService.getEmailSendById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/campaign/{campaignId}")
    @Operation(summary = "Get all email sends for a campaign")
    public ResponseEntity<List<EmailSendResponse>> getEmailSendsByCampaign(@PathVariable Long campaignId) {
        List<EmailSendResponse> responses = emailSendService.getEmailSendsByCampaign(campaignId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Get email sends filtered by status")
    public ResponseEntity<List<EmailSendResponse>> getEmailSendsByStatut(@PathVariable StatutEnvoi statut) {
        List<EmailSendResponse> responses = emailSendService.getEmailSendsByStatut(statut);
        return ResponseEntity.ok(responses);
    }
}
