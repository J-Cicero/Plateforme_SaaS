package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.campaign.domain.enums.StatutEnvoi;
import com.saas.plateform.contact.domain.models.Contact;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "campaign_email_sends",
        indexes = {
                @Index(name = "idx_email_send_status", columnList = "statut"),
                @Index(name = "idx_email_send_contact", columnList = "contact_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private CampaignChannel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Builder.Default
    private StatutEnvoi statut = StatutEnvoi.EN_ATTENTE;

    private LocalDateTime sentAt;

    private LocalDateTime deliveredAt;

    @Column(length = 500)
    private String messageErreur;
}
