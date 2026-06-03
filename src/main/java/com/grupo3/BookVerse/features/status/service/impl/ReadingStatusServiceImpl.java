package com.grupo3.BookVerse.features.status.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.status.domain.ReadingStatusEntity;
import com.grupo3.BookVerse.features.status.dto.ReadingStatusRequestDto;
import com.grupo3.BookVerse.features.status.dto.ReadingStatusResponseDto;
import com.grupo3.BookVerse.features.status.mappers.ReadingStatusMapper;
import com.grupo3.BookVerse.features.status.repository.ReadingStatusRepository;
import com.grupo3.BookVerse.features.status.service.ReadingStatusService;
import com.grupo3.BookVerse.features.stories.domain.StoryEntity;
import com.grupo3.BookVerse.features.stories.repository.StoryRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadingStatusServiceImpl implements ReadingStatusService {

    private final ReadingStatusRepository readingStatusRepository;
    private final ReadingStatusMapper readingStatusMapper;

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final StoryRepository storyRepository;

    @Override
    @Transactional
    public ReadingStatusResponseDto createReadingStatus(
            ReadingStatusRequestDto requestDto) {

        if (requestDto.bookId() == null &&
                requestDto.storyId() == null) {

            throw new BadRequestException(
                    "A reading status must be associated with a book, a story, or both"
            );
        }

        ReadingStatusEntity entity =
                readingStatusMapper.toEntity(requestDto);

        entity.setUser(
                findUserByIdExternal(requestDto.userId())
        );

        if (requestDto.bookId() != null) {
            entity.setBook(
                    findBookByIdExternal(requestDto.bookId())
            );
        }

        if (requestDto.storyId() != null) {
            entity.setStory(
                    findStoryByIdExternal(requestDto.storyId())
            );
        }

        ReadingStatusEntity saved =
                readingStatusRepository.save(entity);

        return readingStatusMapper.toResponseDto(saved);
    }

    @Override
    public ReadingStatusResponseDto getReadingStatusByIdExternal(
            UUID idExternal) {

        ReadingStatusEntity entity =
                findReadingStatusByIdExternal(idExternal);

        return readingStatusMapper.toResponseDto(entity);
    }

    @Override
    public List<ReadingStatusResponseDto> getAllReadingStatuses() {

        return readingStatusMapper.toResponseDtoList(
                readingStatusRepository.findAll()
        );
    }

    @Override
    public List<ReadingStatusResponseDto> getReadingStatusesByUser(
            UUID userId) {

        findUserByIdExternal(userId);

        return readingStatusMapper.toResponseDtoList(
                readingStatusRepository.findByUserIdExternal(userId)
        );
    }

    @Override
    @Transactional
    public void deleteReadingStatus(UUID idExternal) {

        ReadingStatusEntity entity =
                findReadingStatusByIdExternal(idExternal);

        readingStatusRepository.delete(entity);
    }
    @Override
    @Transactional
    public ReadingStatusResponseDto updateReadingStatus(
            UUID idExternal,
            ReadingStatusRequestDto requestDto) {

        if (requestDto.bookId() == null &&
                requestDto.storyId() == null) {

            throw new BadRequestException(
                    "A reading status must be associated with a book, a story, or both"
            );
        }

        ReadingStatusEntity entity =
                findReadingStatusByIdExternal(idExternal);

        entity.setUser(
                findUserByIdExternal(requestDto.userId())
        );

        entity.setBook(null);
        entity.setStory(null);

        if (requestDto.bookId() != null) {
            entity.setBook(
                    findBookByIdExternal(requestDto.bookId())
            );
        }

        if (requestDto.storyId() != null) {
            entity.setStory(
                    findStoryByIdExternal(requestDto.storyId())
            );
        }

        entity.setStatus(requestDto.status());
        entity.setStartedAt(requestDto.startedAt());
        entity.setFinishedAt(requestDto.finishedAt());

        ReadingStatusEntity saved =
                readingStatusRepository.save(entity);

        return readingStatusMapper.toResponseDto(saved);
    }

    private ReadingStatusEntity findReadingStatusByIdExternal(
            UUID idExternal) {

        return readingStatusRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ReadingStatus not found with idExternal: "
                                        + idExternal
                        ));
    }

    private UserEntity findUserByIdExternal(
            UUID idExternal) {

        return userRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: "
                                        + idExternal
                        ));
    }

    private BookEntity findBookByIdExternal(
            UUID idExternal) {

        return bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: "
                                        + idExternal
                        ));
    }

    private StoryEntity findStoryByIdExternal(
            UUID idExternal) {

        return storyRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Story not found with idExternal: "
                                        + idExternal
                        ));
    }

}

//// TO-DO:
//// Validate startedAt and finishedAt according to ReadingStatusEnum
//// when reading workflow requirements are finalized.
//// private void validateDatesByStatus(...)