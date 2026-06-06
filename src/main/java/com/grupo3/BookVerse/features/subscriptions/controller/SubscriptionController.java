package com.grupo3.BookVerse.features.subscriptions.controller;

import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionRequestDto;
import com.grupo3.BookVerse.features.subscriptions.dto.SubscriptionResponseDto;
import com.grupo3.BookVerse.features.subscriptions.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public SubscriptionResponseDto createSubscription(@Valid @RequestBody SubscriptionRequestDto dto) {
        return subscriptionService.createSubscription(dto);
    }

    @GetMapping
    public List<SubscriptionResponseDto> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{idExternal}")
    public SubscriptionResponseDto getSubscriptionByIdExternal(@PathVariable UUID idExternal) {
        return subscriptionService.getSubscriptionByIdExternal(idExternal);
    }

    @PutMapping("/{idExternal}")
    public SubscriptionResponseDto updateSubscription(@PathVariable UUID idExternal,
                                                      @Valid @RequestBody SubscriptionRequestDto dto) {
        return subscriptionService.updateSubscription(idExternal, dto);
    }

    @DeleteMapping("/{idExternal}")
    public void deleteSubscription(@PathVariable UUID idExternal) {
        subscriptionService.deleteSubscription(idExternal);
    }
}
