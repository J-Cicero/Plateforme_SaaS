package com.saas.plateform.social.infrastructure.repositories;

import com.saas.plateform.social.domain.models.StatistiqueSociale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatistiqueSocialeRepository extends JpaRepository<StatistiqueSociale, Long> {

    Optional<StatistiqueSociale> findByTrackingId(UUID trackingId);

    Optional<StatistiqueSociale> findByPublicationId(Long publicationId);
}
