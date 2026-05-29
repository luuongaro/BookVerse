package com.grupo3.BookVerse.features.stories;

import com.grupo3.BookVerse.features.chapters.domain.ChapterEntity;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stories")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class StoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_external", nullable = false, unique = true)
    private UUID idExternal;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }

    //Association
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChapterEntity> chapters = new ArrayList<>();


}
