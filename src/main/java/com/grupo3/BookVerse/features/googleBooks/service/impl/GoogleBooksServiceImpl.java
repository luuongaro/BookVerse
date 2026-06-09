package com.grupo3.BookVerse.features.googleBooks.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.googleBooks.client.GoogleBooksClient;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBookVolumeDto;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBooksApiResponseDto;
import com.grupo3.BookVerse.features.googleBooks.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleBooksServiceImpl implements GoogleBooksService {

    private final GoogleBooksClient googleBooksClient;

    @Override
    public GoogleBooksApiResponseDto searchBooks(String query) {
        validateSearchValue(query, "Query");

        String normalizedQuery = query.trim();
        logGoogleBooksSearch("general", normalizedQuery);

        GoogleBooksApiResponseDto responseDto = googleBooksClient.searchBooks(normalizedQuery);
        validateResponse(responseDto);

        return responseDto;
    }

    @Override
    public GoogleBooksApiResponseDto searchBooksByTitle(String title) {
        validateSearchValue(title, "Title");

        String normalizedTitle = "intitle:" + title.trim();
        logGoogleBooksSearch("title", normalizedTitle);

        GoogleBooksApiResponseDto responseDto = googleBooksClient.searchBooks(normalizedTitle);
        validateResponse(responseDto);

        return responseDto;
    }

    @Override
    public GoogleBooksApiResponseDto searchBooksByAuthor(String author) {
        validateSearchValue(author, "Author");

        String normalizedAuthor = "inauthor:" + author.trim();
        logGoogleBooksSearch("author", normalizedAuthor);

        GoogleBooksApiResponseDto responseDto = googleBooksClient.searchBooks(normalizedAuthor);
        validateResponse(responseDto);

        return responseDto;
    }

    @Override
    public GoogleBookVolumeDto getBookByGoogleId(String googleBookId) {
        validateSearchValue(googleBookId, "Google Book ID");

        GoogleBookVolumeDto book = googleBooksClient.getBookByGoogleId(googleBookId);

        if (book == null || book.getVolumeInfo() == null) {
            throw new ResourceNotFoundException("Book not found in Google Books with id: " + googleBookId);
        }

        return book;
    }

    private void validateSearchValue(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException(fieldName + " parameter cannot be null or empty");
        }
    }

    private void validateResponse(GoogleBooksApiResponseDto responseDto) {
        if (responseDto == null || responseDto.getItems() == null || responseDto.getItems().isEmpty()) {
            throw new ResourceNotFoundException("No books found for the given search criteria");
        }
    }

    @Async
    public void logGoogleBooksSearch(String searchType, String value) {
        System.out.println("Asynchronous Google Books search [" + searchType + "]: " + value);
    }
}
