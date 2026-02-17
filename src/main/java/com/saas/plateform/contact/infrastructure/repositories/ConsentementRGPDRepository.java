package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.ConsentementRGPD;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.enums.TypeConsentement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsentementRGPDRepository extends JpaRepository<ConsentementRGPD, Long> {
    
    Optional<ConsentementRGPD> findByTrackingId(UUID trackingId);
    
    List<ConsentementRGPD> findByContact(Contact contact);
    
    Optional<ConsentementRGPD> findByContactAndType(Contact contact, TypeConsentement type);
    
    List<ConsentementRGPD> findByContactAndAccepted(Contact contact, Boolean accepted);
}
