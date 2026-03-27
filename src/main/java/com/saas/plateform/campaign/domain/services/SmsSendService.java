package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.application.dtos.responses.SmsSendResponse;
import com.saas.plateform.campaign.domain.models.SmsSend;

public interface SmsSendService {

    /**
     * Envoie un SMS via un canal de campagne à un contact
     * @param campaignId ID de la campagne
     * @param channelId ID du canal SMS
     * @param contactId ID du contact destinataire
     * @return SmsSendResponse avec le statut d'envoi
     */
    SmsSendResponse sendSms(Long campaignId, Long channelId, Long contactId);

    /**
     * Récupère un SmsSend par son ID
     * @param smsSendId ID du SmsSend
     * @return le SmsSend trouvé
     */
    SmsSend getSmsSend(Long smsSendId);
}
