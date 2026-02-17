package com.saas.plateform.Shared.security.mailling.api;

import com.saas.plateform.Shared.security.mailling.dto.request.EmailRequest;
import com.saas.plateform.Shared.security.mailling.dto.response.EmailResponse;
import com.saas.plateform.Shared.security.mailling.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mailing/")
public class SendMailApi {

    private final EmailService emailService;

    public SendMailApi(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("send")
    @Operation(
            summary = "Send an email using the specified template",
            description =
                    "This endpoint sends an email using a specified template. A valid EmailRequest object must be provided.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Email sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload"),
                    @ApiResponse(responseCode = "500", description = "Error while sending the email")
            })
    public ResponseEntity<EmailResponse> sendEmail(
            @RequestBody EmailRequest request, @RequestParam String template) {
        try {
            EmailResponse response = emailService.send(request, template);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {

            return ResponseEntity.status(500).body(new EmailResponse());
        }
    }
}

