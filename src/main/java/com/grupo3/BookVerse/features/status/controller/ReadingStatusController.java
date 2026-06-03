package com.grupo3.BookVerse.features.status.controller;


import com.grupo3.BookVerse.features.status.dto.ReadingStatusRequestDto;
import com.grupo3.BookVerse.features.status.dto.ReadingStatusResponseDto;
import com.grupo3.BookVerse.features.status.service.ReadingStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reading-statuses")
@RequiredArgsConstructor
public class ReadingStatusController {

    private final ReadingStatusService readingStatusService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingStatusResponseDto createReadingStatus(
            @Valid @RequestBody ReadingStatusRequestDto requestDto) {

        return readingStatusService.createReadingStatus(requestDto);
    }

    @GetMapping
    public List<ReadingStatusResponseDto> getAllReadingStatuses() {

        return readingStatusService.getAllReadingStatuses();
    }

    @GetMapping("/{idExternal}")
    public ReadingStatusResponseDto getReadingStatusByIdExternal(
            @PathVariable UUID idExternal) {

        return readingStatusService.getReadingStatusByIdExternal(idExternal);
    }

    @GetMapping("/user/{userId}")
    public List<ReadingStatusResponseDto> getReadingStatusesByUser(
            @PathVariable UUID userId) {

        return readingStatusService.getReadingStatusesByUser(userId);
    }

    @DeleteMapping("/{idExternal}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingStatus(
            @PathVariable UUID idExternal) {

        readingStatusService.deleteReadingStatus(idExternal);
    }

    @PutMapping("/{idExternal}")
    public ReadingStatusResponseDto updateReadingStatus(
            @PathVariable UUID idExternal,
            @Valid @RequestBody ReadingStatusRequestDto requestDto) {

        return readingStatusService.updateReadingStatus(
                idExternal,
                requestDto
        );
    }
}