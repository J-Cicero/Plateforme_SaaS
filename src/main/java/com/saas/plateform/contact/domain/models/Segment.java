package com.saas.plateform.contact.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.contact.domain.enums.TypeSegment;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "segments", indexes = {
    @Index(name = "idx_segment_uuid", columnList = "trackingId"),
    @Index(name = "idx_segment_creator", columnList = "creator_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Segment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TypeSegment type;

    @Builder.Default
    @Column(nullable = false)
    private Boolean dynamic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
