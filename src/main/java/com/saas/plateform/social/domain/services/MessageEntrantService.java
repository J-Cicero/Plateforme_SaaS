package com.saas.plateform.social.domain.services;

import com.saas.plateform.social.domain.models.MessageEntrant;
import com.saas.plateform.social.infrastructure.repositories.MessageEntrantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class MessageEntrantService {

    private final MessageEntrantRepository messageEntrantRepository;

    public MessageEntrant creer(MessageEntrant messageEntrant) {
        return messageEntrantRepository.save(messageEntrant);
    }

    @Transactional(readOnly = true)
    public Optional<MessageEntrant> trouverParTrackingId(UUID trackingId) {
        return messageEntrantRepository.findByTrackingId(trackingId);
    }

    @Transactional(readOnly = true)
    public List<MessageEntrant> lister() {
        return messageEntrantRepository.findAll();
    }
}
