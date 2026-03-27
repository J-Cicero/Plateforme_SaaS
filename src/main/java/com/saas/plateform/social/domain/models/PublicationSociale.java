package com.saas.plateform.social.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.social.domain.enums.StatutPublication;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "publications_sociales", indexes = {
    @Index(name = "idx_publication_uuid", columnList = "trackingId"),
    @Index(name = "idx_publication_compte", columnList = "compte_social_id"),
    @Index(name = "idx_publication_createur", columnList = "createur_id"),
    @Index(name = "idx_publication_statut", columnList = "statut")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationSociale extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(length = 500)
    private String mediaUrl;

    @Column
    private LocalDateTime dateProgrammee;

    @Column
    private LocalDateTime datePublication;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatutPublication statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_social_id", nullable = false)
    private CompteSocial compteSocial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_id", nullable = false)
    private User createur;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
