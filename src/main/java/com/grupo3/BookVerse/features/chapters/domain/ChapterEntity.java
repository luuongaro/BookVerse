package com.grupo3.BookVerse.features.chapters.domain;


import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "chapters")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChapterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal;


    //Association with StoryEntity (Yan)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "story_id", nullable = false)
    private StoryEntity story;

    @Column(name = "chapter_number", nullable = false)
    private int chapterNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Column(name = "is_published", nullable = false)
    private boolean isPublished;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}