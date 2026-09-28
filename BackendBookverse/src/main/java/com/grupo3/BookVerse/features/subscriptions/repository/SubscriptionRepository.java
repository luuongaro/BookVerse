package com.grupo3.BookVerse.features.subscriptions.repository;

import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findByIdExternal(UUID idExternal);

    boolean existsByType(SubscriptionType type);

    Optional<SubscriptionEntity> findByType(SubscriptionType type);

}
