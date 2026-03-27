package com.saas.plateform.social.application.mappers;

import com.saas.plateform.social.domain.models.PublicationSociale;
import com.saas.plateform.social.application.dtos.PublicationSocialeRequestDTO;
import com.saas.plateform.social.application.dtos.PublicationSocialeResponseDTO;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Component;

@Component
public class PublicationSocialeMapper {

    public PublicationSociale toEntity(PublicationSocialeRequestDTO request) {
        if (request == null) {
            throw new EntityExistsException("PublicationSocialeRequestDTO cannot be null");
        }
        return PublicationSociale.builder()
                .contenu(request.getContenu())
                .mediaUrl(request.getMediaUrl())
                .dateProgrammee(request.getDateProgrammee())
                .statut(request.getStatut())
                .build();
    }

    public PublicationSocialeResponseDTO toResponse(PublicationSociale publicationSociale) {
        if (publicationSociale == null) {
            return null;
        }
        return PublicationSocialeResponseDTO.builder()
                .trackingId(publicationSociale.getTrackingId())
                .contenu(publicationSociale.getContenu())
                .mediaUrl(publicationSociale.getMediaUrl())
                .dateProgrammee(publicationSociale.getDateProgrammee())
                .datePublication(publicationSociale.getDatePublication())
                .statut(publicationSociale.getStatut())
                .compteSocialNom(publicationSociale.getCompteSocial() != null ? publicationSociale.getCompteSocial().getNomCompte() : null)
                .createurName(publicationSociale.getCreateur() != null ? publicationSociale.getCreateur().getUsername() : null)
                .createdAt(publicationSociale.getCreatedAt())
                .updatedAt(publicationSociale.getUpdatedAt())
                .build();
    }
}
