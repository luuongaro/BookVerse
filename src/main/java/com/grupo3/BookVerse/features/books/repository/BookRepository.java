package com.grupo3.BookVerse.features.books.repository;

import com.grupo3.BookVerse.features.books.domain.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByIdExternal(UUID idExternal);

    Optional<BookEntity> findByGoogleBookId(String googleBookId);

    Page<BookEntity> findByDeletedFalse(Pageable pageable);
}