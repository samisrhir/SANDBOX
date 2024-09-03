package com.hps.sandbox.service.exceptions;

import lombok.Builder;

@Builder
public record ExceptionResponse(String errorMessage,String errorKey,String requestedURI) {
}