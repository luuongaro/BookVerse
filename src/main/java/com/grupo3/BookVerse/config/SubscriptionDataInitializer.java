package com.grupo3.BookVerse.config;

import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionType;
import com.grupo3.BookVerse.features.subscriptions.repository.SubscriptionRepository;
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
@Order(2)
public class SubscriptionDataInitializer implements CommandLineRunner {

    private final SubscriptionRepository subscriptionRepository;

    private static final List<SubscriptionType> DEFAULT_SUBSCRIPTIONS = List.of(
            SubscriptionType.FREE,
            SubscriptionType.PREMIUM
    );

    @Override
    @Transactional
    public void run(String... args) {
        DEFAULT_SUBSCRIPTIONS.forEach(this::createSubscriptionIfNotExists);
    }

    private void createSubscriptionIfNotExists(SubscriptionType type) {
        boolean exists = subscriptionRepository.existsByType(type);

        if (!exists) {
            SubscriptionEntity subscription = SubscriptionEntity.builder()
                    .type(type)
                    .maxStoriesPublished(type == SubscriptionType.FREE ? 5 : Integer.MAX_VALUE)
                    .maxActiveStoriesReading(type == SubscriptionType.FREE ? 3 : Integer.MAX_VALUE)
                    .advancedStatsEnabled(type == SubscriptionType.PREMIUM)
                    .build();

            subscriptionRepository.save(subscription);

            log.info("Subscription created: {}", type);
        } else {
            log.info("Subscription already exists: {}", type);
        }
    }
}