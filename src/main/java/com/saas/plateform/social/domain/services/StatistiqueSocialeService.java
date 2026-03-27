package com.saas.plateform.social.domain.services;

import com.saas.plateform.social.domain.models.StatistiqueSociale;
import com.saas.plateform.social.infrastructure.repositories.StatistiqueSocialeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class StatistiqueSocialeService {

    private final StatistiqueSocialeRepository statistiqueSocialeRepository;

    public StatistiqueSociale creer(StatistiqueSociale statistiqueSociale) {
        return statistiqueSocialeRepository.save(statistiqueSociale);
    }

    @Transactional(readOnly = true)
    public Optional<StatistiqueSociale> trouverParTrackingId(UUID trackingId) {
        return statistiqueSocialeRepository.findByTrackingId(trackingId);
    }

    @Transactional(readOnly = true)
    public List<StatistiqueSociale> lister() {
        return statistiqueSocialeRepository.findAll();
    }
}
