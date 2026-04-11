package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.ResultatCampagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultatCampagneRepository extends JpaRepository<ResultatCampagne, Long> {

    Optional<ResultatCampagne> findByCampaignAndCanal(Campaign campaign, TypeCanal canal);

    List<ResultatCampagne> findByCampaign(Campaign campaign);

    @Query("SELECT r FROM ResultatCampagne r WHERE r.campaign.id = :campaignId")
    List<ResultatCampagne> findByCampaignId(@Param("campaignId") Long campaignId);

    @Query("SELECT r FROM ResultatCampagne r WHERE r.campaign.id = :campaignId AND r.canal = :canal")
    Optional<ResultatCampagne> findByCampaignIdAndCanal(@Param("campaignId") Long campaignId, @Param("canal") TypeCanal canal);
}
