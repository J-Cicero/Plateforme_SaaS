package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.ContactSegment;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.models.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactSegmentRepository extends JpaRepository<ContactSegment, Long> {
    
    Optional<ContactSegment> findByTrackingId(UUID trackingId);
    
    List<ContactSegment> findByContact(Contact contact);
    
    List<ContactSegment> findBySegment(Segment segment);
    
    Optional<ContactSegment> findByContactAndSegment(Contact contact, Segment segment);
    
    boolean existsByContactAndSegment(Contact contact, Segment segment);
    
    void deleteByContactAndSegment(Contact contact, Segment segment);
}
