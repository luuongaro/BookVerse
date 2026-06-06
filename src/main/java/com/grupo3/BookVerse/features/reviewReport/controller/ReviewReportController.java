package com.grupo3.BookVerse.features.reviewReport.controller;

import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;
import com.grupo3.BookVerse.features.reviewReport.services.ReviewReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review-reports")
@RequiredArgsConstructor
@Tag(
        name = "Review Reports",
        description = "Endpoints for managing review reports"
)
public class ReviewReportController {

    private final ReviewReportService reviewReportService;

    @GetMapping
    @Operation(
            summary = "Get all review reports",
            description = "Retrieves all review reports registered in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review reports retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<List<ReviewReportResponseDto>> getAllReports() {
        return ResponseEntity.ok(reviewReportService.getAllReports());
    }

    @GetMapping("/{reportId}")
    @Operation(
            summary = "Get review report by external id",
            description = "Retrieves a review report using its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review report retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review report not found", content = @Content)
    })
    public ResponseEntity<ReviewReportResponseDto> findById(
            @Parameter(
                    description = "External UUID of the review report",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID reportId
    ) {
        return ResponseEntity.ok(
                reviewReportService.getReportById(reportId)
        );
    }

    @PostMapping
    @Operation(
            summary = "Create a new review report",
            description = "Creates a new report associated with a review and a reporting user.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review report created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review or user not found", content = @Content)
    })
    public ResponseEntity<ReviewReportResponseDto> save(
            @Valid @RequestBody ReviewReportRequestDto reviewReportRequestDto
    ) {
        return new ResponseEntity<>(
                reviewReportService.save(reviewReportRequestDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{reportId}")
    @Operation(
            summary = "Delete a review report",
            description = "Deletes a review report using its external UUID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review report deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review report not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "External UUID of the review report to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID reportId
    ) {
        reviewReportService.delete(reportId);
        return ResponseEntity.noContent().build();
    }
}

