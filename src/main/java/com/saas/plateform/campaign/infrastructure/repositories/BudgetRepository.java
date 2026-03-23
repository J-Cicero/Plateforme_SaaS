package com.saas.plateform.campaign.infrastructure.repositories;

import com.saas.plateform.campaign.domain.models.Budget;
import com.saas.plateform.campaign.domain.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByCampaign(Campaign campaign);
}
