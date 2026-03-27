package com.saas.plateform.social.infrastructure.repositories;

import com.saas.plateform.social.domain.models.CompteSocial;
import com.saas.plateform.Shared.security.user.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompteSocialRepository extends JpaRepository<CompteSocial, Long> {

    Optional<CompteSocial> findByTrackingId(UUID trackingId);

    List<CompteSocial> findByProprietaire(User proprietaire);

    List<CompteSocial> findByProprietaireAndActif(User proprietaire, Boolean actif);

    Optional<CompteSocial> findByNomCompte(String nomCompte);
}
