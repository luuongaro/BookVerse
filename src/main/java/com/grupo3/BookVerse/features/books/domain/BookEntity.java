package com.grupo3.BookVerse.features.books.domain;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Boolean deleted;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<AuthorEntity> authors = new HashSet<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<ReadingGroupEntity> readingGroups = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<ReadingStatusEntity> readingStatuses = new ArrayList<>();
}