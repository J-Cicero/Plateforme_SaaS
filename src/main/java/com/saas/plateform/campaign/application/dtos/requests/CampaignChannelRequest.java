package com.saas.plateform.campaign.application.dtos.requests;

import com.saas.plateform.campaign.domain.enums.TypeCanal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignChannelRequest {

    @NotNull
    private TypeCanal typeCanal;

    @NotBlank
    private String templateName;

    private String sujet;

    @Email
    private String senderEmail;

    private String configJson;

    private Boolean actif;
}
