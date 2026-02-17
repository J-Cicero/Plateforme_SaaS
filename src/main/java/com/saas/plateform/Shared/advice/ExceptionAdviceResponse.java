package com.saas.plateform.Shared.advice;

public record ExceptionAdviceResponse(
        int code,
        String status,
        String message
) {
}
