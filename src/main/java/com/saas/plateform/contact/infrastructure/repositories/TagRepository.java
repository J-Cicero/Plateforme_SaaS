package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findByTrackingId(UUID trackingId);

}
