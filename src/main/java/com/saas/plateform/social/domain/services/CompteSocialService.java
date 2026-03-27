package com.saas.plateform.social.domain.services;

import com.saas.plateform.social.domain.models.CompteSocial;
import com.saas.plateform.social.infrastructure.repositories.CompteSocialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class CompteSocialService {

    private final CompteSocialRepository compteSocialRepository;

    public CompteSocial creer(CompteSocial compteSocial) {
        return compteSocialRepository.save(compteSocial);
    }

    @Transactional(readOnly = true)
    public Optional<CompteSocial> trouverParTrackingId(UUID trackingId) {
        return compteSocialRepository.findByTrackingId(trackingId);
    }

    @Transactional(readOnly = true)
    public List<CompteSocial> lister() {
        return compteSocialRepository.findAll();
    }
}
