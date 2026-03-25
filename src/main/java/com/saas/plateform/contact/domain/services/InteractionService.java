package com.saas.plateform.contact.domain.services;

import com.saas.plateform.contact.domain.enums.CanalSource;
import com.saas.plateform.contact.domain.enums.TypeInteraction;

import java.util.UUID;

public interface InteractionService {

    void recordInteraction(UUID contactTrackingId, TypeInteraction type, CanalSource source, String details);
}
