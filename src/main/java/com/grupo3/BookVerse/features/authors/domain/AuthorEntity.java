package com.grupo3.BookVerse.features.authors.domain;

import java.util.UUID;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

import com.grupo3.BookVerse.features.books.BookEntity;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String language;

   @ManyToMany(mappedBy = "authors")
   private List<BookEntity> books;

}