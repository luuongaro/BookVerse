package com.grupo3.BookVerse.features.reviews;

import com.grupo3.BookVerse.common.exceptions.EntityNotFoundException;
import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.reviews.domain.ReviewReportEntity;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewReportRequestDto;
import com.grupo3.BookVerse.features.reviews.domain.dto.ReviewReportResponseDto;
import com.grupo3.BookVerse.features.reviews.domain.mappers.ReviewReportRequestMapper;
import com.grupo3.BookVerse.features.reviews.repository.ReviewReportRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewReportService implements IReviewReportService {

    private final ReviewReportRepository reviewReportRepository;

    private final IMapper<ReviewReportEntity, ReviewReportResponseDto> responseMapper;

    private final ReviewReportRequestMapper requestMapper;

    @Override
    public List<ReviewReportResponseDto> getAllReports() {

        return reviewReportRepository.findAll()
                .stream()
                .map(responseMapper::toDTO)
                .toList();
    }

    @Override
    public ReviewReportResponseDto getReportById(UUID reportId) {

        return reviewReportRepository.findByIdExternal(reportId)
                .map(responseMapper::toDTO)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "ReviewReport",
                                "Review report was not found",
                                "reportId",
                                reportId.toString()
                        ));
    }

    @Override
    public ReviewReportResponseDto save(ReviewReportRequestDto reviewReportRequestDto) {

        ReviewReportEntity toBeSaved =
                requestMapper.toEntity(reviewReportRequestDto);

        ReviewReportEntity saved =
                reviewReportRepository.save(toBeSaved);

        return responseMapper.toDTO(saved);
    }

    @Override
    public void delete(UUID reportId) {

        ReviewReportEntity toBeDeleted =
                reviewReportRepository.findByIdExternal(reportId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "ReviewReport",
                                        "Review report was not found",
                                        "reportId",
                                        reportId.toString()
                                ));

        reviewReportRepository.delete(toBeDeleted);
    }
}