package com.nttdata.pedidos.adapters.inbound.rest.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type;
    private String email;
    private Long userId;
    
    @Builder.Default
    private long expiresIn = 3600; // 1 hora en segundos
}