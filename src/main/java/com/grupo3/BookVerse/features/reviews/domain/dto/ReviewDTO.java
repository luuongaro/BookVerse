
package com.grupo3.BookVerse.features.reviews.domain.dto;

public record ReviewDTO(

        Long id,
        Long userId,
        Integer rating,
        String content

) {
}

