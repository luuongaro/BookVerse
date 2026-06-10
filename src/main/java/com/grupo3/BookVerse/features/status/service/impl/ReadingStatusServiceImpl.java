package com.grupo3.BookVerse.features.status.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.books.domain.BookEntity;
import com.grupo3.BookVerse.features.books.repository.BookRepository;
import com.grupo3.BookVerse.features.chapters.repository.ChapterRepository;
import com.grupo3.BookVerse.features.status.domain.ProgressType;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ChapterRepository chapterRepository;

    @Override
    @Transactional
    public ReadingStatusResponseDto createReadingStatus(
            ReadingStatusRequestDto requestDto
    ) {

        validateContentAssociation(requestDto);
        validateProgress(requestDto);
        validateStoryChapterProgress(requestDto);

        ReadingStatusEntity entity =
                readingStatusMapper.toEntity(requestDto);

        UserEntity user =
                findUserByIdExternal(requestDto.userId());

        entity.setUser(user);

        if (requestDto.bookId() != null) {

            BookEntity book =
                    findBookByIdExternal(requestDto.bookId());

            entity.setBook(book);
        }

        if (requestDto.storyId() != null) {

            StoryEntity story =
                    findStoryByIdExternal(requestDto.storyId());

            entity.setStory(story);
        }

        ReadingStatusEntity saved =
                readingStatusRepository.save(entity);

        return readingStatusMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReadingStatusResponseDto getReadingStatusByIdExternal(
            UUID idExternal
    ) {

        ReadingStatusEntity entity =
                findReadingStatusByIdExternal(idExternal);

        return readingStatusMapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingStatusResponseDto> getAllReadingStatuses() {

        List<ReadingStatusEntity> readingStatuses =
                readingStatusRepository.findAll();

        return readingStatusMapper.toResponseDtoList(readingStatuses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReadingStatusResponseDto> getReadingStatusesByUser(
            UUID userId
    ) {

        findUserByIdExternal(userId);

        List<ReadingStatusEntity> readingStatuses =
                readingStatusRepository.findByUserIdExternal(userId);

        return readingStatusMapper.toResponseDtoList(readingStatuses);
    }

    @Override
    @Transactional
    public ReadingStatusResponseDto updateReadingStatus(
            UUID idExternal,
            ReadingStatusRequestDto requestDto
    ) {

        validateContentAssociation(requestDto);
        validateProgress(requestDto);
        validateStoryChapterProgress(requestDto);

        ReadingStatusEntity entity =
                findReadingStatusByIdExternal(idExternal);

        UserEntity user =
                findUserByIdExternal(requestDto.userId());

        entity.setUser(user);

        entity.setBook(null);
        entity.setStory(null);

        if (requestDto.bookId() != null) {

            BookEntity book =
                    findBookByIdExternal(requestDto.bookId());

            entity.setBook(book);
        }

        if (requestDto.storyId() != null) {

            StoryEntity story =
                    findStoryByIdExternal(requestDto.storyId());

            entity.setStory(story);
        }

        entity.setStatus(requestDto.status());

        entity.setProgressType(requestDto.progressType());
        entity.setProgressValue(requestDto.progressValue());

        entity.setStartedAt(requestDto.startedAt());
        entity.setFinishedAt(requestDto.finishedAt());

        ReadingStatusEntity saved =
                readingStatusRepository.save(entity);

        return readingStatusMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteReadingStatus(UUID idExternal) {

        ReadingStatusEntity entity =
                findReadingStatusByIdExternal(idExternal);

        readingStatusRepository.delete(entity);
    }

    private void validateContentAssociation(
            ReadingStatusRequestDto requestDto
    ) {

        if (requestDto.bookId() == null
                && requestDto.storyId() == null) {

            throw new BadRequestException(
                    "A reading status must be associated with either a book or a story"
            );
        }

        if (requestDto.bookId() != null
                && requestDto.storyId() != null) {

            throw new BadRequestException(
                    "A reading status cannot be associated with both a book and a story"
            );
        }
    }

    private void validateProgress(
            ReadingStatusRequestDto requestDto
    ) {

        boolean hasProgressType =
                requestDto.progressType() != null;

        boolean hasProgressValue =
                requestDto.progressValue() != null;

        if (hasProgressType != hasProgressValue) {

            throw new BadRequestException(
                    "Progress type and progress value must be provided together"
            );
        }

        if (requestDto.progressValue() != null
                && requestDto.progressValue() <= 0) {

            throw new BadRequestException(
                    "Progress value must be greater than zero"
            );
        }
    }

    private void validateStoryChapterProgress(
            ReadingStatusRequestDto requestDto
    ) {

        if (requestDto.storyId() == null) {
            return;
        }

        if (requestDto.progressType() != ProgressType.CHAPTER) {
            return;
        }

        StoryEntity story =
                findStoryByIdExternal(requestDto.storyId());

        boolean chapterExists =
                chapterRepository.existsByStoryIdAndChapterNumberAndDeletedFalse(
                        story.getId(),
                        requestDto.progressValue()
                );

        if (!chapterExists) {

            throw new BadRequestException(
                    "Chapter "
                            + requestDto.progressValue()
                            + " does not exist for this story"
            );
        }
    }

    private ReadingStatusEntity findReadingStatusByIdExternal(
            UUID idExternal
    ) {

        return readingStatusRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reading status not found with idExternal: "
                                        + idExternal
                        )
                );
    }

    private UserEntity findUserByIdExternal(
            UUID idExternal
    ) {

        return userRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: "
                                        + idExternal
                        )
                );
    }

    private BookEntity findBookByIdExternal(
            UUID idExternal
    ) {

        return bookRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with idExternal: "
                                        + idExternal
                        )
                );
    }

    private StoryEntity findStoryByIdExternal(
            UUID idExternal
    ) {

        return storyRepository.findByIdExternal(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Story not found with idExternal: "
                                        + idExternal
                        )
                );
    }
}