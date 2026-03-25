package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignChannel;
import com.saas.plateform.campaign.domain.models.SmsSend;
import com.saas.plateform.contact.domain.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsSendRepository extends JpaRepository<SmsSend, Long> {

    List<SmsSend> findByCampaign(Campaign campaign);

    List<SmsSend> findByChannel(CampaignChannel channel);

    List<SmsSend> findByContact(Contact contact);
}
