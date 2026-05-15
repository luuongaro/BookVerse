
package com.grupo3.BookVerse.features.reviews.domain.dto;

import java.util.UUID;

public record ReviewResponseDto(

        UUID reviewId,
        UUID userId,
        Integer rating,
        String content

) {
}

