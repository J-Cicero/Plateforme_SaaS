package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.ContactTag;
import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactTagRepository extends JpaRepository<ContactTag, Long> {
    
    Optional<ContactTag> findByTrackingId(UUID trackingId);
    
    List<ContactTag> findByContact(Contact contact);
    
    List<ContactTag> findByTag(Tag tag);
    
    Optional<ContactTag> findByContactAndTag(Contact contact, Tag tag);
    
    boolean existsByContactAndTag(Contact contact, Tag tag);
    
    void deleteByContactAndTag(Contact contact, Tag tag);
}
