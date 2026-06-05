package com.grupo3.BookVerse.features.googleBooks.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.config.GoogleBooksProperties;
import com.grupo3.BookVerse.features.googleBooks.client.GoogleBooksClient;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBooksApiResponseDto;
import com.grupo3.BookVerse.features.googleBooks.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@RequiredArgsConstructor
public class GoogleBooksServiceImpl implements GoogleBooksService {


    private final GoogleBooksClient googleBooksClient;


    /**
     * Service responsible for handling business logic related to Google Books search.
     * Validates the query, delegates the API call to the client,
     * and processes the response (e.g., handling empty results).
     */


    @Override
    public GoogleBooksApiResponseDto searchBooks(String query) {

        if (query == null || query.trim().isEmpty()) {
            throw new BadRequestException("Query parameter cannot be null or empty");
        }

        String normalizedQuery = query.trim();


        GoogleBooksApiResponseDto responseDto = googleBooksClient.searchBooks(normalizedQuery);

        if (responseDto == null || responseDto.getItems() == null ||
                responseDto.getItems().isEmpty()) {
            throw new ResourceNotFoundException("No books found for the given query");
        }

        return responseDto;
    }
}
