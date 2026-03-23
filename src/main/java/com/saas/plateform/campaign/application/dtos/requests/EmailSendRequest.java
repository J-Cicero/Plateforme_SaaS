package com.saas.plateform.campaign.application.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSendRequest {

    @NotNull
    private Long campaignId;

    @NotNull
    private Long channelId;

    @NotNull
    private Long contactId;
}
