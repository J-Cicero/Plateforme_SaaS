package com.saas.plateform.contact.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contact_tag",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_contact_tag", columnNames = {"contact_id", "tag_id"})
    },
    indexes = {
        @Index(name = "idx_contact_tag_uuid", columnList = "trackingId"),
        @Index(name = "idx_contact_tag_contact", columnList = "contact_id"),
        @Index(name = "idx_contact_tag_tag", columnList = "tag_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedDate;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
        if (this.addedDate == null) {
            this.addedDate = LocalDateTime.now();
        }
    }
}
