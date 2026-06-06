package com.grupo3.BookVerse.features.users.service.impl;

import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.subscriptions.domain.SubscriptionEntity;
import com.grupo3.BookVerse.features.subscriptions.repository.SubscriptionRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.dto.UserRequestDto;
import com.grupo3.BookVerse.features.users.dto.UserResponseDto;
import com.grupo3.BookVerse.features.users.mappers.UserMapper;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import com.grupo3.BookVerse.features.users.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    // Creates a new user after validating unique email and username, and assigning a subscription.
    public UserResponseDto createUser(UserRequestDto dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email is already in use");
        }

        if (userRepository.existsByUsername(dto.username())) {
            throw new DuplicateResourceException("Username is already in use");
        }

        SubscriptionEntity subscription = subscriptionRepository.findById(dto.subscriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        UserEntity user = userMapper.toEntity(dto);
        user.setPasswordHash(dto.password());
        user.setSubscription(subscription);

        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    // Retrieves all users and maps them to response DTOs.
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return userMapper.toResponseDtoList(users);
    }

    @Override
    // Retrieves a user by external ID and maps it to a response DTO.
    public UserResponseDto getUserByIdExternal(UUID idExternal) {
        UserEntity user = userRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    // Updates an existing user after validating uniqueness and subscription.
    public UserResponseDto updateUser(UUID idExternal, UserRequestDto dto) {
        UserEntity existingUser = userRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!existingUser.getEmail().equals(dto.email())
                && userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email is already in use");
        }

        if (!existingUser.getUsername().equals(dto.username())
                && userRepository.existsByUsername(dto.username())) {
            throw new DuplicateResourceException("Username is already in use");
        }

        SubscriptionEntity subscription = subscriptionRepository.findById(dto.subscriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        existingUser.setUsername(dto.username());
        existingUser.setEmail(dto.email());
        existingUser.setPasswordHash(dto.password());
        existingUser.setSubscription(subscription);

        UserEntity updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDto(updatedUser);
    }

    @Override
    @Transactional
    // Deletes a user by external ID after validating that the user exists.
    public void deleteUser(UUID idExternal) {
        UserEntity user = userRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }
}