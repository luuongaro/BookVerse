package com.grupo3.BookVerse.features.subscriptions;

import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findByIdExternal(UUID idExternal);
}
