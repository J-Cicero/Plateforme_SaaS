package com.saas.plateform.contact.domain.models;

import com.saas.plateform.contact.domain.enums.OperateurComparaison;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "criteres_segment", indexes = {
    @Index(name = "idx_critere_uuid", columnList = "trackingId"),
    @Index(name = "idx_critere_segment", columnList = "segment_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CritereSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id", nullable = false)
    private Segment segment;

    @Column(nullable = false, length = 100)
    private String field;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OperateurComparaison operator;

    @Column(length = 255)
    private String value;

    @Builder.Default
    @Column(nullable = false)
    private Integer orderIndex = 0;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
