package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "campaign_results",
        indexes = {
                @Index(name = "idx_result_campaign", columnList = "campaign_id"),
                @Index(name = "idx_result_canal", columnList = "canal")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultatCampagne extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36, unique = true, updatable = false)
    @Builder.Default
    private String trackingId = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeCanal canal;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreEnvoyes = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreOuverts = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreCliques = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreConversions = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer nombreEchecs = 0;

    private LocalDateTime dateCalcul;
}
