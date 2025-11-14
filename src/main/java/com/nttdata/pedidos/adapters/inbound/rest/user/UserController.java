package com.nttdata.pedidos.adapters.inbound.rest.user;

import com.nttdata.pedidos.adapters.inbound.rest.user.dto.UserRegisterRequest;
import com.nttdata.pedidos.adapters.inbound.rest.user.dto.UserResponse;
import com.nttdata.pedidos.adapters.inbound.rest.user.mapper.UserRestMapper;
import com.nttdata.pedidos.application.user.port.in.RegisterUserUseCase;
import com.nttdata.pedidos.application.user.port.in.GetUserByIdUseCase;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase, GetUserByIdUseCase getUserByIdUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest req) {
        var command = UserRestMapper.toCommand(req);
        var user = registerUserUseCase.register(command);
        var response = UserRestMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return getUserByIdUseCase.getById(id)
                .map(u -> ResponseEntity.ok(UserRestMapper.toResponse(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
