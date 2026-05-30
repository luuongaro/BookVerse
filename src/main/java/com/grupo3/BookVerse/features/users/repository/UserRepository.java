package com.grupo3.BookVerse.features.users.repository;

import com.grupo3.BookVerse.features.users.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdExternal(UUID idExternal);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}