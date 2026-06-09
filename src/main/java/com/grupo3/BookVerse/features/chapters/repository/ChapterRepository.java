package com.grupo3.BookVerse.features.chapters.repository;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {

    Optional<ChapterEntity> findByIdExternalAndDeletedFalse(UUID idExternal);

    List<ChapterEntity> findAllByDeletedFalseOrderByCreatedAtDesc();

    List<ChapterEntity> findByStoryIdAndDeletedFalseOrderByChapterNumberAsc(Long storyId);

    Optional<ChapterEntity> findTopByStoryIdOrderByChapterNumberDesc(Long storyId);
}