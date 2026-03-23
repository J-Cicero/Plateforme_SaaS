package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.UnsubscribeList;
import com.saas.plateform.contact.domain.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnsubscribeListRepository extends JpaRepository<UnsubscribeList, Long> {

    Optional<UnsubscribeList> findByContactAndCampaign(Contact contact, Campaign campaign);

    List<UnsubscribeList> findByCampaign(Campaign campaign);

    List<UnsubscribeList> findByContact(Contact contact);
}
