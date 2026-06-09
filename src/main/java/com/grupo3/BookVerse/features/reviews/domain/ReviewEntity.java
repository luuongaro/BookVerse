package com.grupo3.BookVerse.features.reviews.domain;

import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_external", nullable = false, unique = true, updatable = false)
    private UUID idExternal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private UserEntity reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private StoryEntity story;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ReviewReportEntity> reports = new ArrayList<>();

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    @Column(name = "takedown_at")
    private LocalDateTime takedownAt;

    @Column(name = "takedown_reason", columnDefinition = "TEXT")
    private String takedownReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "takedown_status", nullable = false)
    private ReviewTakedownStatus takedownStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (idExternal == null) {
            idExternal = UUID.randomUUID();
        }

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        deleted = false;

        if (takedownStatus == null) {
            takedownStatus = ReviewTakedownStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}