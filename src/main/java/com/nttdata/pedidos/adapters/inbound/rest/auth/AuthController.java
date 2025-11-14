package com.nttdata.pedidos.adapters.inbound.rest.auth;

import com.nttdata.pedidos.adapters.inbound.rest.auth.dto.LoginRequest;
import com.nttdata.pedidos.adapters.inbound.rest.auth.dto.LoginResponse;
import com.nttdata.pedidos.application.auth.port.out.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for user: {}", request.getEmail());
        
        // Autenticar usuario
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // Cargar detalles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        
        // Generar token JWT
        String jwtToken = jwtService.generateToken(userDetails);

        // Construir respuesta (versión simplificada que compila)
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .type("Bearer")
                .email(userDetails.getUsername())
                .expiresIn(3600)
                .build();

        log.info("User {} successfully authenticated", request.getEmail());
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/health")
    @Operation(summary = "Auth health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is healthy");
    }
}