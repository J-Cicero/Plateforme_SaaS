package com.nitchcorp.backend.titan_hisaa.Shared.security.user.domain.services;

import java.util.UUID;

import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.requests.LoginRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.requests.UserRequest;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.responses.LoginResponse;
import com.nitchcorp.backend.titan_hisaa.Shared.security.user.application.dtos.responses.UserResponse;


public interface UserService {

    UserResponse createUser(UserRequest request);
    LoginResponse authenticate(LoginRequest request);
    UserResponse getUserByTrackingId(UUID trackingId);
    public UserResponse updateUserEtat(UUID trackingId, boolean etat);
    public void deleteUser(UUID trackingId);
    public org.springframework.data.domain.Page<UserResponse> getAllUsers(int page, int size);

}
