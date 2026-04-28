package com.grupo3.BookVerse.features.authors;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

}