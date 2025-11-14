package com.nttdata.pedidos.adapters.outbound.persistence;

import com.nttdata.pedidos.adapters.outbound.persistence.jpa.SpringDataUserRepository;
import com.nttdata.pedidos.adapters.outbound.persistence.jpa.UserEntity;
import com.nttdata.pedidos.application.user.port.out.UserPersistencePort;
import com.nttdata.pedidos.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserPersistencePort {

    private final SpringDataUserRepository repository;

    public UserPersistenceAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity saved = repository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email.toLowerCase()).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email.toLowerCase());
    }
}
