package com.grupo3.BookVerse.features.subscriptions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> { }
