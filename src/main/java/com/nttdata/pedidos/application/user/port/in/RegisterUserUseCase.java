package com.nttdata.pedidos.application.user.port.in;

import com.nttdata.pedidos.domain.user.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);

    record RegisterUserCommand(String firstName, String lastName, String email, String password) {}
}
