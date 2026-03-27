package com.saas.plateform.social.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "statistiques_sociales", indexes = {
    @Index(name = "idx_statistique_uuid", columnList = "trackingId"),
    @Index(name = "idx_statistique_publication", columnList = "publication_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatistiqueSociale extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreVues = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreLikes = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombrePartages = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreCommentaires = 0;

    @Column(nullable = false)
    private LocalDateTime dateCalcul;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", nullable = false, unique = true)
    private PublicationSociale publication;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
