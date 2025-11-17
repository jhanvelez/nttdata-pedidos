package com.nttdata.pedidos.adapters.inbound.rest.user;

import com.nttdata.pedidos.adapters.inbound.rest.user.dto.UserRegisterRequest;
import com.nttdata.pedidos.adapters.inbound.rest.user.dto.UserResponse;
import com.nttdata.pedidos.adapters.inbound.rest.user.mapper.UserRestMapper;
import com.nttdata.pedidos.application.user.port.in.RegisterUserUseCase;
import com.nttdata.pedidos.application.user.port.in.GetUserByIdUseCase;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Importaciones de Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Gestión de Usuarios", description = "Operaciones para el registro y consulta de usuarios")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase, GetUserByIdUseCase getUserByIdUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario en el sistema con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario registrado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "El email ya está registrado en el sistema",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest req) {
        var command = UserRestMapper.toCommand(req);
        var user = registerUserUseCase.register(command);
        var response = UserRestMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener usuario por ID",
        description = "Recupera la información de un usuario específico mediante su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(
            @Parameter(description = "ID del usuario a consultar", example = "1", required = true)
            @PathVariable Long id) {
        return getUserByIdUseCase.getById(id)
                .map(u -> ResponseEntity.ok(UserRestMapper.toResponse(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}