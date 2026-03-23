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
        HttpStatus status = response.getStatut() == StatutEnvoi.ECHOUE
                ? HttpStatus.BAD_REQUEST
                : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(response);
    }
}
