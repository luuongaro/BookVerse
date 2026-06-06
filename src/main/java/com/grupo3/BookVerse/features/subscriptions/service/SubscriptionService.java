package com.grupo3.BookVerse.features.subscriptions.service;

import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionRequestDto;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    SubscriptionResponseDto createSubscription(SubscriptionRequestDto dto);

    List<SubscriptionResponseDto> getAllSubscriptions();

    SubscriptionResponseDto getSubscriptionByIdExternal(UUID idExternal);

    SubscriptionResponseDto updateSubscription(UUID idExternal, SubscriptionRequestDto dto);

    void deleteSubscription(UUID idExternal);
}
