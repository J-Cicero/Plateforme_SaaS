package com.nitchcorp.backend.titan_hisaa.Shared.advice;

public record ExceptionAdviceResponse(
        int code,
        String status,
        String message
) {
}
