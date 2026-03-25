package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.ResultatCampagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultatCampagneRepository extends JpaRepository<ResultatCampagne, Long> {

    Optional<ResultatCampagne> findByCampaignAndCanal(Campaign campaign, TypeCanal canal);
}
