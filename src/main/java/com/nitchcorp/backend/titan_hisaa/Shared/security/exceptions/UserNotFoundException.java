package com.nitchcorp.backend.titan_hisaa.Shared.security.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
