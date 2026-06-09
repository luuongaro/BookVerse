package com.grupo3.BookVerse.features.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {

    private UUID idExternal;
    private String googleBookId;
    private String title;
    private String description;
    private String isbn;
    private String thumbnailUrl;
    private String publisher;
    private String publishedDate;
    private String language;
    private String categories;
    private Set<String> authors;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer readingGroupsCount;
    private Integer readingStatusesCount;
}