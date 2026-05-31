package com.grupo3.BookVerse.features.tips.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipResponseDto {

    private UUID idExternal;
    private Long senderUserId;
    private Long receiverUserId;
    private BigDecimal amount;
    private String message;
    private LocalDateTime createdAt;
}
