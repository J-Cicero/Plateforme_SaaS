package com.nitchcorp.backend.titan_hisaa.Shared.security.user.domain.services.servicesImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nitchcorp.backend.titan_hisaa.Shared.security.jwt.JwtService;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.config.MailConstants;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.dto.request.EmailRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.service.EmailService;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.requests.LoginRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.requests.UserRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.responses.LoginResponse;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.responses.UserResponse;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.mappers.UserMapper;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.domain.models.User;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.domain.services.UserService;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.infrastructure.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    public UserResponse createUser(UserRequest request){
        User user = this.userMapper.toEntity(request);
        User savedUser = this.userRepository.save(user);
       return this.userMapper.toResponse(user);

    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtService.generateJwtToken(authentication);

            User userDetails = (User) authentication.getPrincipal();

            List<String> rolesList = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return new LoginResponse(
                    userDetails.getTrackingId(),
                    token,
                    "Bearer",
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getPhone(),
                    userDetails.getEmail(),
                    userDetails.getRole().name(),
                    rolesList,
                    userDetails.getCountry(),
                    userDetails.isActif()
            );

        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Les paramètres de connexion sont incorrects");
        }
    }


    @Override
    public UserResponse getUserByTrackingId(UUID trackingId) {
        User user =
                userRepository
                        .findByTrackingId(trackingId)
                        .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUserEtat(UUID trackingId, boolean etat) {
        User user =
                userRepository
                        .findByTrackingId(trackingId)
                        .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        user.setActif(etat);
        user = userRepository.save(user);

        if (etat) {
/*
            EmailRequest emailRequest = buildRoleBasedEmailRequest(user, null);

            String template =
                    switch (user.getRole()) {
                        case PATIENT -> TitanEmailConstants.TEMPLATE_USER_PATIENT_ACTIVATION;
                        case DOCTOR -> TitanEmailConstants.TEMPLATE_USER_DOCTOR_ACTIVATION;
                        case PHARMACIST -> TitanEmailConstants.TEMPLATE_USER_PHARMACIST_ACTIVATION;
                        case TITAN_ADMIN -> TitanEmailConstants.TEMPLATE_USER_ADMIN_ACTIVATION;
                        default -> TitanEmailConstants.TEMPLATE_USER_ACTIVATION_DEFAULT;
                    };

            sendSubscriptionEmail(emailRequest, template);

 */
        }

        return userMapper.toResponse(user);
    }

    @Override
    public org.springframework.data.domain.Page<UserResponse> getAllUsers(int page, int size) {
        org.springframework.data.domain.Pageable pageable = 
            org.springframework.data.domain.PageRequest.of(page, size);
        
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    public void deleteUser(UUID trackingId) {
        User user =
                userRepository
                        .findByTrackingId(trackingId)
                        .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        userRepository.delete(user);
    }

    private EmailRequest buildAdminEmailRequest(User user) {
        return EmailRequest.builder()
                .mailFrom(MailConstants.EMAIL_FROM)
                .mailTo(user.getEmail())
                .mailSubject(" " + user.getFirstName() )
                .nom(user.getFirstName())
                .username(user.getEmail())
                .password(user.getPassword())
                .lien(MailConstants.WEBSITE_URL)
                .contact(MailConstants.COMPANY_SUPPORT)
                .build();
    }
}
