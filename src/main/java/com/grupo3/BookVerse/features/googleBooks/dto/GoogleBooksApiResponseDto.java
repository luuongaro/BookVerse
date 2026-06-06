package com.grupo3.BookVerse.features.googleBooks.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
// DTO that receives the full response from Google Books API.
public class GoogleBooksApiResponseDto {
    private List<GoogleBookVolumeDto> items;
}
