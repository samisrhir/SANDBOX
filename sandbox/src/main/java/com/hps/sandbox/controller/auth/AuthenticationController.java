package com.hps.sandbox.controller.auth;

import com.hps.sandbox.service.AuthenticationService;
import com.hps.sandbox.service.dtos.AuthenticationRequest;
import com.hps.sandbox.service.dtos.AuthenticationResponse;
import com.hps.sandbox.service.dtos.RegistrationRequest;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        try {
            if (request == null) {
                return ResponseEntity.badRequest().body("Request body is required");
            }

            ResponseEntity<?> response = service.register(request);

            return response;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws SandboxEntityNotFound {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
