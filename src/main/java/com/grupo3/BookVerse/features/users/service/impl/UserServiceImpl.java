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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email is already in use");
        }

        if (userRepository.existsByUsername(dto.username())) {
            throw new DuplicateResourceException("Username is already in use");
        }

        SubscriptionEntity subscription =
                findSubscriptionByIdExternal(dto.subscriptionId());

        UserEntity user =
                userMapper.toEntity(dto);

        user.setPasswordHash(dto.password());
        user.setSubscription(subscription);

        UserEntity saved =
                userRepository.save(user);

        return userMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {

        List<UserEntity> users =
                userRepository.findAll();

        return userMapper.toResponseDtoList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByIdExternal(UUID idExternal) {

        UserEntity user =
                findUserByIdExternal(idExternal);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UUID idExternal, UserRequestDto dto) {

        UserEntity existing =
                findUserByIdExternal(idExternal);

        if (!existing.getEmail().equals(dto.email())
                && userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email is already in use");
        }

        if (!existing.getUsername().equals(dto.username())
                && userRepository.existsByUsername(dto.username())) {
            throw new DuplicateResourceException("Username is already in use");
        }

        SubscriptionEntity subscription =
                findSubscriptionByIdExternal(dto.subscriptionId());

        existing.setUsername(dto.username());
        existing.setEmail(dto.email());
        existing.setPasswordHash(dto.password());
        existing.setSubscription(subscription);

        UserEntity updated =
                userRepository.save(existing);

        return userMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteUser(UUID idExternal) {

        UserEntity user =
                findUserByIdExternal(idExternal);

        userRepository.delete(user);
    }

    private UserEntity findUserByIdExternal(UUID idExternal) {

        return userRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: " + idExternal
                        )
                );
    }

    private SubscriptionEntity findSubscriptionByIdExternal(UUID idExternal) {

        return subscriptionRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subscription not found with idExternal: " + idExternal
                        )
                );
    }
}

