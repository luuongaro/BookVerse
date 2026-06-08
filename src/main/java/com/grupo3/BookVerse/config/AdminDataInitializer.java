package com.grupo3.BookVerse.config;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import com.grupo3.BookVerse.features.roles.repository.RoleRepository;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionType;
import com.grupo3.BookVerse.features.subscriptions.repository.SubscriptionRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.domain.UserStatus;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminDataInitializer implements CommandLineRunner {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) {
        createAdminIfNotExists();
    }

    private void createAdminIfNotExists() {
        boolean exists = userRepository.existsByEmail(adminEmail);

        if (exists) {
            log.info("Admin user already exists with email: {}", adminEmail);
            return;
        }

        RoleEntity adminRole = roleRepository.findByNameIgnoreCase(ADMIN_ROLE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found: " + ADMIN_ROLE)
                );

        SubscriptionEntity adminSubscription = subscriptionRepository.findByType(SubscriptionType.PREMIUM)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subscription not found: PREMIUM")
                );

        UserEntity adminUser = UserEntity.builder()
                .username(adminUsername)
                .email(adminEmail)
                .passwordHash(passwordEncoder.encode(adminPassword))
                .subscription(adminSubscription)
                .status(UserStatus.ACTIVE)
                .build();

        adminUser.getRoles().add(adminRole);

        userRepository.save(adminUser);

        log.info("Default admin user created with email: {}", adminEmail);
    }
}
