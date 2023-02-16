package com.lazyorchest.e_commerce.config;

import com.lazyorchest.e_commerce.dto.ProductRequest;
import com.lazyorchest.e_commerce.dto.RegisterRequest;
import com.lazyorchest.e_commerce.repositories.UserRepo;
import com.lazyorchest.e_commerce.services.AuthService;
import com.lazyorchest.e_commerce.services.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserRepo userRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    CommandLineRunner run(AuthService authService, ProductService productService) {
        return args -> {
            authService.register(
                    new RegisterRequest("Fazri", "fazri", "fazri")
            );
            productService.createProduct(
                    new ProductRequest("Product 1", 200000.0, 2, "category1")
            );
            productService.createProduct(
                    new ProductRequest("Product 2", 75000.0, 1, "category1")
            );
            productService.createProduct(
                    new ProductRequest("Product 3", 50000.0, 1, "category2")
            );
            productService.createProduct(
                    new ProductRequest("Product 4", 90000.0, 1, "category3")
            );
        };
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true);
            }
        };
    }

/*
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            }
        };
    }
*/
}
