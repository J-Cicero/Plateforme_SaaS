package com.saas.plateform.social.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.social.domain.enums.TypePlateforme;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages_entrants", indexes = {
    @Index(name = "idx_message_uuid", columnList = "trackingId"),
    @Index(name = "idx_message_compte", columnList = "compte_social_id"),
    @Index(name = "idx_message_lu", columnList = "lu")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntrant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Column(nullable = false, length = 255)
    private String expediteur;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(nullable = false)
    private LocalDateTime dateReception;

    @Builder.Default
    @Column(nullable = false)
    private Boolean lu = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TypePlateforme plateforme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_social_id", nullable = false)
    private CompteSocial compteSocial;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
