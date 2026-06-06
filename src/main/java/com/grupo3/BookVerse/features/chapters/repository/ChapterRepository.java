package com.grupo3.BookVerse.features.chapters.repository;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {

    Optional<ChapterEntity> findByIdExternalAndIsDeletedFalse(UUID idExternal);

    List<ChapterEntity> findByIsDeletedFalseOrderByCreatedAtDesc();

    List<ChapterEntity> findByStoryIdAndIsDeletedFalseOrderByChapterNumberAsc(Long storyId);

    Optional<ChapterEntity> findByStoryIdAndChapterNumber(Long storyId, int chapterNumber);

    boolean existsByStoryIdAndChapterNumber(Long storyId, int chapterNumber);

}