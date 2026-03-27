package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.campaign.domain.enums.StatutCampaign;
import com.saas.plateform.campaign.domain.enums.TypeCampaign;
import com.saas.plateform.campaign.domain.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByStatus(StatutCampaign status);

    List<Campaign> findByType(TypeCampaign type);

    List<Campaign> findByProprietaire(User proprietaire);
}
