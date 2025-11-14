package com.nttdata.pedidos.adapters.inbound.rest.user.mapper;

import com.nttdata.pedidos.adapters.inbound.rest.user.dto.UserRegisterRequest;
import com.nttdata.pedidos.adapters.inbound.rest.user.dto.UserResponse;
import com.nttdata.pedidos.domain.user.User;

public final class UserRestMapper {

    private UserRestMapper() {}

    public static com.nttdata.pedidos.application.user.port.in.RegisterUserUseCase.RegisterUserCommand toCommand(UserRegisterRequest req) {
        return new com.nttdata.pedidos.application.user.port.in.RegisterUserUseCase.RegisterUserCommand(
                req.getFirstName(),
                req.getLastName(),
                req.getEmail(),
                req.getPassword()
        );
    }

    public static UserResponse toResponse(User domain) {
        if (domain == null) return null;
        var r = new UserResponse();
        r.setId(domain.getId());
        r.setFirstName(domain.getFirstName());
        r.setLastName(domain.getLastName());
        r.setEmail(domain.getEmail());
        r.setRoles(domain.getRoles());
        return r;
    }
}
