package com.grupo3.BookVerse.features.status;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "group_members")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReadingStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; //FK

    @Column(name = "book_id", nullable = false)
    private BookEntity bookId; //FK

    //Association added by Yan :D
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "story_id", nullable = false)
    private StoryEntity story;

    @Enumerated(EnumType.STRING) // Guarda el enum como Texto en la base de datos
    @Column(name = "status", nullable = false)
    private ReadingStatusEnum status;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
