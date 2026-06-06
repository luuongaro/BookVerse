package com.grupo3.BookVerse.features.reviewReport.domain;

import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "review_report")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReviewReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewEntity review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_user_id", nullable = false)
    private UserEntity reporterUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_user_id")
    private UserEntity moderatorUser;

    @Column(nullable = false)
    private String reason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewReportStatus status;

    @PrePersist
    protected void onCreate() {
        if (idExternal == null) {
            idExternal = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();

        if (status == null) {
            status = ReviewReportStatus.PENDING;
        }
    }
}





