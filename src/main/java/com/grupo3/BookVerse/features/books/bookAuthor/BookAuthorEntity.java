package com.grupo3.BookVerse.features.books.bookAuthor;

import com.grupo3.BookVerse.features.authors.domain.AuthorEntity;
import com.grupo3.BookVerse.features.books.BookEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books_authors",
        uniqueConstraints = {
                @UniqueConstraint
                        (name = "uk_book_author", columnNames = {"book_id", "author_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookAuthorEntity {

    //Entity added by Yan - to manage the many-to-many relationship between books
    // and authors, as a book can have multiple authors and an author
    // can write multiple books.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorBookId;

    private Long authorId;

    private Long bookId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;


}
