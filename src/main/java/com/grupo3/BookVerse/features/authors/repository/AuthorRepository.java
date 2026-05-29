package com.grupo3.BookVerse.features.authors.repository;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByIdExternal(UUID idExternal);

    boolean existsByIdExternal(UUID idExternal);

    boolean existsByFullName(String fullName);

    void deleteByIdExternal(UUID idExternal);
}