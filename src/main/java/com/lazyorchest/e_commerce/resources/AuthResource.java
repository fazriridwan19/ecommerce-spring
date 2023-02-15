package com.lazyorchest.e_commerce.resources;

import com.lazyorchest.e_commerce.dto.AuthenticationResponse;
import com.lazyorchest.e_commerce.dto.LoginRequest;
import com.lazyorchest.e_commerce.dto.RegisterRequest;
import com.lazyorchest.e_commerce.dto.RegisterResponse;
import com.lazyorchest.e_commerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(authService.login(request));
    }
}
