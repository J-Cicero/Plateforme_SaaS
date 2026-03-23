package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campaign_channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignChannel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TypeCanal typeCanal;

    @Column(nullable = false, length = 150)
    private String templateName;

    @Column(length = 200)
    private String sujet;

    @Column(length = 150)
    private String senderEmail;

    @Lob
    private String configJson;

    @Builder.Default
    @Column(nullable = false)
    private Boolean actif = true;
}
