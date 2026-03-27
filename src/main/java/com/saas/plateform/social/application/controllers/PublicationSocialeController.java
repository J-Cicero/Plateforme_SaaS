package com.saas.plateform.social.application.controllers;

import com.saas.plateform.social.domain.services.PublicationSocialeService;
import com.saas.plateform.social.domain.models.PublicationSociale;
import com.saas.plateform.social.application.dtos.PublicationSocialeRequestDTO;
import com.saas.plateform.social.application.dtos.PublicationSocialeResponseDTO;
import com.saas.plateform.social.application.mappers.PublicationSocialeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publications-sociales")
@RequiredArgsConstructor
@Tag(name = "Publications Sociales", description = "Social publications management APIs")
public class PublicationSocialeController {

    private final PublicationSocialeService publicationSocialeService;
    private final PublicationSocialeMapper publicationSocialeMapper;

    @PostMapping
    @Operation(summary = "Create a new social publication")
    public ResponseEntity<PublicationSocialeResponseDTO> creerPublicationSociale(
            @Valid @RequestBody PublicationSocialeRequestDTO request) {
        PublicationSociale publication = publicationSocialeMapper.toEntity(request);
        PublicationSociale created = publicationSocialeService.creer(publication);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationSocialeMapper.toResponse(created));
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Get social publication by tracking ID")
    public ResponseEntity<PublicationSocialeResponseDTO> obtenirPublicationSociale(
            @PathVariable UUID trackingId) {
        var publication = publicationSocialeService.trouverParTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        return ResponseEntity.ok(publicationSocialeMapper.toResponse(publication));
    }

    @GetMapping
    @Operation(summary = "List all social publications")
    public ResponseEntity<List<PublicationSocialeResponseDTO>> listerPublicationsSociales() {
        List<PublicationSociale> publications = publicationSocialeService.lister();
        List<PublicationSocialeResponseDTO> response = publications.stream()
                .map(publicationSocialeMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
