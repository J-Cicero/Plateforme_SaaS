package com.saas.plateform.contact.domain.models;

import com.saas.plateform.contact.domain.enums.TypeConsentement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consentements_rgpd", 
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_contact_type", columnNames = {"contact_id", "type"})
    },
    indexes = {
        @Index(name = "idx_consentement_uuid", columnList = "trackingId"),
        @Index(name = "idx_consentement_contact", columnList = "contact_id"),
        @Index(name = "idx_consentement_type", columnList = "type")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentementRGPD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TypeConsentement type;

    @Builder.Default
    @Column(nullable = false)
    private Boolean accepted = false;

    @Column(nullable = false)
    private LocalDateTime consentDate;

    @Column(length = 45)
    private String ipAddress;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
        if (this.consentDate == null) {
            this.consentDate = LocalDateTime.now();
        }
    }
}
