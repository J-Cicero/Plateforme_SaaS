package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "campaign_budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal montantTotal;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal montantDepense = BigDecimal.ZERO;

    @Column(length = 10, nullable = false)
    private String devise;

    @Column(precision = 19, scale = 2)
    private BigDecimal alerteSeuil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;
}
