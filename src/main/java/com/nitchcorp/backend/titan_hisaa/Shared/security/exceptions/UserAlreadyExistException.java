package com.nitchcorp.backend.titan_hisaa.Shared.security.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
