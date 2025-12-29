package com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.requests.LoginRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.requests.UserRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.responses.LoginResponse;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.responses.UserResponse;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.domain.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "UserController", description = "api permettant a un utilisateur de s'enregistrer et de se connecter ")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Register a new user",
            description = "Register a new user and sends email if necessary")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input or user already exists"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }



    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns JWT token")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Account disabled or locked"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{trackingId}")
    @Operation(summary = "Get user by trackingId", description = "Get user by trackingId")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<UserResponse> getUserByTrackingId(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(userService.getUserByTrackingId(trackingId));
    }

    @PatchMapping("/etat/{trackingId}")
    @Operation(
            summary = "Update user activation status",
            description = "Activates or deactivates a user")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Status updated successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<UserResponse> updateUserEtat(
            @RequestParam boolean etat, @PathVariable UUID trackingId) {
        UserResponse response = userService.updateUserEtat(trackingId, etat);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lister tous les utilisateurs",
            description = "Retourne la liste paginée de tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/all")
    public ResponseEntity<org.springframework.data.domain.Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @DeleteMapping("/{trackingId}")
    @Operation(
            summary = "Supprimer un utilisateur",
            description = "Supprime un utilisateur par son trackingId")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID trackingId) {
        userService.deleteUser(trackingId);
        return ResponseEntity.noContent().build();
    }
}