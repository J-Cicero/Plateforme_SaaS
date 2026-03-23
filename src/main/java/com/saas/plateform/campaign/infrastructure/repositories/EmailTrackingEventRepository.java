package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.enums.TypeEvenement;
import com.saas.plateform.campaign.domain.models.EmailSend;
import com.saas.plateform.campaign.domain.models.EmailTrackingEvent;
import com.saas.plateform.contact.domain.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailTrackingEventRepository extends JpaRepository<EmailTrackingEvent, Long> {

    List<EmailTrackingEvent> findByEmailSend(EmailSend emailSend);

    List<EmailTrackingEvent> findByContact(Contact contact);

    List<EmailTrackingEvent> findByType(TypeEvenement type);
}
