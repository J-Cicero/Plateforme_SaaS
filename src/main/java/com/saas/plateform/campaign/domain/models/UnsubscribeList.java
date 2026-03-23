package com.saas.plateform.campaign.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.contact.domain.models.Contact;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "campaign_unsubscribe_list",
        indexes = {
                @Index(name = "idx_unsub_contact", columnList = "contact_id"),
                @Index(name = "idx_unsub_campaign", columnList = "campaign_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnsubscribeList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(length = 255)
    private String raison;

    @Column(nullable = false)
    private LocalDateTime unsubscribedAt;
}
