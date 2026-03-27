package com.saas.plateform.social.infrastructure.repositories;

import com.saas.plateform.social.domain.models.PublicationSociale;
import com.saas.plateform.social.domain.models.CompteSocial;
import com.saas.plateform.social.domain.enums.StatutPublication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublicationSocialeRepository extends JpaRepository<PublicationSociale, Long> {

    Optional<PublicationSociale> findByTrackingId(UUID trackingId);

    List<PublicationSociale> findByStatut(StatutPublication statut);

    List<PublicationSociale> findByCompteSocial(CompteSocial compteSocial);

    List<PublicationSociale> findByCompteSocialAndStatut(CompteSocial compteSocial, StatutPublication statut);
}
