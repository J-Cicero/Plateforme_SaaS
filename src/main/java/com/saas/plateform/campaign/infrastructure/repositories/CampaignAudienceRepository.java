package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignAudience;
import com.saas.plateform.contact.domain.models.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignAudienceRepository extends JpaRepository<CampaignAudience, Long> {

    List<CampaignAudience> findByCampaign(Campaign campaign);

    List<CampaignAudience> findBySegment(Segment segment);
}
