package com.grupo3.BookVerse.features.reviewReport.controller;


import com.grupo3.BookVerse.features.reviewReport.services.ReviewReportService;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review-reports")

@AllArgsConstructor
@Tag(
        name = "Review Reports",
        description = "Endpoints for managing review reports"
)
public class ReviewReportController {

    private final ReviewReportService reviewReportService;


    @GetMapping
    @Operation(
            summary = "Get all review reports",
            description = "Retrieves the complete list of review reports registered in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Review reports retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })



    public ResponseEntity<List<ReviewReportResponseDto>> getAllReports() {

        return ResponseEntity.ok(reviewReportService.getAllReports());
    }

    @GetMapping("/{reportId}")
    @Operation(
            summary = "Get review report by ID",
            description = "Retrieves a review report using its external identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Review report retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Review report not found",
                    content = @Content
            )
    })
    public ResponseEntity<ReviewReportResponseDto> findById(
            @Parameter(
                    description = "External UUID of the review report",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID reportId
    ) {

        return ResponseEntity.ok(
                reviewReportService.getReportById(reportId)
        );
    }

    @PostMapping
    @Operation(
            summary = "Create a review report",
            description = "Creates a new review report associated with a review.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Review report created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referenced review or user not found",
                    content = @Content
            )
    })
    public ResponseEntity<ReviewReportResponseDto> save(
            @RequestBody
           (
                    description = "Review report creation payload",
                    required = true
            )
            @org.springframework.web.bind.annotation.RequestBody
            ReviewReportRequestDto reviewReportRequestDto
    ) {

        return ResponseEntity.ok(
                reviewReportService.save(reviewReportRequestDto)
        );
    }

    @DeleteMapping("/{reportId}")
    @Operation(
            summary = "Delete a review report",
            description = "Deletes a review report using its external identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Review report deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Review report not found",
                    content = @Content
            )
    })

    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "External UUID of the review report",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID reportId
    ) {

        reviewReportService.delete(reportId);

        return ResponseEntity.noContent().build();
    }
}