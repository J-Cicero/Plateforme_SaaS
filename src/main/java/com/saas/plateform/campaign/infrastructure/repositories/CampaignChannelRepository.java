package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignChannelRepository extends JpaRepository<CampaignChannel, Long> {

    List<CampaignChannel> findByCampaign(Campaign campaign);

    List<CampaignChannel> findByCampaignAndTypeCanal(Campaign campaign, TypeCanal typeCanal);
}
