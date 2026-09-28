package com.grupo3.BookVerse.features.reviewReport.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviewReport.domain.ReviewReportStatus;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportCreateRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportModerationRequestDto;
import com.grupo3.BookVerse.features.reviewReport.dtos.ReviewReportResponseDto;
import com.grupo3.BookVerse.features.reviewReport.mapper.ReviewReportMapper;
import com.grupo3.BookVerse.features.reviewReport.repository.ReviewReportRepository;
import com.grupo3.BookVerse.features.reviewReport.services.impl.ReviewReportServiceImpl;
import com.grupo3.BookVerse.features.reviews.domain.ReviewEntity;
import com.grupo3.BookVerse.features.reviews.domain.ReviewTakedownStatus;
import com.grupo3.BookVerse.features.reviews.repository.ReviewRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewReportServiceImplTest {

    @Mock
    private ReviewReportRepository reviewReportRepository;

    @Mock
    private ReviewReportMapper reviewReportMapper;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewReportServiceImpl reviewReportService;

    private UUID reportId;
    private UUID reviewId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        reportId = UUID.randomUUID();
        reviewId = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    // Verify that all reports are returned successfully
    @Test
    void getAllReports_shouldReturnList() {

        List<ReviewReportEntity> reports =
                List.of(new ReviewReportEntity());

        List<ReviewReportResponseDto> response =
                List.of(mock(ReviewReportResponseDto.class));

        when(reviewReportRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(reports);

        when(reviewReportMapper.toResponseListDto(reports))
                .thenReturn(response);

        List<ReviewReportResponseDto> result =
                reviewReportService.getAllReports();

        assertEquals(response, result);

        verify(reviewReportRepository)
                .findAllByOrderByCreatedAtDesc();
    }

    // Verify that pending reports are returned successfully
    @Test
    void getPendingReports_shouldReturnList() {

        List<ReviewReportEntity> reports =
                List.of(new ReviewReportEntity());

        List<ReviewReportResponseDto> response =
                List.of(mock(ReviewReportResponseDto.class));

        when(reviewReportRepository
                .findByStatusOrderByCreatedAtDesc(
                        ReviewReportStatus.PENDING))
                .thenReturn(reports);

        when(reviewReportMapper.toResponseListDto(reports))
                .thenReturn(response);

        List<ReviewReportResponseDto> result =
                reviewReportService.getPendingReports();

        assertEquals(response, result);
    }

    // Verify that a report is returned by external id
    @Test
    void getReportById_shouldReturnDto() {

        ReviewReportEntity entity =
                new ReviewReportEntity();

        ReviewReportResponseDto response =
                mock(ReviewReportResponseDto.class);

        when(reviewReportRepository.findByIdExternal(reportId))
                .thenReturn(Optional.of(entity));

        when(reviewReportMapper.toResponseDto(entity))
                .thenReturn(response);

        ReviewReportResponseDto result =
                reviewReportService.getReportById(reportId);

        assertEquals(response, result);
    }

    // Verify that an exception is thrown when report is not found
    @Test
    void getReportById_shouldThrowException_whenNotFound() {

        when(reviewReportRepository.findByIdExternal(reportId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> reviewReportService.getReportById(reportId)
        );
    }

    // Verify that a report is created successfully
    @Test
    void createReport_shouldReturnDto() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setIdExternal(userId);

        ReviewEntity review = new ReviewEntity();

        UserEntity reviewOwner = new UserEntity();
        reviewOwner.setId(2L);

        review.setReviewer(reviewOwner);
        review.setId(10L);
        review.setTakedownStatus(
                ReviewTakedownStatus.ACTIVE
        );

        ReviewReportCreateRequestDto dto =
                new ReviewReportCreateRequestDto(
                        reviewId,
                        userId,
                        "Spam content",
                        "Bad review"
                );

        ReviewReportEntity entity =
                new ReviewReportEntity();

        ReviewReportEntity saved =
                new ReviewReportEntity();

        ReviewReportResponseDto response =
                mock(ReviewReportResponseDto.class);

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null
                        )
                );

        when(userRepository.findByIdExternal(userId))
                .thenReturn(Optional.of(user));

        when(reviewRepository
                .findByIdExternalAndDeletedFalse(reviewId))
                .thenReturn(Optional.of(review));

        when(reviewReportRepository
                .existsByReviewIdAndReporterUserIdAndStatus(
                        10L,
                        1L,
                        ReviewReportStatus.PENDING
                ))
                .thenReturn(false);

        when(reviewReportMapper.toEntity(dto))
                .thenReturn(entity);

        when(reviewReportRepository.save(entity))
                .thenReturn(saved);

        when(reviewReportMapper.toResponseDto(saved))
                .thenReturn(response);

        ReviewReportResponseDto result =
                reviewReportService.createReport(dto);

        assertEquals(response, result);

        verify(reviewReportRepository)
                .save(entity);
    }

    // Verify that duplicate pending reports throw exception
    @Test
    void createReport_shouldThrow_whenDuplicatePendingReport() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setIdExternal(userId);

        ReviewEntity review = new ReviewEntity();
        review.setId(10L);

        UserEntity reviewOwner = new UserEntity();
        reviewOwner.setId(2L);

        review.setReviewer(reviewOwner);
        review.setTakedownStatus(
                ReviewTakedownStatus.ACTIVE
        );

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null
                        )
                );

        ReviewReportCreateRequestDto dto =
                new ReviewReportCreateRequestDto(
                        reviewId,
                        userId,
                        "Spam",
                        null
                );

        when(userRepository.findByIdExternal(userId))
                .thenReturn(Optional.of(user));

        when(reviewRepository
                .findByIdExternalAndDeletedFalse(reviewId))
                .thenReturn(Optional.of(review));

        when(reviewReportRepository
                .existsByReviewIdAndReporterUserIdAndStatus(
                        10L,
                        1L,
                        ReviewReportStatus.PENDING
                ))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> reviewReportService.createReport(dto)
        );
    }

    // Verify that users cannot report their own reviews
    @Test
    void createReport_shouldThrow_whenUserReportsOwnReview() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setIdExternal(userId);

        ReviewEntity review = new ReviewEntity();
        review.setReviewer(user);
        review.setTakedownStatus(
                ReviewTakedownStatus.ACTIVE
        );

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null
                        )
                );

        ReviewReportCreateRequestDto dto =
                new ReviewReportCreateRequestDto(
                        reviewId,
                        userId,
                        "Spam",
                        null
                );

        when(userRepository.findByIdExternal(userId))
                .thenReturn(Optional.of(user));

        when(reviewRepository
                .findByIdExternalAndDeletedFalse(reviewId))
                .thenReturn(Optional.of(review));

        assertThrows(
                BadRequestException.class,
                () -> reviewReportService.createReport(dto)
        );
    }

    // Verify that approving a report updates status and review takedown
    @Test
    void approveReport_shouldApproveSuccessfully() {

        ReviewEntity review = new ReviewEntity();

        ReviewReportEntity report =
                new ReviewReportEntity();

        report.setStatus(
                ReviewReportStatus.PENDING
        );

        report.setReview(review);

        ReviewReportModerationRequestDto dto =
                new ReviewReportModerationRequestDto(
                        "Inappropriate content"
                );

        ReviewReportResponseDto response =
                mock(ReviewReportResponseDto.class);

        when(reviewReportRepository.findByIdExternal(reportId))
                .thenReturn(Optional.of(report));

        when(reviewReportRepository.save(report))
                .thenReturn(report);

        when(reviewReportMapper.toResponseDto(report))
                .thenReturn(response);

        ReviewReportResponseDto result =
                reviewReportService.approveReport(
                        reportId,
                        dto
                );

        assertEquals(response, result);

        assertEquals(
                ReviewReportStatus.APPROVED,
                report.getStatus()
        );

        assertEquals(
                ReviewTakedownStatus.TAKEN_DOWN,
                review.getTakedownStatus()
        );
    }

    // Verify that rejecting a report updates status correctly
    @Test
    void rejectReport_shouldRejectSuccessfully() {

        ReviewReportEntity report =
                new ReviewReportEntity();

        report.setStatus(
                ReviewReportStatus.PENDING
        );

        ReviewReportModerationRequestDto dto =
                new ReviewReportModerationRequestDto(
                        "Not enough evidence"
                );

        ReviewReportResponseDto response =
                mock(ReviewReportResponseDto.class);

        when(reviewReportRepository.findByIdExternal(reportId))
                .thenReturn(Optional.of(report));

        when(reviewReportRepository.save(report))
                .thenReturn(report);

        when(reviewReportMapper.toResponseDto(report))
                .thenReturn(response);

        ReviewReportResponseDto result =
                reviewReportService.rejectReport(
                        reportId,
                        dto
                );

        assertEquals(response, result);

        assertEquals(
                ReviewReportStatus.REJECTED,
                report.getStatus()
        );
    }
}