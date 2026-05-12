package com.grupo3.BookVerse.features.groups;

import com.grupo3.BookVerse.features.books.bookEntity;
import com.grupo3.BookVerse.features.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "reading_groups")
public class ReadingGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //PK

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal = UUID.randomUUID();

    @Column(name = "book_id", nullable = false)
    private BookEntity bookId; //FK

    @Column(name = "created_by_user_id", nullable = false)
    private UserEntity createdByUserId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
