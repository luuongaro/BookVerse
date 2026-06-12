package com.grupo3.BookVerse.features.groups.readingGroups.repository;

import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingGroupRepository extends JpaRepository<ReadingGroupEntity, Long> {

    Optional<ReadingGroupEntity> findByIdExternal(UUID idExternal);

    List<ReadingGroupEntity> findByBook_IdExternal(UUID bookId);

    List<ReadingGroupEntity> findByStory_IdExternal(UUID storyId);

    List<ReadingGroupEntity> findByCreatedBy_IdExternal(UUID userId);

    boolean existsByNameIgnoreCaseAndBook_IdExternalAndIsActiveTrue(String name, UUID bookId);

    boolean existsByNameIgnoreCaseAndStory_IdExternalAndIsActiveTrue(String name, UUID storyId);

    boolean existsByNameIgnoreCaseAndBook_IdExternalAndIsActiveTrueAndIdExternalNot(String name, UUID bookId,  UUID idExternal);

    boolean existsByNameIgnoreCaseAndStory_IdExternalAndIsActiveTrueAndIdExternalNot(String name, UUID storyId,UUID idExternal);
}