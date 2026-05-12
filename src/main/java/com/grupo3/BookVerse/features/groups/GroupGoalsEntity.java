package com.grupo3.BookVerse.features.groups;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "group_goals")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class GroupGoalsEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "group_id", nullable = false)
        private Long groupId;

        @Column(name = "target_progress", nullable = false)
        private Integer targetProgress;

        @Column(name = "target_date", nullable = false)
        private LocalDateTime targetDate;

        @Column(name = "average_progress", nullable = false, precision = 5, scale = 2)
        private BigDecimal averageProgress;

        @Builder.Default
        @Column(nullable = false)
        private Boolean achieved = false;

        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @PrePersist
        public void prePersist() {
            this.updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        public void preUpdate() {
            this.updatedAt = LocalDateTime.now();
        }
    }

