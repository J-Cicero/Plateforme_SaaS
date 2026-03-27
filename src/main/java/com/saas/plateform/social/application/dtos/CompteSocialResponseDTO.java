package com.saas.plateform.social.application.dtos;

import com.saas.plateform.social.domain.enums.TypePlateforme;
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
public class CompteSocialResponseDTO {

    private UUID trackingId;

    private TypePlateforme plateforme;

    private String nomCompte;

    private Boolean actif;

    private String proprietaireName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
