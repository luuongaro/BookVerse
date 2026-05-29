package com.grupo3.BookVerse.features.subscriptions;

import com.grupo3.BookVerse.features.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(nullable = false)
    private SubscriptionType type;

    @Column(name = "max_stories_published", nullable = false)
    private int maxStoriesPublished;

    @Column(name = "advanced_stats_enabled", nullable = false)
    private boolean advancedStatsEnabled;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (idExternal == null) {
            idExternal = UUID.randomUUID();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum SubscriptionType {
        FREE, PREMIUM
    }

    //adding association with user (Yan)
    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();

}
