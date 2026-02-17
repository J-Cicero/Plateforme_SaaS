package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.Segment;
import com.saas.plateform.Shared.security.user.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
    
    Optional<Segment> findByTrackingId(UUID trackingId);
    
    List<Segment> findByCreator(User creator);
    
    List<Segment> findByDynamic(Boolean dynamic);
}
