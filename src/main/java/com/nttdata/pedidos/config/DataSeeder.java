package com.nttdata.pedidos.config;

import com.nttdata.pedidos.application.user.port.out.UserPersistencePort;
import com.nttdata.pedidos.domain.user.User;
import com.nttdata.pedidos.domain.user.Role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.HashSet;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(UserPersistencePort userPersistencePort, PasswordEncoder encoder) {
        return args -> {
            String adminEmail = "admin@nttdata.com";
            if (userPersistencePort.existsByEmail(adminEmail)) return;

            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("NTT");
            admin.setEmail(adminEmail);
            admin.setPasswordHash(encoder.encode("Admin123!"));
            var roles = new HashSet<String>();
            roles.add(Role.ROLE_ADMIN);
            roles.add(Role.ROLE_USER);
            admin.setRoles(roles);

            userPersistencePort.save(admin);
            System.out.println("Seeded admin user: " + adminEmail);
        };
    }
}