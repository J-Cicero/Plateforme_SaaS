package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.contact.domain.models.Segment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campaign_audiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignAudience extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id", nullable = false)
    private Segment segment;

    @Lob
    private String filtresJson;
}
