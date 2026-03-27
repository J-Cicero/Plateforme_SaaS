package com.saas.plateform.social.application.dtos;

import com.saas.plateform.social.domain.enums.StatutPublication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationSocialeResponseDTO {

    private UUID trackingId;

    private String contenu;

    private String mediaUrl;

    private LocalDateTime dateProgrammee;

    private LocalDateTime datePublication;

    private StatutPublication statut;

    private String compteSocialNom;

    private String createurName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
