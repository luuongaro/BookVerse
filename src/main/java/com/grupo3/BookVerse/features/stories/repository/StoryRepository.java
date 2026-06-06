package com.grupo3.BookVerse.features.stories.repository;

import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {

    Optional<StoryEntity> findByIdExternal(UUID idExternal);

    Optional<StoryEntity> findByIdExternalAndIsDeletedFalse(UUID idExternal);

    List<StoryEntity> findByIsDeletedFalseOrderByCreatedAtDesc();

    List<StoryEntity> findByAuthorIdAndIsDeletedFalseOrderByCreatedAtDesc(Long authorId);


}


