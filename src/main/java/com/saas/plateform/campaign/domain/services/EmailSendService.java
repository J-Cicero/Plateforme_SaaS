package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.requests.EmailSendRequest;
import com.saas.plateform.campaign.application.dtos.responses.EmailSendResponse;

public interface EmailSendService {

    EmailSendResponse sendEmail(EmailSendRequest request);
}
