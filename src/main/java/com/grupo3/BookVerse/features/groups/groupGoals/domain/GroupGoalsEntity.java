package com.grupo3.BookVerse.features.groups.groupGoals.domain;

import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private ReadingGroupEntity group;

    @Column(name = "target_progress", nullable = false)
    private Integer targetProgress;

    @Column(name = "target_date", nullable = false)
    private LocalDateTime targetDate;

    @Column(name = "average_progress", nullable = false, precision = 5, scale = 2)
    private BigDecimal averageProgress;

    @Column(name = "is_achieved", nullable = false)
    private Boolean isAchieved = false;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (isAchieved == null) {
            isAchieved = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
