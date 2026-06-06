package com.grupo3.BookVerse.features.subscriptions.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionType;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionRequestDto;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import com.grupo3.BookVerse.features.subscriptions.mappers.SubscriptionMapper;
import com.grupo3.BookVerse.features.subscriptions.repository.SubscriptionRepository;
import com.grupo3.BookVerse.features.subscriptions.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionResponseDto createSubscription(SubscriptionRequestDto dto) {
        SubscriptionEntity entity = subscriptionMapper.toEntity(dto);
        SubscriptionEntity saved = subscriptionRepository.save(entity);
        return subscriptionMapper.toResponseDto(saved);
    }

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
    public SubscriptionResponseDto updateSubscription(UUID idExternal, SubscriptionRequestDto dto) {
        SubscriptionEntity entity = subscriptionRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        entity.setType(SubscriptionType.valueOf(dto.getType()));
        entity.setMaxStoriesPublished(dto.getMaxStoriesPublished());
        entity.setAdvancedStatsEnabled(dto.isAdvancedStatsEnabled());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        SubscriptionEntity saved = subscriptionRepository.save(entity);
        return subscriptionMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteSubscription(UUID idExternal) {
        SubscriptionEntity entity = subscriptionRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        subscriptionRepository.delete(entity);
    }
}