package com.grupo3.BookVerse.features.googleBooks.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.config.GoogleBooksProperties;
import com.grupo3.BookVerse.features.googleBooks.dto.GoogleBooksApiResponseDto;
import com.grupo3.BookVerse.features.googleBooks.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@RequiredArgsConstructor
public class GoogleBooksServiceImpl implements GoogleBooksService {

    private final GoogleBooksProperties googleBooksProperties;
    private final RestTemplate restTemplate;

    @Override
    public GoogleBooksApiResponseDto searchBooks(String query) {

        if (query == null || query.trim().isEmpty()) {
            throw new BadRequestException("Query parameter cannot be null or empty");
        }

        String normalizedQuery = query.trim();

        String url = UriComponentsBuilder.fromUriString(googleBooksProperties.getUrl())
                .queryParam("q", normalizedQuery)
                .queryParam("key", googleBooksProperties.getKey())
                .toUriString();

        GoogleBooksApiResponseDto responseDto =
                restTemplate.getForObject(url, GoogleBooksApiResponseDto.class);

        if (responseDto == null || responseDto.getItems() == null ||
                responseDto.getItems().isEmpty()) {
            throw new ResourceNotFoundException("No books found for the given query");
        }

        return responseDto;
    }

    /*Service is responsible for consuming the external Google Books API.
    First, it validates that the search is not empty, then it normalizes the query,
    constructs the URL using the configuration data loaded from application.yml,
    makes the HTTP call with RestTemplate, and finally validates whether the
    response contains results before returning it.*/
}
