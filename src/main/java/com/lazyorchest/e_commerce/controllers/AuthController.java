package com.lazyorchest.e_commerce.controllers;

import com.lazyorchest.e_commerce.dto.AuthenticationResponse;
import com.lazyorchest.e_commerce.dto.LoginRequest;
import com.lazyorchest.e_commerce.dto.RegisterRequest;
import com.lazyorchest.e_commerce.dto.RegisterResponse;
import com.lazyorchest.e_commerce.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "Register user", description = "This is endpoint to register new user", tags = "Auth Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has been created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @SecurityRequirements
    @PostMapping("/v1/auth/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
    @Operation(summary = "Login user", description = "This is endpoint used to login registered user, after logged in json web token is generated", tags = "Auth Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been validated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @SecurityRequirements
    @PostMapping("/v1/auth/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(authService.login(request));
    }
}
