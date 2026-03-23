package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.campaign.domain.enums.TypeEvenement;
import com.saas.plateform.contact.domain.models.Contact;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "campaign_email_tracking_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailTrackingEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_send_id", nullable = false)
    private EmailSend emailSend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TypeEvenement type;

    @Column(length = 500)
    private String urlCliquee;

    @Column(length = 45)
    private String ipAdresse;

    @Column(length = 255)
    private String userAgent;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
