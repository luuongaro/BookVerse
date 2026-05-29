package com.grupo3.BookVerse.features.reviews.repository;

import com.grupo3.BookVerse.features.reviews.domain.ReviewReportEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewReportRepository extends JpaRepository<ReviewReportEntity, Long> {

    Optional<ReviewReportEntity> findByIdExternal(UUID idExternal);

    boolean existsByIdExternal(UUID idExternal);

    void deleteByIdExternal(UUID idExternal);
}