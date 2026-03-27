package com.saas.plateform.social.application.mappers;

import com.saas.plateform.social.domain.models.CompteSocial;
import com.saas.plateform.social.application.dtos.CompteSocialRequestDTO;
import com.saas.plateform.social.application.dtos.CompteSocialResponseDTO;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Component;

@Component
public class CompteSocialMapper {

    public CompteSocial toEntity(CompteSocialRequestDTO request) {
        if (request == null) {
            throw new EntityExistsException("CompteSocialRequestDTO cannot be null");
        }
        return CompteSocial.builder()
                .plateforme(request.getPlateforme())
                .nomCompte(request.getNomCompte())
                .tokenAcces(request.getTokenAcces())
                .actif(request.getActif() != null ? request.getActif() : true)
                .build();
    }

    public CompteSocialResponseDTO toResponse(CompteSocial compteSocial) {
        if (compteSocial == null) {
            return null;
        }
        return CompteSocialResponseDTO.builder()
                .trackingId(compteSocial.getTrackingId())
                .plateforme(compteSocial.getPlateforme())
                .nomCompte(compteSocial.getNomCompte())
                .actif(compteSocial.getActif())
                .proprietaireName(compteSocial.getProprietaire() != null ? compteSocial.getProprietaire().getUsername() : null)
                .createdAt(compteSocial.getCreatedAt())
                .updatedAt(compteSocial.getUpdatedAt())
                .build();
    }
}
