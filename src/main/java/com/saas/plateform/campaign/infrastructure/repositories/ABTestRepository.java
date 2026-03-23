package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.models.ABTest;
import com.saas.plateform.campaign.domain.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ABTestRepository extends JpaRepository<ABTest, Long> {

    List<ABTest> findByCampaign(Campaign campaign);
}
