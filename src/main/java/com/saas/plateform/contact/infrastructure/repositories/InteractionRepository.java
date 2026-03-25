package com.saas.plateform.contact.infrastructure.repositories;

import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.domain.models.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    List<Interaction> findByContact(Contact contact);
}
