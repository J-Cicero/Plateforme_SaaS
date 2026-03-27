package com.saas.plateform.social.application.controllers;

import com.saas.plateform.social.domain.services.CompteSocialService;
import com.saas.plateform.social.domain.models.CompteSocial;
import com.saas.plateform.social.application.dtos.CompteSocialRequestDTO;
import com.saas.plateform.social.application.dtos.CompteSocialResponseDTO;
import com.saas.plateform.social.application.mappers.CompteSocialMapper;
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
@RequestMapping("/api/comptes-sociaux")
@RequiredArgsConstructor
@Tag(name = "Comptes Sociaux", description = "Social accounts management APIs")
public class CompteSocialController {

    private final CompteSocialService compteSocialService;
    private final CompteSocialMapper compteSocialMapper;

    @PostMapping
    @Operation(summary = "Create a new social account")
    public ResponseEntity<CompteSocialResponseDTO> creerCompteSocial(
            @Valid @RequestBody CompteSocialRequestDTO request) {
        CompteSocial compteSocial = compteSocialMapper.toEntity(request);
        CompteSocial created = compteSocialService.creer(compteSocial);
        return ResponseEntity.status(HttpStatus.CREATED).body(compteSocialMapper.toResponse(created));
    }

    @GetMapping("/{trackingId}")
    @Operation(summary = "Get social account by tracking ID")
    public ResponseEntity<CompteSocialResponseDTO> obtenirCompteSocial(
            @PathVariable UUID trackingId) {
        var compteSocial = compteSocialService.trouverParTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Compte social not found"));
        return ResponseEntity.ok(compteSocialMapper.toResponse(compteSocial));
    }

    @GetMapping
    @Operation(summary = "List all social accounts")
    public ResponseEntity<List<CompteSocialResponseDTO>> listerCompteSociaux() {
        List<CompteSocial> comptes = compteSocialService.lister();
        List<CompteSocialResponseDTO> response = comptes.stream()
                .map(compteSocialMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
