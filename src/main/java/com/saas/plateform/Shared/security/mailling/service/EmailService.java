package com.saas.plateform.Shared.security.mailling.service;

import com.saas.plateform.Shared.security.mailling.dto.request.EmailRequest;
import com.saas.plateform.Shared.security.mailling.dto.response.EmailResponse;

public interface EmailService {
    EmailResponse send(EmailRequest request, String template);
}
