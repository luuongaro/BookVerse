package com.grupo3.BookVerse.features.subscriptions.domain;

import com.grupo3.BookVerse.features.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_external", nullable = false, unique = true)
    private UUID idExternal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SubscriptionType type;

    @Column(name = "max_stories_published", nullable = false)
    private int maxStoriesPublished;

    @Column(name = "advanced_stats_enabled", nullable = false)
    private boolean advancedStatsEnabled;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "max_active_stories_reading", nullable = false)
    private int maxActiveStoriesReading;

    @PrePersist
    protected void onCreate() {
        if (idExternal == null) {
            idExternal = UUID.randomUUID();
        }
    }

    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    @Builder.Default
    private List<UserEntity> users = new ArrayList<>();
}
