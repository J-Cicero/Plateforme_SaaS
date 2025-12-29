package com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.service;

import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.dto.request.EmailRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.dto.response.EmailResponse;

public interface EmailService {
    EmailResponse send(EmailRequest request, String template);
}
