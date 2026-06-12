package com.grupo3.BookVerse.features.subscriptions.service;

import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    List<SubscriptionResponseDto> getAllSubscriptions();

    SubscriptionResponseDto getSubscriptionByIdExternal(UUID idExternal);

    SubscriptionResponseDto upgradeAuthenticatedUserToPremium();
}
