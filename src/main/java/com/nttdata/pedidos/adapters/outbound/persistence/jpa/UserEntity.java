package com.nttdata.pedidos.adapters.outbound.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(name="password_hash", nullable=false)
    private String passwordHash;

    // simple roles representation as a separate table
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    public static UserEntity fromDomain(com.nttdata.pedidos.domain.user.User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .email(domain.getEmail())
                .passwordHash(domain.getPasswordHash())
                .roles(domain.getRoles() == null ? new HashSet<>() : domain.getRoles())
                .build();
    }

    public com.nttdata.pedidos.domain.user.User toDomain() {
        var domain = new com.nttdata.pedidos.domain.user.User();
        domain.setId(this.id);
        domain.setFirstName(this.firstName);
        domain.setLastName(this.lastName);
        domain.setEmail(this.email);
        domain.setPasswordHash(this.passwordHash);
        domain.setRoles(this.roles);
        return domain;
    }
}
