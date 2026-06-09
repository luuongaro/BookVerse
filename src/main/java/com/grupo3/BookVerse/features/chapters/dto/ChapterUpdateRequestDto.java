package com.grupo3.BookVerse.features.chapters.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterUpdateRequestDto {


    @NotBlank(message = "Chapter title is required")
    @Size(max = 150, message = "Chapter title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Chapter content is required")
    private String content;

    private boolean published;

}
