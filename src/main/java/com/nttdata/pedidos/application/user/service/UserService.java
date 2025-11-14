package com.nttdata.pedidos.application.user.service;

import com.nttdata.pedidos.application.user.port.in.RegisterUserUseCase;
import com.nttdata.pedidos.application.user.port.in.GetUserByIdUseCase;
import com.nttdata.pedidos.application.user.port.in.GetUserByEmailUseCase;
import com.nttdata.pedidos.application.user.port.out.UserPersistencePort;
import com.nttdata.pedidos.domain.user.User;
import com.nttdata.pedidos.domain.user.Role;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService implements RegisterUserUseCase, GetUserByIdUseCase, GetUserByEmailUseCase {

    private final UserPersistencePort userPersistence;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserPersistencePort userPersistence, PasswordEncoder passwordEncoder) {
        this.userPersistence = userPersistence;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User register(RegisterUserCommand command) {
        if (userPersistence.existsByEmail(command.email().toLowerCase())) {
            throw new IllegalStateException("Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(command.password());

        User user = new User();
        user.setFirstName(command.firstName());
        user.setLastName(command.lastName());
        user.setEmail(command.email().toLowerCase());
        user.setPasswordHash(hashedPassword);

        var roles = new HashSet<String>();
        // CORRECCIÓN: Role.ROLE_USER es una constante String, no enum
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        return userPersistence.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userPersistence.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userPersistence.findByEmail(email.toLowerCase());
    }
}