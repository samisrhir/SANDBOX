package com.hps.sandbox.service;

import com.hps.sandbox.entity.User;
import com.hps.sandbox.repository.RoleRepository;
import com.hps.sandbox.repository.TokenRepository;
import com.hps.sandbox.repository.UserRepository;
import com.hps.sandbox.service.dtos.AuthenticationRequest;
import com.hps.sandbox.service.dtos.AuthenticationResponse;
import com.hps.sandbox.service.dtos.RegistrationRequest;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    public ResponseEntity<?> register(RegistrationRequest request) {
        try {
            // Check if the username already exists
            if (userRepository.existsByUsername(request.getUsername().toLowerCase())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("{\"error\": \"Username already exists\"}");
            }
            // Check if the email already exists
            if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("{\"error\": \"Email already exists\"}");
            }

            // Retrieve the user role
            var userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));

            // Create a new user entity
            var user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .enabled(false)
                    .fullName(request.getFullName())
                    .createdDate(new Date())
                    .phone(request.getPhone())
                    .roles(List.of(userRole))
                    .build();

            // Save the user entity
            userRepository.save(user);

            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"User registered successfully\"}");
        } catch (Exception ex) {
            // Return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Internal server error\"}");
        }
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Constructing the JWT token and response after successful authentication
            var claims = new HashMap<String, Object>();
            var user = (User) auth.getPrincipal();
            claims.put("user", user);

            var jwtToken = jwtService.generateToken(claims, user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        } catch (UsernameNotFoundException ex) {
            return handleAuthenticationError("No user found with username: " + request.getUsername());
        } catch (DisabledException ex) {
            return handleAuthenticationError("User account is disabled !");
        } catch (AuthenticationException ex) {
            return handleAuthenticationError("User or Password Incorrect !");
        }
    }

    private AuthenticationResponse handleAuthenticationError(String errorMessage) {
        return AuthenticationResponse.builder().error(errorMessage).build();
    }

}
