package com.saas.plateform.campaign.application.mappers;

import com.saas.plateform.campaign.application.dtos.responses.SmsSendResponse;
import com.saas.plateform.campaign.domain.models.SmsSend;
import org.springframework.stereotype.Component;

@Component
public class SmsSendMapper {

    public SmsSendResponse toResponse(SmsSend smsSend) {
        return SmsSendResponse.builder()
                .id(smsSend.getId())
                .campaignId(smsSend.getCampaign() != null ? smsSend.getCampaign().getId() : null)
                .channelId(smsSend.getChannel() != null ? smsSend.getChannel().getId() : null)
                .contactId(smsSend.getContact() != null ? smsSend.getContact().getId() : null)
                .phoneNumber(smsSend.getPhoneNumber())
                .content(smsSend.getContent())
                .statut(smsSend.getStatut())
                .sentAt(smsSend.getSentAt())
                .messageErreur(smsSend.getMessageErreur())
                .build();
    }
}
