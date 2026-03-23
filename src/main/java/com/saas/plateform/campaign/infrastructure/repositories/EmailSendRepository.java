package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.enums.StatutEnvoi;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignChannel;
import com.saas.plateform.campaign.domain.models.EmailSend;
import com.saas.plateform.contact.domain.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailSendRepository extends JpaRepository<EmailSend, Long> {

    List<EmailSend> findByCampaign(Campaign campaign);

    List<EmailSend> findByChannel(CampaignChannel channel);

    List<EmailSend> findByContact(Contact contact);

    List<EmailSend> findByStatut(StatutEnvoi statut);
}
