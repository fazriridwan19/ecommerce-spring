package com.lazyorchest.e_commerce.services;

import com.lazyorchest.e_commerce.dto.AuthenticationResponse;
import com.lazyorchest.e_commerce.dto.LoginRequest;
import com.lazyorchest.e_commerce.dto.RegisterRequest;
import com.lazyorchest.e_commerce.dto.RegisterResponse;
import com.lazyorchest.e_commerce.models.Role;
import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.repositories.UserRepo;
import com.lazyorchest.e_commerce.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public RegisterResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);

        return RegisterResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
