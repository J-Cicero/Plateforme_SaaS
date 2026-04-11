package com.saas.plateform.campaign.domain.services;

import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.ResultatCampagne;

import java.util.List;
import java.util.Optional;

public interface ResultatCampagneService {

    ResultatCampagne creer(Long campaignId, TypeCanal canal);

    List<ResultatCampagne> trouverParCampagne(Long campaignId);

    Optional<ResultatCampagne> trouverParCampagneEtCanal(Long campaignId, TypeCanal canal);

    void incrementerEnvoyes(Long campaignId, TypeCanal canal);

    void incrementerOuverts(Long campaignId, TypeCanal canal);

    void incrementerCliques(Long campaignId, TypeCanal canal);

    void incrementerConversions(Long campaignId, TypeCanal canal);

    void incrementerEchecs(Long campaignId, TypeCanal canal);
}
