package com.saas.plateform.social.application.dtos;

import com.saas.plateform.social.domain.enums.TypePlateforme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteSocialRequestDTO {

    private TypePlateforme plateforme;

    private String nomCompte;

    private String tokenAcces;

    private Boolean actif;

    private Long proprietaireId;
}
