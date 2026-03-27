package com.saas.plateform.social.domain.services;

import com.saas.plateform.social.domain.models.PublicationSociale;
import com.saas.plateform.social.infrastructure.repositories.PublicationSocialeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class PublicationSocialeService {

    private final PublicationSocialeRepository publicationSocialeRepository;

    public PublicationSociale creer(PublicationSociale publicationSociale) {
        return publicationSocialeRepository.save(publicationSociale);
    }

    @Transactional(readOnly = true)
    public Optional<PublicationSociale> trouverParTrackingId(UUID trackingId) {
        return publicationSocialeRepository.findByTrackingId(trackingId);
    }

    @Transactional(readOnly = true)
    public List<PublicationSociale> lister() {
        return publicationSocialeRepository.findAll();
    }
}
