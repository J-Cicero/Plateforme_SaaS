package com.saas.plateform.contact.domain.models;

import com.saas.plateform.Shared.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tags", indexes = {
    @Index(name = "idx_tag_uuid", columnList = "trackingId"),
    @Index(name = "idx_tag_lastName", columnList = "lastName")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Column(nullable = false, unique = true, length = 100)
    private String lastName;

    @Column(length = 7)
    private String color;

    @Column(length = 500)
    private String description;

    @PrePersist
    public void generateTrackingId() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }
}
