package com.grupo3.BookVerse.features.groups.readingGroups.controller;

import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupRequestDto;
import com.grupo3.BookVerse.features.groups.readingGroups.dto.ReadingGroupResponseDto;
import com.grupo3.BookVerse.features.groups.readingGroups.service.ReadingGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reading-groups")
@RequiredArgsConstructor
public class ReadingGroupController {

    private final ReadingGroupService readingGroupService;

    @PostMapping
    public ResponseEntity<ReadingGroupResponseDto> createGroup(
            @Valid @RequestBody ReadingGroupRequestDto dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(readingGroupService.createGroup(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReadingGroupResponseDto>> getAllGroups() {
        return ResponseEntity.ok(readingGroupService.getAllGroups());
    }

    @GetMapping("/{idExternal}")
    public ResponseEntity<ReadingGroupResponseDto> getGroupById(
            @PathVariable UUID idExternal
    ) {
        return ResponseEntity.ok(
                readingGroupService.getGroupByIdExternal(idExternal)
        );
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReadingGroupResponseDto>> getGroupsByBook(
            @PathVariable UUID bookId
    ) {
        return ResponseEntity.ok(
                readingGroupService.getGroupsByBookId(bookId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReadingGroupResponseDto>> getGroupsByUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(
                readingGroupService.getGroupsByUserId(userId)
        );
    }

    @PutMapping("/{idExternal}")
    public ResponseEntity<ReadingGroupResponseDto> updateGroup(
            @PathVariable UUID idExternal,
            @Valid @RequestBody ReadingGroupRequestDto dto
    ) {
        return ResponseEntity.ok(
                readingGroupService.updateGroup(idExternal, dto)
        );
    }

    @DeleteMapping("/{idExternal}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable UUID idExternal
    ) {
        readingGroupService.deleteGroup(idExternal);
        return ResponseEntity.noContent().build();
    }
}
