package com.grupo3.BookVerse.features.authors;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")

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