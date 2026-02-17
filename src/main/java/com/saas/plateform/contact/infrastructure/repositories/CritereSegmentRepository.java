package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.CritereSegment;
import com.saas.plateform.contact.domain.models.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CritereSegmentRepository extends JpaRepository<CritereSegment, Long> {
    
    Optional<CritereSegment> findByTrackingId(UUID trackingId);
    
    List<CritereSegment> findBySegmentOrderByOrderIndexAsc(Segment segment);
}
