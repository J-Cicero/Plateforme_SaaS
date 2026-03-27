package com.saas.plateform.social.application.dtos;

import com.saas.plateform.social.domain.enums.StatutPublication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationSocialeRequestDTO {

    private String contenu;

    private String mediaUrl;

    private LocalDateTime dateProgrammee;

    private StatutPublication statut;

    private Long compteSocialId;

    private Long createurId;
}
