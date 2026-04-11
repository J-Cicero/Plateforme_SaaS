package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.requests.EmailSendRequest;
import com.saas.plateform.campaign.application.dtos.responses.EmailSendResponse;
import com.saas.plateform.campaign.domain.enums.StatutEnvoi;

import java.util.List;
import java.util.Optional;

public interface EmailSendService {

    EmailSendResponse sendEmail(EmailSendRequest request);

    Optional<EmailSendResponse> getEmailSendById(Long id);

    List<EmailSendResponse> getEmailSendsByCampaign(Long campaignId);

    List<EmailSendResponse> getEmailSendsByStatut(StatutEnvoi statut);
}
