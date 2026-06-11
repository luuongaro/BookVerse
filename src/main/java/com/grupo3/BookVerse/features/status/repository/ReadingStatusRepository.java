package com.grupo3.BookVerse.features.status.repository;

import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingStatusRepository
        extends JpaRepository<ReadingStatusEntity, Long> {

    Optional<ReadingStatusEntity> findByIdExternal(
            UUID idExternal
    );

    List<ReadingStatusEntity> findByUserIdExternal(
            UUID userId
    );

    List<ReadingStatusEntity> findByBookIdExternal(
            UUID bookId
    );

    List<ReadingStatusEntity> findByStoryIdExternal(
            UUID storyId
    );

    List<ReadingStatusEntity>
    findByUserIdExternalInAndBookIdExternal(
            List<UUID> userIds,
            UUID bookId
    );

    List<ReadingStatusEntity>
    findByUserIdExternalInAndStoryIdExternal(
            List<UUID> userIds,
            UUID storyId
    );
}