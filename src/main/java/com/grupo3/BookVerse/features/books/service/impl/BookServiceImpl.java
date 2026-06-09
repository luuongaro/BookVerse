package com.grupo3.BookVerse.features.books.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.dto.BookResponseDto;
import com.grupo3.BookVerse.features.books.mapper.BookMapper;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.books.service.BookService;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBookIndustryIdentifierDto;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBookItemDto;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBookVolumeDto;
import com.grupo3.BookVerse.features.googleBooks.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GoogleBooksService googleBooksService;

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        return bookMapper.toResponseDtoList(bookRepository.findByDeletedFalse());
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBookByIdExternal(UUID idExternal) {
        BookEntity book = findBookByIdExternal(idExternal);
        return bookMapper.toResponseDto(book);
    }

    @Override
    @Transactional
    public BookResponseDto findOrCreateFromGoogleBookId(String googleBookId) {
        BookEntity existingBook = bookRepository.findByGoogleBookId(googleBookId)
                .filter(book -> Boolean.FALSE.equals(book.getDeleted()))
                .orElse(null);

        if (existingBook != null) {
            return bookMapper.toResponseDto(existingBook);
        }

        GoogleBookVolumeDto googleBook = googleBooksService.getBookByGoogleId(googleBookId);
        BookEntity newBook = mapGoogleBookToEntity(googleBook);

        BookEntity savedBook = bookRepository.save(newBook);
        return bookMapper.toResponseDto(savedBook);
    }

    private BookEntity findBookByIdExternal(UUID idExternal) {
        return bookRepository.findByIdExternal(idExternal)
                .filter(book -> Boolean.FALSE.equals(book.getDeleted()))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: " + idExternal
                        )
                );
    }

    private BookEntity mapGoogleBookToEntity(GoogleBookVolumeDto googleBook) {
        GoogleBookItemDto volumeInfo = googleBook.getVolumeInfo();

        return BookEntity.builder()
                .googleBookId(googleBook.getId())
                .title(volumeInfo.getTitle())
                .description(volumeInfo.getDescription())
                .authors(extractAuthors(volumeInfo))
                .categories(extractCategories(volumeInfo))
                .publisher(volumeInfo.getPublisher())
                .publishedDate(volumeInfo.getPublishedDate())
                .language(volumeInfo.getLanguage())
                .thumbnailUrl(extractThumbnail(volumeInfo))
                .isbn(extractIsbn(volumeInfo))
                .build();
    }

    private Set<String> extractAuthors(GoogleBookItemDto volumeInfo) {
        if (volumeInfo.getAuthors() == null || volumeInfo.getAuthors().isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(volumeInfo.getAuthors());
    }

    private String extractCategories(GoogleBookItemDto volumeInfo) {
        if (volumeInfo.getCategories() == null || volumeInfo.getCategories().isEmpty()) {
            return null;
        }
        return String.join(", ", volumeInfo.getCategories());
    }

    private String extractThumbnail(GoogleBookItemDto volumeInfo) {
        if (volumeInfo.getImageLinks() == null) {
            return null;
        }
        return volumeInfo.getImageLinks().getThumbnail();
    }

    private String extractIsbn(GoogleBookItemDto volumeInfo) {
        if (volumeInfo.getIndustryIdentifiers() == null || volumeInfo.getIndustryIdentifiers().isEmpty()) {
            return null;
        }

        return volumeInfo.getIndustryIdentifiers().stream()
                .filter(identifier -> "ISBN_13".equalsIgnoreCase(identifier.getType()))
                .map(GoogleBookIndustryIdentifierDto::getIdentifier)
                .findFirst()
                .orElse(
                        volumeInfo.getIndustryIdentifiers().stream()
                                .map(GoogleBookIndustryIdentifierDto::getIdentifier)
                                .findFirst()
                                .orElse(null)
                );
    }
}
