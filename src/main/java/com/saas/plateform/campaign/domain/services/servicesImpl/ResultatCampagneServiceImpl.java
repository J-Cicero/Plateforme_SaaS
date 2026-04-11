package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.ResultatCampagne;
import com.saas.plateform.campaign.domain.services.ResultatCampagneService;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import com.saas.plateform.campaign.infrastructure.repositories.ResultatCampagneRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultatCampagneServiceImpl implements ResultatCampagneService {

    private static final Logger log = LoggerFactory.getLogger(ResultatCampagneServiceImpl.class);

    private final ResultatCampagneRepository resultatCampagneRepository;
    private final CampaignRepository campaignRepository;

    @Override
    public ResultatCampagne creer(Long campaignId, TypeCanal canal) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        // Vérifier si le résultat existe déjà
        Optional<ResultatCampagne> existe = resultatCampagneRepository.findByCampaignIdAndCanal(campaignId, canal);
        if (existe.isPresent()) {
            return existe.get();
        }

        // Créer un nouveau résultat
        ResultatCampagne resultat = ResultatCampagne.builder()
                .campaign(campaign)
                .canal(canal)
                .nombreEnvoyes(0)
                .nombreOuverts(0)
                .nombreCliques(0)
                .nombreConversions(0)
                .nombreEchecs(0)
                .dateCalcul(LocalDateTime.now())
                .build();

        ResultatCampagne saved = resultatCampagneRepository.save(resultat);
        log.info("Created new ResultatCampagne for campaign {} and canal {}", campaignId, canal);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultatCampagne> trouverParCampagne(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        return resultatCampagneRepository.findByCampaign(campaign);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResultatCampagne> trouverParCampagneEtCanal(Long campaignId, TypeCanal canal) {
        return resultatCampagneRepository.findByCampaignIdAndCanal(campaignId, canal);
    }

    @Override
    public void incrementerEnvoyes(Long campaignId, TypeCanal canal) {
        incrementerChamp(campaignId, canal, "envoyes");
    }

    @Override
    public void incrementerOuverts(Long campaignId, TypeCanal canal) {
        incrementerChamp(campaignId, canal, "ouverts");
    }

    @Override
    public void incrementerCliques(Long campaignId, TypeCanal canal) {
        incrementerChamp(campaignId, canal, "cliques");
    }

    @Override
    public void incrementerConversions(Long campaignId, TypeCanal canal) {
        incrementerChamp(campaignId, canal, "conversions");
    }

    @Override
    public void incrementerEchecs(Long campaignId, TypeCanal canal) {
        incrementerChamp(campaignId, canal, "echecs");
    }

    /**
     * Méthode interne pour incrémenter un champ du résultat.
     * 1. Cherche le ResultatCampagne existant pour cette campagne et ce canal
     * 2. Si n'existe pas, en crée un nouveau
     * 3. Incrémente le champ concerné de 1
     * 4. Met à jour dateCalcul avec LocalDateTime.now()
     * 5. Sauvegarde
     */
    private void incrementerChamp(Long campaignId, TypeCanal canal, String typeIncrement) {
        // Chercher ou créer
        ResultatCampagne resultat = trouverParCampagneEtCanal(campaignId, canal)
                .orElseGet(() -> creer(campaignId, canal));

        // Incrémenter le champ approprié
        switch (typeIncrement) {
            case "envoyes":
                resultat.setNombreEnvoyes(resultat.getNombreEnvoyes() + 1);
                break;
            case "ouverts":
                resultat.setNombreOuverts(resultat.getNombreOuverts() + 1);
                break;
            case "cliques":
                resultat.setNombreCliques(resultat.getNombreCliques() + 1);
                break;
            case "conversions":
                resultat.setNombreConversions(resultat.getNombreConversions() + 1);
                break;
            case "echecs":
                resultat.setNombreEchecs(resultat.getNombreEchecs() + 1);
                break;
            default:
                log.warn("Type d'incrémentation inconnu: {}", typeIncrement);
        }

        // Mettre à jour dateCalcul
        resultat.setDateCalcul(LocalDateTime.now());

        // Sauvegarder
        resultatCampagneRepository.save(resultat);
        log.debug("Incremented {} for campaign {} and canal {}", typeIncrement, campaignId, canal);
    }
}
