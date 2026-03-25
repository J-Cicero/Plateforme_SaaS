package com.saas.plateform.contact.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.contact.domain.enums.StatutContact;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "contacts", indexes = {
    @Index(name = "idx_contact_email", columnList = "email"),
    @Index(name = "idx_contact_uuid", columnList = "trackingId"),
    @Index(name = "idx_contact_owner", columnList = "owner_id"),
    @Index(name = "idx_contact_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String company;

    @Column(length = 100)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutContact status;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Builder.Default
    @Column(nullable = false)
    private Integer leadScore = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean archived = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
