package com.grupo3.BookVerse.features.books.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDto {

    @NotBlank(message = "Book title is required")
    @Size(max = 255, message = "Book title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Book description is required")
    private String description;

    @NotBlank(message = "Book ISBN is required")
    @Size(max = 20, message = "Book ISBN cannot exceed 20 characters")
    private String isbn;

    @NotBlank(message = "Book genre is required")
    @Size(max = 100, message = "Book genre cannot exceed 100 characters")
    private String genre;
}