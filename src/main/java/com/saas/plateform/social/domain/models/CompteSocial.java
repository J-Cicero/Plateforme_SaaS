package com.saas.plateform.social.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.social.domain.enums.TypePlateforme;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "comptes_sociaux", indexes = {
    @Index(name = "idx_compte_social_uuid", columnList = "trackingId"),
    @Index(name = "idx_compte_social_proprietaire", columnList = "proprietaire_id"),
    @Index(name = "idx_compte_social_plateforme", columnList = "plateforme")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteSocial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TypePlateforme plateforme;

    @Column(nullable = false, length = 255)
    private String nomCompte;

    @Column(length = 500)
    private String tokenAcces;

    @Builder.Default
    @Column(nullable = false)
    private Boolean actif = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietaire_id", nullable = false)
    private User proprietaire;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
