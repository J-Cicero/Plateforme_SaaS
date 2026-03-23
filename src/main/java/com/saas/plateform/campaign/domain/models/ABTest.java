package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.campaign.domain.enums.CritereGagnant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campaign_ab_tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ABTest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(length = 100, nullable = false)
    private String nomVarianteA;

    @Column(length = 100, nullable = false)
    private String nomVarianteB;

    @Column(length = 150, nullable = false)
    private String templateNameA;

    @Column(length = 150, nullable = false)
    private String templateNameB;

    @Column(nullable = false)
    private Integer pourcentageA;

    @Column(nullable = false)
    private Integer pourcentageB;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private CritereGagnant critereGagnant;

    @Column(length = 20)
    private String gagnant;

    @Column(length = 30)
    private String statut;
}
