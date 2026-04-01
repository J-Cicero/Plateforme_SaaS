package com.saas.plateform.Shared.security.mailling.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmailRequest implements Serializable {

    private String mailFrom;

    private String mailTo;

    private String mailCc;

    private String mailBcc;

    private String mailSubject;

    private String mailContent;

    private String lastName;

    private String entreprise;

    private String plan;

    private String username;

    private String password;

    private String lien;

    private String endDate;

    private String startDate;

    private String montant;

    private String contact;

    // Variables pour welcome.html
    private String firstName;
    private String registrationDate;

    // Variables pour campaign-notification.html
    private String campaignName;
    private String campaignDescription;
    private String campaignStartDate;
    private Integer recipientCount;

    // Variables pour password-reset.html
    private String resetLink;
    private Integer expiryMinutes;
    private String supportEmail;

    public EmailRequest() {}
}
