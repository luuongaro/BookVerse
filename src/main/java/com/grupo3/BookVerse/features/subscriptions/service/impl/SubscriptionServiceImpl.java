package com.grupo3.BookVerse.features.subscriptions.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.subscriptions.repository.SubscriptionRepository;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionRequestDto;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import com.grupo3.BookVerse.features.subscriptions.mappers.SubscriptionMapper;
import com.grupo3.BookVerse.features.subscriptions.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    // Creates a new subscription from the request DTO and returns the persisted result.
    public SubscriptionResponseDto createSubscription(SubscriptionRequestDto dto) {
        SubscriptionEntity entity = subscriptionMapper.toEntity(dto);
        SubscriptionEntity saved = subscriptionRepository.save(entity);
        return subscriptionMapper.toResponseDto(saved);
    }

    @Override
    // Retrieves all subscriptions and maps them to response DTOs.
    public List<SubscriptionResponseDto> getAllSubscriptions() {
        return subscriptionMapper.toResponseDtoList(subscriptionRepository.findAll());
    }

    @Override
    // Retrieves a subscription by external ID and maps it to a response DTO.
    public SubscriptionResponseDto getSubscriptionByIdExternal(UUID idExternal) {
        SubscriptionEntity e = subscriptionRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        return subscriptionMapper.toResponseDto(e);
    }

    @Override
    @Transactional
    // Updates an existing subscription after validating that it exists.
    public SubscriptionResponseDto updateSubscription(UUID idExternal, SubscriptionRequestDto dto) {
        SubscriptionEntity e = subscriptionRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        e.setType(SubscriptionEntity.SubscriptionType.valueOf(dto.getType()));
        e.setMaxStoriesPublished(dto.getMaxStoriesPublished());
        e.setAdvancedStatsEnabled(dto.isAdvancedStatsEnabled());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());

        SubscriptionEntity saved = subscriptionRepository.save(e);
        return subscriptionMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    // Deletes a subscription by external ID after validating that it exists.
    public void deleteSubscription(UUID idExternal) {
        SubscriptionEntity e = subscriptionRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        subscriptionRepository.delete(e);
    }
}
