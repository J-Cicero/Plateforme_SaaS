package com.saas.plateform.campaign.application.dtos.responses;

import com.saas.plateform.campaign.domain.enums.StatutEnvoi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SmsSendResponse {

    private Long id;
    private Long campaignId;
    private Long channelId;
    private Long contactId;
    private String phoneNumber;
    private String content;
    private StatutEnvoi statut;
    private LocalDateTime sentAt;
    private String messageErreur;
}
