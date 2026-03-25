package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.domain.enums.TypeEvenement;
import com.saas.plateform.campaign.domain.models.EmailTrackingEvent;

public interface EmailTrackingEventService {

    EmailTrackingEvent recordEvent(Long emailSendId,
                                   Long contactId,
                                   TypeEvenement type,
                                   String urlCliquee,
                                   String ipAdresse,
                                   String userAgent);
}
