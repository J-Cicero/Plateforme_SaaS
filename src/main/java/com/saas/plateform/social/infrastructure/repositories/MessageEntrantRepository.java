package com.saas.plateform.social.infrastructure.repositories;

import com.saas.plateform.social.domain.models.MessageEntrant;
import com.saas.plateform.social.domain.models.CompteSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageEntrantRepository extends JpaRepository<MessageEntrant, Long> {

    Optional<MessageEntrant> findByTrackingId(UUID trackingId);

    List<MessageEntrant> findByCompteSocial(CompteSocial compteSocial);

    List<MessageEntrant> findByCompteSocialAndLuFalse(CompteSocial compteSocial);
}
