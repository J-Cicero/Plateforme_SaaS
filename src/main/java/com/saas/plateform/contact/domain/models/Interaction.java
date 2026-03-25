package com.saas.plateform.contact.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import com.saas.plateform.contact.domain.enums.CanalSource;
import com.saas.plateform.contact.domain.enums.TypeInteraction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "interactions",
        indexes = {
                @Index(name = "idx_interaction_contact", columnList = "contact_id"),
                @Index(name = "idx_interaction_type", columnList = "type")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TypeInteraction type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CanalSource source;

    @Column(length = 500)
    private String details;

    @Column(nullable = false)
    private LocalDateTime date;
}
