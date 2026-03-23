package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.responses.EmailSendResponse;
import com.saas.plateform.campaign.domain.models.EmailSend;
import org.springframework.stereotype.Component;

@Component
public class EmailSendMapper {

    public EmailSendResponse toResponse(EmailSend emailSend) {
        return EmailSendResponse.builder()
                .id(emailSend.getId())
                .campaignId(emailSend.getCampaign() != null ? emailSend.getCampaign().getId() : null)
                .channelId(emailSend.getChannel() != null ? emailSend.getChannel().getId() : null)
                .contactId(emailSend.getContact() != null ? emailSend.getContact().getId() : null)
                .statut(emailSend.getStatut())
                .sentAt(emailSend.getSentAt())
                .deliveredAt(emailSend.getDeliveredAt())
                .messageErreur(emailSend.getMessageErreur())
                .build();
    }
}
