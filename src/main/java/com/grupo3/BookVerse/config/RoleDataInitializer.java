package com.grupo3.BookVerse.config;

import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private static final List<String> DEFAULT_ROLES = List.of(
            "ROLE_USER",
            "ROLE_MODERATOR",
            "ROLE_ADMIN"
    );

    @Override
    @Transactional
    public void run(String... args) {
        DEFAULT_ROLES.forEach(this::createRoleIfNotExists);
    }

    private void createRoleIfNotExists(String roleName) {
        boolean exists = roleRepository.existsByNameIgnoreCase(roleName);

        if (!exists) {
            RoleEntity role = RoleEntity.builder()
                    .name(roleName)
                    .build();

            roleRepository.save(role);
            log.info("Role created: {}", roleName);
        } else {
            log.info("Role already exists: {}", roleName);
        }
    }
}
