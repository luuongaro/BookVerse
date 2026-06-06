package com.grupo3.BookVerse.features.reviews.repository;

import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByIdExternal(UUID idExternal);

    boolean existsByIdExternal(UUID idExternal);

    void deleteByIdExternal(UUID idExternal);
}