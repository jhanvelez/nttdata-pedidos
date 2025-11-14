package com.nttdata.pedidos.application.user.port.in;

import com.nttdata.pedidos.domain.user.User;

import java.util.Optional;

public interface GetUserByEmailUseCase {
    Optional<User> getByEmail(String email);
}