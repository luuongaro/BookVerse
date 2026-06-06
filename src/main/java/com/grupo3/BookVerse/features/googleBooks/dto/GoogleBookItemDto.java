package com.grupo3.BookVerse.features.googleBooks.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
// DTO that represents the content inside "volumeInfo".
public class GoogleBookItemDto {
    private String title;
    private List<String> authors;
    private String description;
}
