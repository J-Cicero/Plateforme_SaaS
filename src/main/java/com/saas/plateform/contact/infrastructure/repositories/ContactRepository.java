package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.enums.StatutContact;
import com.saas.plateform.Shared.security.user.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    Optional<Contact> findByTrackingId(UUID trackingId);
    
    Optional<Contact> findByEmail(String email);
    
    List<Contact> findByOwner(User owner);
    
    List<Contact> findByStatus(StatutContact status);
    
    List<Contact> findByOwnerAndArchived(User owner, Boolean archived);
    
    boolean existsByEmail(String email);
}
