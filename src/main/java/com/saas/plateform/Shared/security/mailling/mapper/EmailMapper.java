package com.saas.plateform.Shared.security.mailling.mapper;

import com.saas.plateform.Shared.security.mailling.dto.request.EmailRequest;
import com.saas.plateform.Shared.security.mailling.dto.response.EmailResponse;
import com.saas.plateform.Shared.security.mailling.entity.Email;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailMapper {
    public EmailResponse toDto(Email email) {
        EmailResponse response = new EmailResponse();
        BeanUtils.copyProperties(email, response);
        return response;
    }

    public Email toEntity(EmailRequest request) {
        Email email = new Email();
        BeanUtils.copyProperties(request, email);
        return email;
    }

    public Email toEntity(EmailRequest request, Email email) {
        BeanUtils.copyProperties(request, email);
        return email;
    }
}
