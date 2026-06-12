package com.grupo3.BookVerse.features.subscriptions.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionType;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import com.grupo3.BookVerse.features.subscriptions.mappers.SubscriptionMapper;
import com.grupo3.BookVerse.features.subscriptions.repository.SubscriptionRepository;
import com.grupo3.BookVerse.features.subscriptions.service.SubscriptionService;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseDto> getAllSubscriptions() {
        return subscriptionMapper.toResponseDtoList(subscriptionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public SubscriptionResponseDto getSubscriptionByIdExternal(UUID idExternal) {
        SubscriptionEntity entity = subscriptionRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        return subscriptionMapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public SubscriptionResponseDto upgradeAuthenticatedUserToPremium() {
        UserEntity authenticatedUser = getAuthenticatedUser();

        SubscriptionEntity premiumSubscription = subscriptionRepository.findByType(SubscriptionType.PREMIUM)
                .orElseThrow(() -> new ResourceNotFoundException("Premium subscription not found"));

        authenticatedUser.setSubscription(premiumSubscription);
        userRepository.save(authenticatedUser);

        return subscriptionMapper.toResponseDto(premiumSubscription);
    }

    private UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity user)) {
            throw new AccessDeniedException("Authenticated user not found");
        }

        return user;
    }
}