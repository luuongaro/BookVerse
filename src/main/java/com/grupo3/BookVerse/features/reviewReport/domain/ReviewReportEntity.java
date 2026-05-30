package com.grupo3.BookVerse.features.reviewReport.domain;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

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

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "reporter_user_id", nullable = false)
    private Long reporterUserId;

    @Column(name = "moderator_user_id")
    private Long moderatorUserId;

    @Column(nullable = false)
    private String reason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    public enum ReportStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

}



