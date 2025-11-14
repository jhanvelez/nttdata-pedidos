package com.nttdata.pedidos.application.user.port.out;

import com.nttdata.pedidos.domain.user.User;
import java.util.Optional;

public interface UserPersistencePort {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
}
