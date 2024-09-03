package com.hps.sandbox.service.exceptions;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SandboxEntityNotFound extends Exception{

    private String errorMessage;
    private String errorKey;
    private String requestedURI;

    public SandboxEntityNotFound(String message) {
        super(message);
        this.errorMessage = message;
    }

}