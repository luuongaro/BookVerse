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
    private String title;
    private String description;
    private String isbn;
    private String genre;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<Long> authorIds;
    private Set<String> authorNames;
    private Integer readingGroupsCount;
    private Integer readingStatusesCount;
}