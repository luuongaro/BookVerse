package com.grupo3.BookVerse.features.googleBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//DTO that represents each item inside the "items" list
public class GoogleBooksResponseDto {
    private String id;
    private GoogleBookItemDto volumeInfo;
}
